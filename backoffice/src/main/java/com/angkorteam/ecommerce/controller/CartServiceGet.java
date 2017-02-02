package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.*;
import com.angkorteam.ecommerce.vo.*;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Lists;
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
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartItemServicePut.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("user_id = :user_id", currentUser.getUserId());
        ECommerceCart cartRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCart.class);


        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));

        ResponseBody cart = new ResponseBody();
        cart.id = cartRecord.getECommerceCartId();
        cart.currency = currency;

        Integer productCount = 0;
        Double totalPrice = 0d;

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getECommerceCartId());
        List<ECommerceCartProductItem> itemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCartProductItem.class);

        if (itemRecords != null && !itemRecords.isEmpty()) {
            cart.items = Lists.newArrayList();
            for (ECommerceCartProductItem itemRecord : itemRecords) {
                selectQuery = new SelectQuery("ecommerce_product");
                selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", itemRecord.getECommerceProductId());
                ECommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProduct.class);
                selectQuery = new SelectQuery("ecommerce_product_variant");
                selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", itemRecord.getECommerceProductVariantId());
                ECommerceProductVariant variantRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProductVariant.class);
                selectQuery = new SelectQuery("ecommerce_category");
                selectQuery.addWhere("ecommerce_category_id = :ecommerce_category_id", productRecord.getECommerceCategoryId());
                ECommerceCategory categoryRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCategory.class);
                Double price = productRecord.getPrice() == null ? 0d : productRecord.getPrice();
                Integer quantity = itemRecord.getQuantity() == null ? 0 : itemRecord.getQuantity();
                productCount = quantity + productCount;
                totalPrice = totalPrice + (quantity * price);
                CartProductItemVO item = new CartProductItemVO();
                item.setId(itemRecord.getECommerceCartProductItemId());
                item.setQuantity(itemRecord.getQuantity());
                item.setRemoteId(0L);
                item.setTotalItemPrice(quantity * price);
                item.setTotalItemPriceFormatted(priceFormat.format(item.getTotalItemPrice()));
                cart.items.add(item);
                CartProductItemVariantVO variant = new CartProductItemVariantVO();
                item.setVariant(variant);
                variant.setId(variantRecord.getECommerceProductVariantId());
                variant.setRemoteId(0L);
                variant.setProductId(productRecord.getECommerceProductId());
                variant.setUrl(productRecord.getUrl());
                variant.setName(productRecord.getName());
                variant.setPrice(productRecord.getPrice());
                variant.setPriceFormatted(priceFormat.format(variant.getPrice()));
                variant.setCategory(categoryRecord.getECommerceCategoryId());
                if (productRecord.getDiscountPrice() != null) {
                    variant.setDiscountPrice(productRecord.getDiscountPrice());
                    variant.setDiscountPriceFormatted(priceFormat.format(variant.getDiscountPrice()));
                }
                variant.setCurrency(currency);
                variant.setCode(variantRecord.getReference());
                variant.setDescription(productRecord.getDescription());
                selectQuery = new SelectQuery("file");
                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', file.path, '/', file.name) mainImage");
                selectQuery.addWhere("file.file_id = :file_id", productRecord.getMainImageFileId());
                variant.setMainImage(named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class));
                selectQuery = new SelectQuery("ecommerce_color");
                selectQuery.addJoin(JoinType.LeftJoin, "file", "ecommerce_color.img_file_id = file.file_id");
                selectQuery.addField("ecommerce_color.ecommerce_color_id AS id");
                selectQuery.addField("ecommerce_color.code AS code");
                selectQuery.addField("ecommerce_color.value");
                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', file.path, '/', file.name) AS img");
                selectQuery.addWhere("ecommerce_color.ecommerce_color_id = :ecommerce_color_id", variantRecord.getECommerceColorId());
                variant.setColor(named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ProductColorVO.class));
                selectQuery = new SelectQuery("ecommerce_size");
                selectQuery.addField("ecommerce_size_id AS id");
                selectQuery.addField("value");
                selectQuery.addWhere("ecommerce_size_id = ?", variantRecord.getECommerceSizeId());
                variant.setSize(named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ProductSizeVO.class));
            }
        }

        cart.productCount = productCount;
        cart.totalPrice = totalPrice;
        cart.totalPriceFormatted = "$" + totalPrice;

        return ResponseEntity.ok(cart);
    }

    public static class ResponseBody {

        @Expose
        @SerializedName("id")
        Long id;

        @Expose
        @SerializedName("product_count")
        Integer productCount;

        @Expose
        @SerializedName("total_price")
        Double totalPrice;

        @Expose
        @SerializedName("total_price_formatted")
        String totalPriceFormatted;

        @Expose
        @SerializedName("currency")
        String currency;

        @Expose
        @SerializedName("items")
        List<CartProductItemVO> items;

        @Expose
        @SerializedName("discounts")
        List<CartDiscountItemVO> discounts;

    }

}
