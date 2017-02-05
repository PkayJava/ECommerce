package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.EcommerceProductVariant;
import com.angkorteam.ecommerce.model.EcommerceWishlist;
import com.angkorteam.framework.jdbc.InsertQuery;
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
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class WishlistServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/wishlist", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, WishlistServicePost.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);

        RequestBody requestBody = this.gson.fromJson(request.getReader(), RequestBody.class);

        EcommerceProductVariant variantRecord = jdbcTemplate.queryForObject("SELECT * FROM ecommerce_product_variant WHERE ecommerce_product_variant_id = ?", EcommerceProductVariant.class, requestBody.getProductVariantId());

        EcommerceWishlist wishlist = jdbcTemplate.queryForObject("SELECT * FROM ecommerce_wishlist WHERE platform_user_id = ? AND ecommerce_product_id = ?", EcommerceWishlist.class, currentUser.getPlatformUserId(), variantRecord.getEcommerceProductId());
        Long id = null;
        if (wishlist == null) {
            id = Platform.randomUUIDLong("ecommerce_wishlist");
            InsertQuery insertQuery = new InsertQuery("ecommerce_wishlist");
            insertQuery.addValue("ecommerce_wishlist_id = :ecommerce_wishlist_id", id);
            insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", variantRecord.getEcommerceProductId());
            insertQuery.addValue("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
            named.update(insertQuery.toSQL(), insertQuery.getParam());
        } else {
            id = wishlist.getEcommerceWishlistId();
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("id", id);
        return ResponseEntity.ok(data);
    }

    public static class RequestBody {

        @Expose
        @SerializedName("product_variant_id")
        Long productVariantId;

        public Long getProductVariantId() {
            return productVariantId;
        }

        public void setProductVariantId(Long productVariantId) {
            this.productVariantId = productVariantId;
        }

    }


}