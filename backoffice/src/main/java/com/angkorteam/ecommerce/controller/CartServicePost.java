package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.ECommerceCart;
import com.angkorteam.ecommerce.model.ECommerceCartProductItem;
import com.angkorteam.ecommerce.model.ECommerceProduct;
import com.angkorteam.ecommerce.model.ECommerceProductVariant;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
import com.angkorteam.platform.model.PlatformUser;
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

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class CartServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/cart", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartItemServicePut.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        InputStreamReader stream = new InputStreamReader(request.getInputStream());
        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);

        LOGGER.info(this.gson.toJson(requestBody));

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("user_id = :user_id", currentUser.getUserId());
        ECommerceCart cartRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCart.class);

        selectQuery = new SelectQuery("ecommerce_product_variant");
        selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", requestBody.productVariantId);
        ECommerceProductVariant variantRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProductVariant.class);

        selectQuery = new SelectQuery("ecommerce_product");
        selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", variantRecord.getECommerceProductId());
        ECommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProduct.class);

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", variantRecord.getECommerceProductVariantId());
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getECommerceCartId());

        Long id = null;
        ECommerceCartProductItem itemRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCartProductItem.class);
        if (itemRecord == null) {
            id = Platform.randomUUIDLong();
            InsertQuery insertQuery = new InsertQuery("ecommerce_cart_product_item");
            insertQuery.addValue("ecommerce_cart_product_item_id = :ecommerce_cart_product_item", id);
            insertQuery.addValue("quantity = :quantity", 1);
            insertQuery.addValue("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getECommerceCartId());
            insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant_id", variantRecord.getECommerceProductVariantId());
            insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productRecord.getECommerceProductId());
            named.update(insertQuery.toSQL(), insertQuery.getParam());
        } else {
            id = itemRecord.getECommerceCartProductItemId();
        }

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_cart_product_item");
        updateQuery.addValue("quantity = quantity + 1");
        updateQuery.addWhere("ecommerce_cart_product_item_id = :ecommerce_cart_product_item_id", id);
        named.update(updateQuery.toSQL(), updateQuery.getParam());

        ResponseBody responseBody = new ResponseBody();
        responseBody.id = id;
        return ResponseEntity.ok(responseBody);
    }

    public static class RequestBody {

        @Expose
        @SerializedName("product_variant_id")
        String productVariantId;

    }

    public static class ResponseBody {

        @Expose
        @SerializedName("id")
        Long id;

    }

}
