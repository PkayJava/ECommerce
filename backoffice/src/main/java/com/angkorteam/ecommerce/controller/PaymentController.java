package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.delivery.Payment;
import com.angkorteam.ecommerce.model.EcommercePayment;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.braintreegateway.BraintreeGateway;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by socheatkhauv on 9/2/17.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @RequestMapping(path = "/paypal/initiate/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> paypalInitiate(HttpServletRequest request, Long paymentId) throws ServletException {
        LOGGER.info("{}", getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        EcommercePayment ecommercePayment = jdbcTemplate.queryForObject("select * from ecommerce_payment where ecommerce_payment_id = ?", EcommercePayment.class, paymentId);
        LOGGER.info("payment type {}", ecommercePayment == null ? "" : ecommercePayment.getType());
        if (ecommercePayment == null || !Payment.TYPE_PAYPAL.equals(ecommercePayment.getType())) {
            throw new ServletException("payment is not available");
        }
        LOGGER.info("payment server param1 {}", ecommercePayment.getServerParam1());
        if (Strings.isNullOrEmpty(ecommercePayment.getServerParam1())) {
            throw new ServletException("payment is not available");
        }
        try {
            BraintreeGateway gateway = new BraintreeGateway(ecommercePayment.getServerParam1());
            String token = gateway.clientToken().generate();
            return ResponseEntity.ok(token);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
