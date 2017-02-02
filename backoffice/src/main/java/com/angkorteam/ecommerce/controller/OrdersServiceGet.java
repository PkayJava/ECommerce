package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.ECommerceOrder;
import com.angkorteam.ecommerce.model.ECommerceOrderItem;
import com.angkorteam.ecommerce.vo.*;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Lists;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class OrdersServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServiceGet.class);

    @RequestMapping(path = "/{shop}/orders", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, CartDeliveryInfoServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        SelectQuery selectQuery = null;

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));

        PlatformUser currentUser = Platform.getCurrentUser(request);

        ResponseBody responseBody = new ResponseBody();

        selectQuery = new SelectQuery("ecommerce_order");
        selectQuery.addWhere("user_id = :user_id", currentUser.getUserId());

        List<ECommerceOrder> orderRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceOrder.class);

        if (orderRecords != null && !orderRecords.isEmpty()) {
            responseBody.orders = Lists.newArrayList();
            for (ECommerceOrder orderRecord : orderRecords) {
                OrderVO order = new OrderVO();
                order.setId(orderRecord.getECommerceOrderId());
                order.setRemoteId(order.getId());
                order.setDateCreated(datetimeFormat.format(orderRecord.getDateCreated()));
                order.setStatus(orderRecord.getOrderStatus());
                order.setShippingName(orderRecord.getShippingName());
                order.setShippingPrice(orderRecord.getShippingPrice());
                order.setShippingPriceFormatted(priceFormat.format(order.getShippingPrice()));
                order.setCurrency(currency);
                order.setShippingType(orderRecord.getECommerceShippingId());
                order.setPaymentType(orderRecord.getECommercePaymentId());
                order.setName(orderRecord.getName());
                order.setStreet(orderRecord.getStreet());
                order.setHouseNumber(orderRecord.getHouseNumber());
                order.setCity(orderRecord.getCity());
                order.setZip(orderRecord.getZip());
                order.setEmail(orderRecord.getEmail());
                order.setPhone(orderRecord.getPhone());
                order.setNote(orderRecord.getNote());
                order.setTotal((orderRecord.getTotal() == null ? 0d : orderRecord.getTotal()) + (order.getShippingPrice() == null ? 0d : order.getShippingPrice()) + (orderRecord.getPaymentPrice() == null ? 0d : orderRecord.getPaymentPrice()));
                order.setTotalFormatted(priceFormat.format(order.getTotal()));

                List<CartProductItemVO> products = Lists.newArrayList();
                order.setProducts(products);

                selectQuery = new SelectQuery("ecommerce_order_item");
                selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderRecord.getECommerceOrderId());

                List<ECommerceOrderItem> orderItemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceOrderItem.class);
                for (ECommerceOrderItem orderItemRecord : orderItemRecords) {
                    CartProductItemVO item = new CartProductItemVO();
                    item.setId(orderItemRecord.getECommerceOrderItemId());
                    item.setExpiration(0);
                    item.setQuantity(orderItemRecord.getQuantity());
                    item.setRemoteId(0L);
                    item.setTotalItemPrice(orderItemRecord.getTotalPrice());
                    item.setTotalItemPriceFormatted(priceFormat.format(item.getTotalItemPrice()));

                    CartProductItemVariantVO variant = new CartProductItemVariantVO();
                    item.setVariant(variant);
                    variant.setRemoteId(0L);
                    variant.setId(orderItemRecord.getECommerceProductVariantId());
                    variant.setRemoteId(0L);
                    variant.setProductId(orderItemRecord.getECommerceProductId());
                    variant.setUrl(orderItemRecord.getProductUrl());
                    variant.setName(orderItemRecord.getProductName());
                    variant.setPrice(orderItemRecord.getProductPrice());
                    variant.setPriceFormatted(priceFormat.format(variant.getPrice()));
                    variant.setCategory(orderItemRecord.getECommerceCategoryId());
                    variant.setDiscountPrice(orderItemRecord.getProductDiscountPrice());
                    if (variant.getDiscountPrice() != null) {
                        variant.setDiscountPriceFormatted(priceFormat.format(variant.getDiscountPrice()));
                    }
                    variant.setCurrency(currency);
                    variant.setCode(orderItemRecord.getVariantReference());
                    variant.setDescription(orderItemRecord.getProductDescription());
                    variant.setMainImage(orderItemRecord.getProductMainImage());
                    ProductColorVO color = new ProductColorVO();
                    variant.setColor(color);
                    color.setRemoteId(0L);
                    color.setId(orderItemRecord.getECommerceColorId());
                    color.setCode(orderItemRecord.getColorCode());
                    color.setValue(orderItemRecord.getColorValue());
                    color.setImg(orderItemRecord.getColorImg());
                    ProductSizeVO size = new ProductSizeVO();
                    variant.setSize(size);
                    size.setId(orderItemRecord.getECommerceSizeId());
                    size.setRemoteId(0L);
                    size.setValue(orderItemRecord.getSizeValue());
                    products.add(item);
                }
                responseBody.orders.add(order);
            }
        }

        return ResponseEntity.ok(responseBody);
    }

    public static class ResponseBody {

        @Expose
        @SerializedName("records")
        List<OrderVO> orders;

    }

}
