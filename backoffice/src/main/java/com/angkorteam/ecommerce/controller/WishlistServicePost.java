package com.angkorteam.ecommerce.controller;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
//        InputStreamReader stream = new InputStreamReader(request.getInputStream())
//        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class)
//
//        String authorization = request.getHeader("Authorization")
//        byte[] base64Token = authorization.substring(6).getBytes("UTF-8")
//        byte[] decoded = Base64.decodeBase64(base64Token)
//        String token = new String(decoded, "UTF-8")
//        Integer delim = token.indexOf(":")
//        String accessToken = token.substring(0, delim)
//        Map<String, Object> userRecord = this.jdbcTemplate.queryForMap("SELECT * FROM user WHERE access_token = ?", accessToken)
//
//        Map<String, Object> wishlistRecord = this.jdbcTemplate.queryForMap("SELECT * FROM ecommerce_wishlist WHERE user_id = ?", userRecord.get("user_id"))
//
//        Map<String, Object> variantRecord = this.jdbcTemplate.queryForMap("SELECT * FROM ecommerce_product_variant WHERE entity_id = ?", requestBody.productVariantId)
//
//        int count = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ecommerce_wishlist_item WHERE ecommerce_wishlist_id = ? AND ecommerce_product_id = ?", Integer.class, wishlistRecord.get("ecommerce_wishlist_id"), variantRecord.get("ecommerce_product_id"))
//        if (count == 0) {
//
//            int entityId = 0
//            try {
//                entityId = jdbcTemplate.queryForObject("SELECT entity_id FROM ecommerce_wishlist_item ORDER BY entity_id desc limit 1", Integer.class) + 1
//            } catch (EmptyResultDataAccessException e) {
//                entityId = 1
//            }
//
//            Connection connection = this.sql2o.beginTransaction()
//            connection.withCloseable {
//                Query query = connection.createQuery("INSERT INTO ecommerce_wishlist_item(ecommerce_wishlist_item_id, ecommerce_wishlist_id, entity_id, ecommerce_product_id) VALUES(:ecommerce_wishlist_item_id, :ecommerce_wishlist_id, :entity_id, :ecommerce_product_id)")
//                query.addParameter("ecommerce_wishlist_item_id", system.randomUUIDString())
//                query.addParameter("entity_id", entityId)
//                query.addParameter("ecommerce_product_id", variantRecord.get("ecommerce_product_id"))
//                query.addParameter("ecommerce_wishlist_id", wishlistRecord.get("ecommerce_wishlist_id"))
//                query.executeUpdate()
//                connection.commit()
//            }
//        }
//
//        ResponseBody responseBody = new ResponseBody()
//        responseBody.id = this.jdbcTemplate.queryForObject("SELECT entity_id FROM ecommerce_wishlist_item WHERE ecommerce_wishlist_id = ? AND ecommerce_product_id = ?", Integer.class, wishlistRecord.get("ecommerce_wishlist_id"), variantRecord.get("ecommerce_product_id"))
//        return ResponseEntity.ok(responseBody)
        return null;
    }

    public static class RequestBody {

        @Expose
        @SerializedName("product_variant_id")
        Integer productVariantId;

    }

    public static class ResponseBody {

        @Expose
        @SerializedName("id")
        Integer id;

    }


}