package com.angkorteam.ecommerce.controller;

import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class CartItemServicePut {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartItemServicePut.class);

    @Autowired
    private Gson gson;

    @RequestMapping(path = "/{shop}/cart/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws ServletException, IOException {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);

        if (!Platform.hasAccess(request, CartItemServicePut.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        InputStreamReader stream = new InputStreamReader(request.getInputStream());
        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_cart_product_item");
        updateQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant_id", requestBody.productVariantId);
        updateQuery.addValue("quantity = :quantity", requestBody.quantity);
        updateQuery.addWhere("ecommerce_cart_product_item_id = :ecommerce_cart_product_item_id", id);

        named.update(updateQuery.toSQL(), updateQuery.getParam());

        Map<String, Object> message = Maps.newHashMap();
        message.put("body", new String[]{"OK"});
        return ResponseEntity.ok(message);
    }

    public static class RequestBody {

        @Expose
        @SerializedName("quantity")
        private Integer quantity;

        @Expose
        @SerializedName("product_variant_id")
        private Integer productVariantId;

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getProductVariantId() {
            return productVariantId;
        }

        public void setProductVariantId(Integer productVariantId) {
            this.productVariantId = productVariantId;
        }
    }

}
