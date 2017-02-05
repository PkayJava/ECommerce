package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.cart.CartProductItem;
import com.angkorteam.ecommerce.mobile.cart.CartProductItemVariant;
import com.angkorteam.ecommerce.mobile.order.Order;
import com.angkorteam.ecommerce.mobile.product.ProductColor;
import com.angkorteam.ecommerce.mobile.product.ProductSize;
import com.angkorteam.ecommerce.model.EcommerceOrder;
import com.angkorteam.ecommerce.model.EcommerceOrderItem;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Lists;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class OrdersSingleServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersSingleServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/orders/{id}")
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        if (!Platform.hasAccess(request, OrdersSingleServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery("ecommerce_order");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", id);
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());

        EcommerceOrder orderRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceOrder.class);

        Order data = new Order();
        data.setId(orderRecord.getEcommerceOrderId());
        data.setRemoteId(String.valueOf(data.getId()));
        data.setDateCreated(datetimeFormat.format(orderRecord.getDateCreated()));
        data.setStatus(orderRecord.getOrderStatus());
        data.setShippingName(orderRecord.getShippingName());
        data.setShippingPrice(orderRecord.getShippingPrice());
        data.setShippingPriceFormatted(priceFormat.format(data.getShippingPrice()));
        data.setCurrency(currency);
        data.setShippingType(orderRecord.getEcommerceShippingId());
        data.setPaymentType(orderRecord.getEcommercePaymentId());
        data.setName(orderRecord.getName());
        data.setStreet(orderRecord.getStreet());
        data.setHouseNumber(orderRecord.getHouseNumber());
        data.setCity(orderRecord.getCity());
        data.setZip(orderRecord.getZip());
        data.setEmail(orderRecord.getEmail());
        data.setPhone(orderRecord.getPhone());
        data.setNote(orderRecord.getNote());
        data.setTotal(orderRecord.getTotal() + data.getShippingPrice() + orderRecord.getPaymentPrice());
        data.setTotalFormatted(priceFormat.format(data.getTotal()));

        selectQuery = new SelectQuery("ecommerce_order_item");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderRecord.getEcommerceOrderId());

        List<EcommerceOrderItem> orderItemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceOrderItem.class);
        List<CartProductItem> products = Lists.newArrayList();
        data.setProducts(products);
        for (EcommerceOrderItem orderItemRecord : orderItemRecords) {
            CartProductItem item = new CartProductItem();
            item.setId(orderItemRecord.getEcommerceOrderId());
            item.setExpiration(0);
            item.setQuantity(orderItemRecord.getQuantity());
            item.setRemoteId(0L);
            item.setTotalItemPrice(orderItemRecord.getTotalPrice());
            item.setTotalItemPriceFormatted(priceFormat.format(item.getTotalItemPrice()));
            CartProductItemVariant variant = new CartProductItemVariant();
            item.setVariant(variant);
            variant.setId(orderItemRecord.getEcommerceProductVariantId());
            variant.setRemoteId(0L);
            variant.setProductId(orderItemRecord.getEcommerceProductId());
            variant.setUrl(orderItemRecord.getProductUrl());
            variant.setName(orderItemRecord.getProductName());
            variant.setPrice(orderItemRecord.getProductPrice());
            variant.setPriceFormatted(priceFormat.format(variant.getPrice()));
            variant.setCategory(orderItemRecord.getEcommerceCategoryId());
            variant.setDiscountPrice(orderItemRecord.getProductDiscountPrice());
            if (variant.getDiscountPrice() != null) {
                variant.setDiscountPriceFormatted(priceFormat.format(variant.getDiscountPrice()));
            }
            variant.setCurrency(currency);
            variant.setCode(orderItemRecord.getVariantReference());
            variant.setDescription(orderItemRecord.getProductDescription());
            variant.setMainImage(orderItemRecord.getProductMainImage());
            ProductColor color = new ProductColor();
            variant.setColor(color);
            color.setRemoteId(0L);
            color.setId(orderItemRecord.getEcommerceColorId());
            color.setCode(orderItemRecord.getColorCode());
            color.setValue(orderItemRecord.getColorValue());
            color.setImg(orderItemRecord.getColorImg());
            ProductSize size = new ProductSize();
            variant.setSize(size);
            size.setId(orderItemRecord.getEcommerceSizeId());
            size.setRemoteId(0L);
            size.setValue(orderItemRecord.getSizeValue());
            products.add(item);
        }
        return ResponseEntity.ok(data);
    }

}
