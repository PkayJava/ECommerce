package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.EcommerceProduct;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class WishlistIsInWishlistServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistIsInWishlistServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/wishlist/is-in-wishlist/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, WishlistIsInWishlistServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_product");
        selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", id);
        EcommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProduct.class);

        selectQuery = new SelectQuery("ecommerce_wishlist");
        selectQuery.addField("count(*)");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", productRecord.getEcommerceProductId());

        int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
        Map<String, Object> data = Maps.newHashMap();
        data.put("is_in_wishlist", count > 0);
        data.put("wishlist_product_id", String.valueOf(id));
        return ResponseEntity.ok(data);
    }

}
