package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.ECommerceCart;
import com.angkorteam.ecommerce.model.ECommerceCartProductItem;
import com.angkorteam.ecommerce.model.ECommerceProduct;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
import com.angkorteam.platform.model.PlatformUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartInfoServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser user = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("user_id = :user_id", user.getUserId());
        ECommerceCart cartRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCart.class);

        int productCount = 0;
        double totalPrice = 0d;

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_cart_id = ?", cartRecord.getECommerceCartId());
        List<ECommerceCartProductItem> itemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCartProductItem.class);

        for (ECommerceCartProductItem itemRecord : itemRecords) {
            selectQuery = new SelectQuery("ecommerce_product");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", itemRecord.getECommerceProductId());
            ECommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProduct.class);
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

        ResponseBody response = new ResponseBody();
        response.productCount = productCount;
        response.totalPrice = totalPrice;
        response.totalPriceFormatted = priceFormat.format(totalPrice);
        return ResponseEntity.ok(response);
    }

    public static class ResponseBody {

        @Expose
        @SerializedName("product_count")
        private int productCount;

        @Expose
        @SerializedName("total_price")
        private double totalPrice;

        @Expose
        @SerializedName("total_price_formatted")
        private String totalPriceFormatted;

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getTotalPriceFormatted() {
            return totalPriceFormatted;
        }

        public void setTotalPriceFormatted(String totalPriceFormatted) {
            this.totalPriceFormatted = totalPriceFormatted;
        }
    }

}
