package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.EcommerceCart;
import com.angkorteam.ecommerce.model.EcommerceCartProductItem;
import com.angkorteam.ecommerce.model.EcommerceProduct;
import com.angkorteam.ecommerce.model.EcommerceProductVariant;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
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
import java.util.Map;

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
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartServicePost.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);

        RequestBody requestBody = this.gson.fromJson(request.getReader(), RequestBody.class);

        LOGGER.info(this.gson.toJson(requestBody));

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        EcommerceCart cartRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCart.class);

        selectQuery = new SelectQuery("ecommerce_product_variant");
        selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", requestBody.getProductVariantId());
        EcommerceProductVariant variantRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProductVariant.class);

        selectQuery = new SelectQuery("ecommerce_product");
        selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", variantRecord.getEcommerceProductId());
        EcommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProduct.class);

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", variantRecord.getEcommerceProductVariantId());
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getEcommerceCartId());

        Long id = null;
        EcommerceCartProductItem itemRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCartProductItem.class);
        if (itemRecord == null) {
            id = Platform.randomUUIDLong();
            InsertQuery insertQuery = new InsertQuery("ecommerce_cart_product_item");
            insertQuery.addValue("ecommerce_cart_product_item_id = :ecommerce_cart_product_item", id);
            insertQuery.addValue("quantity = :quantity", 1);
            insertQuery.addValue("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getEcommerceCartId());
            insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant_id", variantRecord.getEcommerceProductVariantId());
            insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productRecord.getEcommerceProductId());
            named.update(insertQuery.toSQL(), insertQuery.getParam());
        } else {
            id = itemRecord.getEcommerceCartProductItemId();
            UpdateQuery updateQuery = new UpdateQuery("ecommerce_cart_product_item");
            updateQuery.addValue("quantity = quantity + 1");
            updateQuery.addWhere("ecommerce_cart_product_item_id = :ecommerce_cart_product_item_id", id);
            named.update(updateQuery.toSQL(), updateQuery.getParam());
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("id", id);
        return ResponseEntity.ok(data);
    }

    public static class RequestBody {

        @Expose
        @SerializedName("product_variant_id")
        private String productVariantId;

        public String getProductVariantId() {
            return productVariantId;
        }

        public void setProductVariantId(String productVariantId) {
            this.productVariantId = productVariantId;
        }

    }

}
