package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.cart.*;
import com.angkorteam.ecommerce.mobile.product.ProductColor;
import com.angkorteam.ecommerce.mobile.product.ProductSize;
import com.angkorteam.ecommerce.model.*;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
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
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class CartServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/cart", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        EcommerceCart cartRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCart.class);


        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));

        Cart data = new Cart();
        data.setId(cartRecord.getEcommerceCartId());
        data.setCurrency(currency);

        Integer productCount = 0;
        Double totalPrice = 0d;

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getEcommerceCartId());
        List<EcommerceCartProductItem> itemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCartProductItem.class);

        if (itemRecords != null && !itemRecords.isEmpty()) {
            List<CartProductItem> items = Lists.newArrayList();
            data.setItems(items);
            for (EcommerceCartProductItem itemRecord : itemRecords) {

                selectQuery = new SelectQuery("ecommerce_product");
                selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", itemRecord.getEcommerceProductId());
                EcommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProduct.class);

                selectQuery = new SelectQuery("ecommerce_product_variant");
                selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", itemRecord.getEcommerceProductVariantId());
                EcommerceProductVariant variantRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProductVariant.class);

                selectQuery = new SelectQuery("ecommerce_category");
                selectQuery.addWhere("ecommerce_category_id = :ecommerce_category_id", productRecord.getEcommerceCategoryId());
                EcommerceCategory categoryRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCategory.class);

                Double price = productRecord.getPrice() == null ? 0d : productRecord.getPrice();
                Integer quantity = itemRecord.getQuantity() == null ? 0 : itemRecord.getQuantity();
                productCount = quantity + productCount;
                totalPrice = totalPrice + (quantity * price);
                CartProductItem item = new CartProductItem();
                item.setId(itemRecord.getEcommerceCartProductItemId());
                item.setQuantity(itemRecord.getQuantity());
                item.setRemoteId(0L);
                item.setTotalItemPrice(quantity * price);
                item.setTotalItemPriceFormatted(priceFormat.format(item.getTotalItemPrice()));
                items.add(item);
                CartProductItemVariant variant = new CartProductItemVariant();
                item.setVariant(variant);
                variant.setId(variantRecord.getEcommerceProductVariantId());
                variant.setRemoteId(0L);
                variant.setProductId(productRecord.getEcommerceProductId());
                variant.setUrl(productRecord.getUrl());
                variant.setName(productRecord.getName());
                variant.setPrice(productRecord.getPrice());
                variant.setPriceFormatted(priceFormat.format(variant.getPrice()));
                variant.setCategory(categoryRecord.getEcommerceCategoryId());
                if (productRecord.getDiscountPrice() != null) {
                    variant.setDiscountPrice(productRecord.getDiscountPrice());
                    variant.setDiscountPriceFormatted(priceFormat.format(variant.getDiscountPrice()));
                }
                variant.setCurrency(currency);
                variant.setCode(variantRecord.getReference());
                variant.setDescription(productRecord.getDescription());
                selectQuery = new SelectQuery("platform_file");
                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) mainImage");
                selectQuery.addWhere("platform_file.platform_file_id = :platform_file_id", productRecord.getMainImagePlatformFileId());
                variant.setMainImage(named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class));
                selectQuery = new SelectQuery("ecommerce_color");
                selectQuery.addJoin(JoinType.LeftJoin, "platform_file", "ecommerce_color.img_platform_file_id = platform_file.platform_file_id");
                selectQuery.addField("ecommerce_color.ecommerce_color_id AS id");
                selectQuery.addField("ecommerce_color.ecommerce_color_id AS remoteId");
                selectQuery.addField("ecommerce_color.code AS code");
                selectQuery.addField("ecommerce_color.value");
                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) AS img");
                selectQuery.addWhere("ecommerce_color.ecommerce_color_id = :ecommerce_color_id", variantRecord.getEcommerceColorId());
                variant.setColor(named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ProductColor.class));
                selectQuery = new SelectQuery("ecommerce_size");
                selectQuery.addField("ecommerce_size_id AS id");
                selectQuery.addField("ecommerce_size_id AS remoteId");
                selectQuery.addField("value");
                selectQuery.addWhere("ecommerce_size_id = :ecommerce_size_id", variantRecord.getEcommerceSizeId());
                variant.setSize(named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ProductSize.class));
            }
        }

        data.setDiscounts(Lists.newArrayList());
        selectQuery = new SelectQuery("ecommerce_cart_discount_item");
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getEcommerceCartId());
        List<EcommerceCartDiscountItem> discountItems = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCartDiscountItem.class);
        if (discountItems != null && !discountItems.isEmpty()) {
            for (EcommerceCartDiscountItem discountItem : discountItems) {
                selectQuery = new SelectQuery("ecommerce_discount");
                selectQuery.addWhere("ecommerce_discount_id = :ecommerce_discount_id", discountItem.getEcommerceDiscountId());
                EcommerceDiscount ecommerceDiscount = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceDiscount.class);
                CartDiscountItem cartDiscountItem = new CartDiscountItem();
                cartDiscountItem.setId(discountItem.getEcommerceCartDiscountItemId());
                cartDiscountItem.setQuantity(1);
                data.getDiscounts().add(cartDiscountItem);
                Discount discount = new Discount();
                cartDiscountItem.setDiscount(discount);
                discount.setId(ecommerceDiscount.getEcommerceDiscountId());
                discount.setMinCartAmount(String.valueOf(ecommerceDiscount.getMinCartAmount()));
                discount.setName(ecommerceDiscount.getName());
                discount.setType(ecommerceDiscount.getType());
                discount.setValue(String.valueOf(ecommerceDiscount.getValue()));
                discount.setValueFormatted(priceFormat.format(ecommerceDiscount.getValue()));
            }
        }

        data.setProductCount(productCount);
        data.setTotalPrice(totalPrice);
        data.setTotalPriceFormatted(priceFormat.format(totalPrice));

        return ResponseEntity.ok(data);
    }

}
