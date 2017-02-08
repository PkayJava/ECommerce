package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.EcommerceCart;
import com.angkorteam.ecommerce.model.EcommerceDiscount;
import com.angkorteam.ecommerce.model.EcommerceDiscountCoupon;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class CartDiscountsServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartDiscountsServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/cart/discounts", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("shop") String shop) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        if (!Platform.hasAccess(request, CartDiscountsServicePost.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBody = this.gson.fromJson(request.getReader(), type);

        String code = requestBody.get("code");

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery("ecommerce_discount_coupon");
        selectQuery.addWhere("code = :code", code);
        EcommerceDiscountCoupon ecommerceDiscountCoupon = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceDiscountCoupon.class);
        if (ecommerceDiscountCoupon == null || ecommerceDiscountCoupon.getUsed()) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("body", new String[]{"discount is not available"});
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        selectQuery = new SelectQuery("ecommerce_discount");
        selectQuery.addWhere("ecommerce_discount_id = :ecommerce_discount_id", ecommerceDiscountCoupon.getEcommerceDiscountId());
        EcommerceDiscount ecommerceDiscount = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceDiscount.class);
        if (!ecommerceDiscount.getEnabled()) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("body", new String[]{"discount is not available"});
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Date now = new Date();
        if (now.before(ecommerceDiscount.getStartDate()) || now.after(ecommerceDiscount.getEndDate())) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("body", new String[]{"discount is not available"});
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        EcommerceCart ecommerceCart = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCart.class);
        jdbcTemplate.update("update ecommerce_cart set ecommerce_discount_id = ?, ecommerce_discount_coupon_id = ? where ecommerce_cart_id = ?", ecommerceDiscountCoupon.getEcommerceDiscountId(), ecommerceDiscountCoupon.getEcommerceDiscountCouponId(), ecommerceCart.getEcommerceCartId());

        Map<String, Object> data = Maps.newHashMap();
        data.put("body", new String[]{"your discount is applied"});
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
