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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class WishlistSingleServiceDelete {

    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistSingleServiceDelete.class);


    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/wishlist/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
//        Integer wishlist = pathVariables.get("id") as Integer
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
//        this.jdbcTemplate.update("DELETE FROM ecommerce_wishlist_item WHERE entity_id = ? AND ecommerce_wishlist_id = ?", wishlist, wishlistRecord.get("ecommerce_wishlist_id"))
//
//        ResponseBody responseBody = new ResponseBody()
//        responseBody.id = wishlist
//
//        return ResponseEntity.ok(responseBody)
        return null;
    }

    public static class ResponseBody {

        @Expose
        @SerializedName("id")
        Integer id;

    }

}