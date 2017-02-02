package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.*;
import com.angkorteam.ecommerce.vo.*;
import com.angkorteam.framework.jdbc.DeleteQuery;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class OrdersServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/orders", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartDeliveryInfoServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));
        SelectQuery selectQuery = null;

        PlatformUser currentUser = Platform.getCurrentUser(request);

        InputStreamReader stream = new InputStreamReader(request.getInputStream());
        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);

        LOGGER.info(this.gson.toJson(requestBody));

        selectQuery = new SelectQuery("ecommerce_shipping");
        selectQuery.addWhere("ecommerce_shipping_id = :ecommerce_shipping_id", requestBody.shippingType);
        ECommerceShipping shippingRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceShipping.class);

        selectQuery = new SelectQuery("ecommerce_payment");
        selectQuery.addWhere("ecommerce_payment_id = :ecommerce_payment_id", requestBody.paymentType);
        ECommercePayment paymentRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommercePayment.class);

        String ecommerceRegionId = null;
        Date createdDate = new Date();

        // new, in_process, completed, canceled
        String status = "New";

        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("user_id = :user_id", currentUser.getUserId());
        ECommerceCart cartRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCart.class);

        Integer totalProductCount = 0;
        Double totalPrice = 0d;

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getECommerceCartId());
        List<ECommerceCartProductItem> cartItemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCartProductItem.class);

        Map<Long, Integer> requestVariants = Maps.newHashMap();
        if (cartItemRecords != null && !cartItemRecords.isEmpty()) {
            for (ECommerceCartProductItem cartItemRecord : cartItemRecords) {
                Long variantId = cartItemRecord.getECommerceProductVariantId();
                if (!requestVariants.containsKey(variantId)) {
                    requestVariants.put(variantId, 0);
                }
                Integer quantity = cartItemRecord.getQuantity();
                quantity = quantity + requestVariants.get(variantId);
                requestVariants.put(variantId, quantity);
            }
        }

        // stock validation
        List<String> errors = Lists.newArrayList();
        for (Map.Entry<Long, Integer> requestQuantity : requestVariants.entrySet()) {
            selectQuery = new SelectQuery("ecommerce_product_variant");
            selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", requestQuantity.getKey());
            ECommerceProductVariant variantRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProductVariant.class);

            selectQuery = new SelectQuery("ecommerce_product");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", variantRecord.getECommerceProductId());
            ECommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProduct.class);

            selectQuery = new SelectQuery("ecommerce_color");
            selectQuery.addWhere("ecommerce_color_id = :ecommerce_color_id", variantRecord.getECommerceColorId());
            ECommerceColor colorRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceColor.class);

            selectQuery = new SelectQuery("ecommerce_size");
            selectQuery.addWhere("ecommerce_size_id = :ecommerce_size_id", variantRecord.getECommerceSizeId());
            ECommerceSize sizeRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceSize.class);

            Integer quantity = variantRecord.getQuantity() == null ? 0 : variantRecord.getQuantity();
            if (quantity <= 0) {
                String productName = productRecord.getName();
                String colorValue = colorRecord.getValue();
                String sizeValue = sizeRecord.getValue();
                errors.add(productName + " is out of stock for " + colorValue + " / " + sizeValue);
            } else {
                if (quantity < requestQuantity.getValue()) {
                    String productName = productRecord.getName();
                    String colorValue = colorRecord.getValue();
                    String sizeValue = sizeRecord.getValue();
                    errors.add(productName + " is available only " + quantity + " for " + colorValue + " / " + sizeValue);
                }
            }
        }

        if (!errors.isEmpty()) {
            Map<String, Object> error = Maps.newHashMap();
            error.put("body", errors);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        InsertQuery insertQuery = null;
        insertQuery = new InsertQuery("ecommerce_order");

        Double shippingPriceAddon = 0d;

        Long orderId = Platform.randomUUIDLong();
        insertQuery.addValue("ecommerce_order_id", orderId);
        insertQuery.addValue("user_id = :user_id", currentUser.getUserId());
        insertQuery.addValue("ecommerce_shipping_id = :ecommerce_shipping_id", shippingRecord.getECommerceShippingId());
        insertQuery.addValue("ecommerce_payment_id = :ecommerce_payment_id", paymentRecord.getECommercePaymentId());
        insertQuery.addValue("name = :name", requestBody.name);
        insertQuery.addValue("street = :street", requestBody.street);
        insertQuery.addValue("house_number = :house_number", requestBody.houseNumber);
        insertQuery.addValue("city = :city", requestBody.city);
        insertQuery.addValue("zip = :zip", requestBody.zip);
        insertQuery.addValue("email = :email", requestBody.email);
        insertQuery.addValue("phone = :phone", requestBody.phone);
        insertQuery.addValue("note = :note", requestBody.note);
        insertQuery.addValue("date_created = :date_created", createdDate);
        insertQuery.addValue("buyer_status = :buyer_status", "Reviewing");
        insertQuery.addValue("order_status = :order_status", status);
        insertQuery.addValue("total = :total", 0);
        insertQuery.addValue("shipping_name = :shipping_name", shippingRecord.getName());
        insertQuery.addValue("shipping_price = :shipping_price", 0);
        insertQuery.addValue("payment_name = :payment_name", paymentRecord.getName());
        insertQuery.addValue("payment_price = :payment_price", paymentRecord.getPrice());

        shippingPriceAddon = shippingRecord.getPrice();

        named.update(insertQuery.toSQL(), insertQuery.getParam());

        if (cartItemRecords != null && !cartItemRecords.isEmpty()) {
            for (ECommerceCartProductItem cartItemRecord : cartItemRecords) {

                // create master order detail

                selectQuery = new SelectQuery("ecommerce_product");
                selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", cartItemRecord.getECommerceProductId());
                ECommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProduct.class);

                selectQuery = new SelectQuery("ecommerce_product_variant");
                selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", cartItemRecord.getECommerceProductVariantId());
                ECommerceProductVariant variantRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProductVariant.class);

                selectQuery = new SelectQuery("ecommerce_category");
                selectQuery.addWhere("ecommerce_category_id = :ecommerce_category_id", productRecord.getECommerceCategoryId());
                ECommerceCategory categoryRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCategory.class);

                selectQuery = new SelectQuery("ecommerce_color");
                selectQuery.addWhere("ecommerce_color_id = :ecommerce_color_id", variantRecord.getECommerceColorId());
                ECommerceColor colorRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceColor.class);

                selectQuery = new SelectQuery("ecommerce_size");
                selectQuery.addWhere("ecommerce_size_id = :ecommerce_size_id", variantRecord.getECommerceColorId());
                ECommerceSize sizeRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceSize.class);

                Integer quantity = cartItemRecord.getQuantity() == null ? 0 : cartItemRecord.getQuantity();
                Double price = productRecord.getPrice() == null ? 0d : productRecord.getPrice();
                totalProductCount = quantity + totalProductCount;
                totalPrice = totalPrice + (quantity * price);
                Double productShippingPriceAddon = productRecord.getShippingPrice() == null ? 0d : productRecord.getShippingPrice();
                if (productShippingPriceAddon != null && productShippingPriceAddon > 0) {
                    shippingPriceAddon = (shippingPriceAddon == null ? 0d : shippingPriceAddon) + (productShippingPriceAddon * quantity);
                }

                selectQuery = new SelectQuery("file");
                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', file.path, '/', file.name)");
                selectQuery.addWhere("file.file_id = :file_id", productRecord.getMainImageFileId());
                String mainImage = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);

                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', file.path, '/', file.name)");
                selectQuery.addWhere("file.file_id = :file_id", colorRecord.getImgFileId());
                String img = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);

                insertQuery = new InsertQuery("ecommerce_order_item");
                insertQuery.addValue("ecommerce_order_item = :ecommerce_order_item", Platform.randomUUIDLong());
                insertQuery.addValue("ecommerce_order_id = :ecommerce_order_id", orderId);
                insertQuery.addValue("quantity = :quantity", cartItemRecord.getQuantity());
                insertQuery.addValue("total_price = :total_price", quantity * price);
                insertQuery.addValue("ecommerce_category_id = :ecommerce_category_id", categoryRecord.getECommerceCategoryId());
                insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productRecord.getECommerceProductId());
                insertQuery.addValue("product_url = :product_url", productRecord.getUrl());
                insertQuery.addValue("product_name = :product_name", productRecord.getName());
                insertQuery.addValue("product_shipping_price = :product_shipping_price", productRecord.getShippingPrice());
                insertQuery.addValue("product_price = :product_price", productRecord.getPrice());
                insertQuery.addValue("product_reference = :product_reference", productRecord.getReference());
                insertQuery.addValue("product_discount_price = :product_discount_price", productRecord.getDiscountPrice());
                insertQuery.addValue("product_description = :product_description", productRecord.getDescription());
                insertQuery.addValue("product_main_image = :product_main_image", mainImage);
                insertQuery.addValue("product_main_image_file_id = :product_main_image_file_id", productRecord.getMainImageFileId());
                insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant_id", variantRecord.getECommerceProductId());
                insertQuery.addValue("variant_reference = :variant_reference", variantRecord.getReference());
                insertQuery.addValue("ecommerce_color_id = :ecommerce_color_id", colorRecord.getECommerceColorId());
                insertQuery.addValue("color_value = :color_value", colorRecord.getValue());
                insertQuery.addValue("color_code = :color_code", colorRecord.getCode());
                insertQuery.addValue("color_reference = :color_reference", colorRecord.getReference());
                insertQuery.addValue("color_img = :color_img", img);
                insertQuery.addValue("color_img_file_id = :color_img_file_id", colorRecord.getImgFileId());
                insertQuery.addValue("ecommerce_size_id = :ecommerce_size_id", sizeRecord.getECommerceSizeId());
                insertQuery.addValue("size_value = :size_value", sizeRecord.getValue());
                insertQuery.addValue("size_reference = :size_reference", sizeRecord.getReference());
                named.update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        List<Long> productIds = Lists.newArrayList();

        UpdateQuery updateQuery = null;

        // Stock Reduce Variant
        for (Map.Entry<Long, Integer> requestVariant : requestVariants.entrySet()) {
            selectQuery = new SelectQuery("ecommerce_product_variant");
            selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", requestVariant.getKey());
            ECommerceProductVariant variantRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProductVariant.class);
            Long productId = variantRecord.getECommerceProductId();
            if (!productIds.contains(productId)) {
                productIds.add(productId);
            }
            Integer quantity = variantRecord.getQuantity();
            Integer remainQuantity = (quantity == null ? 0 : quantity) - requestVariant.getValue();
            updateQuery = new UpdateQuery("ecommerce_product_variant");
            updateQuery.addValue("quantity = :quantity", remainQuantity);
            updateQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", requestVariant.getKey());
            named.update(updateQuery.toSQL(), updateQuery.getParam());
        }

        // Product Stock Re-Calculation
        for (Long productId : productIds) {
            selectQuery = new SelectQuery("ecommerce_product_variant");
            selectQuery.addField("sum(quantity)");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", productId);
            Integer quantity = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            updateQuery = new UpdateQuery("ecommerce_product");
            updateQuery.addValue("quantity = :quantity", quantity);
            updateQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", productId);
            named.update(updateQuery.toSQL(), updateQuery.getParam());
        }

        updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("shipping_price = :shipping_price", shippingPriceAddon);
        updateQuery.addValue("total = :total", totalPrice);
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderId);
        named.update(updateQuery.toSQL(), updateQuery.getParam());

        insertQuery = new InsertQuery("ecommerce_cart");
        insertQuery.addValue("ecommerce_cart = :ecommerce_cart", Platform.randomUUIDLong());
        insertQuery.addValue("user_id = :user_id", currentUser.getUserId());
        named.update(insertQuery.toSQL(), insertQuery.getParam());

        DeleteQuery deleteQuery = null;

        deleteQuery = new DeleteQuery("ecommerce_cart");
        deleteQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getECommerceCartId());
        named.update(deleteQuery.toSQL(), deleteQuery.getParam());

        deleteQuery = new DeleteQuery("ecommerce_cart_product_item");
        deleteQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getECommerceCartId());
        named.update(deleteQuery.toSQL(), deleteQuery.getParam());

        ResponseBody responseBody = new ResponseBody();

        selectQuery = new SelectQuery("ecommerce_order");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderId);

        ECommerceOrder orderRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceOrder.class);

        selectQuery = new SelectQuery("ecommerce_order_item");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderId);

        List<ECommerceOrderItem> orderItemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceOrderItem.class);

        responseBody.id = orderRecord.getECommerceOrderId();
        responseBody.remoteId = responseBody.id;
        responseBody.dateCreated = datetimeFormat.format(orderRecord.getDateCreated());
        responseBody.status = orderRecord.getOrderStatus();
        responseBody.total = orderRecord.getTotal();
        responseBody.totalFormatted = priceFormat.format(responseBody.total);
        responseBody.shippingName = orderRecord.getShippingName();
        responseBody.shippingPrice = orderRecord.getShippingPrice();
        responseBody.shippingPriceFormatted = priceFormat.format(responseBody.shippingPrice);
        responseBody.currency = currency;
        responseBody.shippingType = orderRecord.getECommerceShippingId();
        responseBody.paymentType = orderRecord.getECommercePaymentId();
        responseBody.name = orderRecord.getName();
        responseBody.street = orderRecord.getStreet();
        responseBody.houseNumber = orderRecord.getHouseNumber();
        responseBody.city = orderRecord.getCity();
        responseBody.zip = orderRecord.getZip();
        responseBody.email = orderRecord.getEmail();
        responseBody.phone = orderRecord.getPhone();
        responseBody.note = orderRecord.getNote();
        responseBody.region = null;

        responseBody.products = Lists.newArrayList();
        for (ECommerceOrderItem orderItemRecord : orderItemRecords) {
            CartProductItemVO item = new CartProductItemVO();
            item.setId(orderItemRecord.getECommerceOrderId());
            item.setExpiration(0);
            item.setQuantity(orderItemRecord.getQuantity());
            item.setRemoteId(0L);
            item.setTotalItemPrice(orderItemRecord.getTotalPrice());
            item.setTotalItemPriceFormatted(priceFormat.format(item.getTotalItemPrice()));
            CartProductItemVariantVO variant = new CartProductItemVariantVO();
            item.setVariant(variant);
            variant.setRemoteId(0L);
            variant.setId(orderItemRecord.getECommerceProductVariantId());
            variant.setRemoteId(0l);
            variant.setProductId(orderItemRecord.getECommerceProductId());
            variant.setUrl(orderItemRecord.getProductUrl());
            variant.setName(orderItemRecord.getProductName());
            variant.setPrice(orderItemRecord.getProductPrice());
            variant.setPriceFormatted(priceFormat.format(variant.getPrice()));
            variant.setCategory(orderItemRecord.getECommerceCategoryId());
            variant.setDiscountPrice(orderItemRecord.getProductDiscountPrice());
            if (variant.getDiscountPrice() != null) {
                variant.setDiscountPriceFormatted(priceFormat.format(variant.getDiscountPrice()));
            }
            variant.setCurrency(currency);
            variant.setCode(orderItemRecord.getVariantReference());
            variant.setDescription(orderItemRecord.getProductDescription());
            variant.setMainImage(orderItemRecord.getProductMainImage());
            ProductColorVO color = new ProductColorVO();
            variant.setColor(color);
            color.setRemoteId(0L);
            color.setId(orderItemRecord.getECommerceColorId());
            color.setCode(orderItemRecord.getColorCode());
            color.setValue(orderItemRecord.getColorValue());
            color.setImg(orderItemRecord.getColorImg());
            ProductSizeVO size = new ProductSizeVO();
            variant.setSize(size);
            size.setId(orderItemRecord.getECommerceSizeId());
            size.setRemoteId(0L);
            size.setValue(orderItemRecord.getSizeValue());
            responseBody.products.add(item);
        }
        return ResponseEntity.ok(responseBody);
    }

    public static class RequestBody {

        @Expose
        @SerializedName("shipping_type")
        Integer shippingType;

        @Expose
        @SerializedName("payment_type")
        Integer paymentType;

        @Expose
        @SerializedName("name")
        String name;

        @Expose
        @SerializedName("street")
        String street;

        @Expose
        @SerializedName("house_number")
        String houseNumber;

        @Expose
        @SerializedName("city")
        String city;

        @Expose
        @SerializedName("zip")
        String zip;

        @Expose
        @SerializedName("email")
        String email;

        @Expose
        @SerializedName("phone")
        String phone;

        @Expose
        @SerializedName("note")
        String note;

    }

    public static class ResponseBody {

        @Expose
        @SerializedName("id")
        Long id;

        @Expose
        @SerializedName("remote_id")
        Long remoteId = 0l;

        @Expose
        @SerializedName("date_created")
        String dateCreated;

        @Expose
        @SerializedName("status")
        String status;

        @Expose
        @SerializedName("total")
        Double total;

        @Expose
        @SerializedName("total_formatted")
        String totalFormatted;

        @Expose
        @SerializedName("shipping_name")
        String shippingName;

        @Expose
        @SerializedName("shipping_price")
        Double shippingPrice;

        @Expose
        @SerializedName("shipping_price_formatted")
        String shippingPriceFormatted;

        @Expose
        @SerializedName("currency")
        String currency;

        @Expose
        @SerializedName("shipping_type")
        Long shippingType;

        @Expose
        @SerializedName("payment_type")
        Long paymentType;

        @Expose
        @SerializedName("name")
        String name;

        @Expose
        @SerializedName("street")
        String street;

        @Expose
        @SerializedName("house_number")
        String houseNumber;

        @Expose
        @SerializedName("city")
        String city;

        @Expose
        @SerializedName("region")
        RegionVO region;

        @Expose
        @SerializedName("zip")
        String zip;

        @Expose
        @SerializedName("items")
        List<CartProductItemVO> products;

        @Expose
        @SerializedName("email")
        String email;

        @Expose
        @SerializedName("phone")
        String phone;

        @Expose
        @SerializedName("note")
        String note;

    }

}
