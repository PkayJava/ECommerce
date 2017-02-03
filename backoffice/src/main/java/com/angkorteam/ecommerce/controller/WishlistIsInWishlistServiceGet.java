//package com.angkorteam.ecommerce.controller;
//
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
//import com.angkorteam.platform.Spring;
//import com.google.gson.Gson;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by socheatkhauv on 28/1/17.
// */
//@Controller
//public class WishlistIsInWishlistServiceGet {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistIsInWishlistServiceGet.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(path = "/{shop}/wishlist/is-in-wishlist/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
////        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
////        if (!ECommerce.hasAccess(connection, request, CartDeliveryInfoServiceGet.class)) {
////            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
////        }
////        String authorization = request.getHeader("Authorization");
////        byte[] base64Token = authorization.substring(6).getBytes("UTF-8");
////        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(base64Token);
////        String token = new String(decoded, "UTF-8");
////        Integer delim = token.indexOf(":");
////        String accessToken = token.substring(0, delim);
////        SelectQuery selectQuery = null;
////        selectQuery = new SelectQuery("user");
////        selectQuery.addWhere("access_token = ?", accessToken);
////        User userRecord = selectQuery.queryForObject(connection, new UserMapper());
////
////        selectQuery = new SelectQuery("ecommerce_product");
////        selectQuery.addWhere("ecommerce_product_id = ?", id);
////        ECommerceProduct productRecord = selectQuery.queryForObject(connection, new ECommerceProductMapper());
////        selectQuery = new SelectQuery("ecommerce_wishlist");
////        selectQuery.addWhere("user_id = ?", userRecord.getUserId());
////        ECommerceWishlist wishlistRecord = selectQuery.queryForObject(connection, new ECommerceWishlistMapper());
////
////        selectQuery = new SelectQuery("ecommerce_wishlist_item");
////        selectQuery.addField("count(*)");
////        selectQuery.addWhere("ecommerce_wishlist_id = ?", wishlistRecord.getECommerceWishlistId());
////        selectQuery.addWhere("ecommerce_product_id = ?", productRecord.getECommerceProductId());
////        int count = selectQuery.queryForObject(connection, int.class);
////        ResponseBody response = new ResponseBody();
////        response.inWishlist = count > 0;
////        response.wishlistProductId = String.valueOf(id);
////        return ResponseEntity.ok(response);
//        return null;
//    }
//
//    public static class ResponseBody {
//
//        @Expose
//        @SerializedName("is_in_wishlist")
//        boolean inWishlist;
//
//        @Expose
//        @SerializedName("wishlist_product_id")
//        String wishlistProductId;
//    }
//
//
//}
