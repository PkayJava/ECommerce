package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.cart.CartInfo;
import com.angkorteam.ecommerce.model.EcommerceCart;
import com.angkorteam.ecommerce.model.EcommerceCartProductItem;
import com.angkorteam.ecommerce.model.EcommerceProduct;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class CartInfoServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartInfoServiceGet.class);

    @RequestMapping(path = "/{shop}/cart/info", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartInfoServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        EcommerceCart cartRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCart.class);

        int productCount = 0;
        double totalPrice = 0d;

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getEcommerceCartId());
        List<EcommerceCartProductItem> itemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCartProductItem.class);

        for (EcommerceCartProductItem itemRecord : itemRecords) {
            selectQuery = new SelectQuery("ecommerce_product");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", itemRecord.getEcommerceProductId());
            EcommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProduct.class);
            double price = 0d;
            if (productRecord.getPrice() != null) {
                price = productRecord.getPrice();
            }
            if (productRecord.getDiscountPrice() != null) {
                double discountPrice = productRecord.getDiscountPrice();
                if (discountPrice < price) {
                    price = discountPrice;
                }
            }
            int quantity = itemRecord.getQuantity() == null ? 0 : itemRecord.getQuantity();
            productCount = quantity + productCount;
            totalPrice = totalPrice + (quantity * price);
        }

        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));

        CartInfo data = new CartInfo();
        data.setProductCount(productCount);
        data.setTotalPrice(totalPrice);
        data.setTotalPriceFormatted(priceFormat.format(totalPrice));
        return ResponseEntity.ok(data);
    }

}
