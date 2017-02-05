package com.angkorteam.ecommerce.controller;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class CartDiscountsSingleServiceDelete {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartDiscountsSingleServiceDelete.class);

    @RequestMapping(path = "/{shop}/cart/discounts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        if (!Platform.hasAccess(request, CartDiscountsSingleServiceDelete.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        Map<String, Object> error = Maps.newHashMap();
        error.put("body", new String[]{"discount is not available"});
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
