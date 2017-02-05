package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.EcommerceCart;
import com.angkorteam.ecommerce.model.EcommerceDiscount;
import com.angkorteam.framework.jdbc.InsertQuery;
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
        selectQuery = new SelectQuery("ecommerce_discount");
        selectQuery.addWhere("lower(name) = lower(:name)", "name", code);
        EcommerceDiscount ecommerceDiscount = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceDiscount.class);
        if (ecommerceDiscount == null) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("body", new String[]{"discount is not available"});
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        EcommerceCart ecommerceCart = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCart.class);

        selectQuery = new SelectQuery("ecommerce_cart_discount_item");
        selectQuery.addField("count(*)");
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", ecommerceCart.getEcommerceCartId());
        selectQuery.addWhere("ecommerce_discount_id = :ecommerce_discount_id", ecommerceDiscount.getEcommerceDiscountId());

        int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
        if (count == 0) {
            InsertQuery insertQuery = new InsertQuery("ecommerce_cart_discount_item");
            insertQuery.addValue("ecommerce_cart_discount_item_id = :ecommerce_cart_discount_item_id", Platform.randomUUIDLong("ecommerce_cart_discount_item"));
            insertQuery.addValue("ecommerce_cart_id = :ecommerce_cart_id", ecommerceCart.getEcommerceCartId());
            insertQuery.addValue("ecommerce_discount_id = :ecommerce_discount_id", ecommerceDiscount.getEcommerceDiscountId());
            insertQuery.addValue("quantity = :quantity", 1);
            named.update(insertQuery.toSQL(), insertQuery.getParam());
        }
        Map<String, Object> data = Maps.newHashMap();
        data.put("body", new String[]{"your discount is applied"});
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
