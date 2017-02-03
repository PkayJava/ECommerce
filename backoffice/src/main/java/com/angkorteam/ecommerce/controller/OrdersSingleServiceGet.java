//package com.angkorteam.ecommerce.controller;
//
//import com.angkorteam.ecommerce.model.ECommerceOrder;
//import com.angkorteam.ecommerce.model.ECommerceOrderItem;
//import com.angkorteam.ecommerce.vo.*;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
//import com.angkorteam.platform.Platform;
//import com.angkorteam.platform.Spring;
//import com.angkorteam.platform.model.PlatformUser;
//import com.google.common.collect.Lists;
//import com.google.gson.Gson;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
///**
// * Created by socheatkhauv on 28/1/17.
// */
//@Controller
//public class OrdersSingleServiceGet {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersSingleServiceGet.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(path = "/{shop}/orders/{id}")
//    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
//        if (!Platform.hasAccess(request, CartDeliveryInfoServiceGet.class)) {
//            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
//        }
//
//        SelectQuery selectQuery = null;
//
//        String asset = Platform.getSetting("asset");
//        String currency = Platform.getSetting("currency");
//        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
//        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));
//
//        PlatformUser currentUser = Platform.getCurrentUser(request);
//
//        selectQuery = new SelectQuery("ecommerce_order");
//        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", id);
//        selectQuery.addWhere("user_id = :user_id", currentUser.getUserId());
//
//        ECommerceOrder orderRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceOrder.class);
//
//        ResponseBody order = new ResponseBody();
//        order.id = orderRecord.getECommerceOrderId();
//        order.remoteId = order.id;
//        order.dateCreated = datetimeFormat.format(orderRecord.getDateCreated());
//        order.status = orderRecord.getOrderStatus();
//        order.shippingName = orderRecord.getShippingName();
//        order.shippingPrice = orderRecord.getShippingPrice();
//        order.shippingPriceFormatted = priceFormat.format(order.shippingPrice);
//        order.currency = currency;
//        order.shippingType = orderRecord.getECommerceShippingId();
//        order.paymentType = orderRecord.getECommercePaymentId();
//        order.name = orderRecord.getName();
//        order.street = orderRecord.getStreet();
//        order.houseNumber = orderRecord.getHouseNumber();
//        order.city = orderRecord.getCity();
//        order.zip = orderRecord.getZip();
//        order.email = orderRecord.getEmail();
//        order.phone = orderRecord.getPhone();
//        order.note = orderRecord.getNote();
//        order.total = orderRecord.getTotal() + order.shippingPrice + orderRecord.getPaymentPrice();
//        order.totalFormatted = priceFormat.format(order.total);
//        order.region = null;
//
//        selectQuery = new SelectQuery("ecommerce_order_item");
//        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderRecord.getECommerceOrderId());
//
//        List<ECommerceOrderItem> orderItemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceOrderItem.class);
//        order.products = Lists.newArrayList();
//        for (ECommerceOrderItem orderItemRecord : orderItemRecords) {
//            CartProductItemVO item = new CartProductItemVO();
//            item.setId(orderItemRecord.getECommerceOrderId());
//            item.setExpiration(0);
//            item.setQuantity(orderItemRecord.getQuantity());
//            item.setRemoteId(0L);
//            item.setTotalItemPrice(orderItemRecord.getTotalPrice());
//            item.setTotalItemPriceFormatted(priceFormat.format(item.getTotalItemPrice()));
//            CartProductItemVariantVO variant = new CartProductItemVariantVO();
//            item.setVariant(variant);
//            variant.setId(orderItemRecord.getECommerceProductVariantId());
//            variant.setRemoteId(0L);
//            variant.setProductId(orderItemRecord.getECommerceProductId());
//            variant.setUrl(orderItemRecord.getProductUrl());
//            variant.setName(orderItemRecord.getProductName());
//            variant.setPrice(orderItemRecord.getProductPrice());
//            variant.setPriceFormatted(priceFormat.format(variant.getPrice()));
//            variant.setCategory(orderItemRecord.getECommerceCategoryId());
//            variant.setDiscountPrice(orderItemRecord.getProductDiscountPrice());
//            if (variant.getDiscountPrice() != null) {
//                variant.setDiscountPriceFormatted(priceFormat.format(variant.getDiscountPrice()));
//            }
//            variant.setCurrency(currency);
//            variant.setCode(orderItemRecord.getVariantReference());
//            variant.setDescription(orderItemRecord.getProductDescription());
//            variant.setMainImage(orderItemRecord.getProductMainImage());
//            ProductColorVO color = new ProductColorVO();
//            variant.setColor(color);
//            color.setRemoteId(0L);
//            color.setId(orderItemRecord.getECommerceColorId());
//            color.setCode(orderItemRecord.getColorCode());
//            color.setValue(orderItemRecord.getColorValue());
//            color.setImg(orderItemRecord.getColorImg());
//            ProductSizeVO size = new ProductSizeVO();
//            variant.setSize(size);
//            size.setId(orderItemRecord.getECommerceSizeId());
//            size.setRemoteId(0L);
//            size.setValue(orderItemRecord.getSizeValue());
//            order.products.add(item);
//        }
//        return ResponseEntity.ok(order);
//    }
//
//    public static class ResponseBody {
//
//        @Expose
//        @SerializedName("id")
//        Long id;
//
//        @Expose
//        @SerializedName("remote_id")
//        Long remoteId = 0l;
//
//        @Expose
//        @SerializedName("date_created")
//        String dateCreated;
//
//        @Expose
//        @SerializedName("status")
//        String status;
//
//        @Expose
//        @SerializedName("total")
//        Double total;
//
//        @Expose
//        @SerializedName("total_formatted")
//        String totalFormatted;
//
//        @Expose
//        @SerializedName("shipping_name")
//        String shippingName;
//
//        @Expose
//        @SerializedName("shipping_price")
//        Double shippingPrice;
//
//        @Expose
//        @SerializedName("shipping_price_formatted")
//        String shippingPriceFormatted;
//
//        @Expose
//        @SerializedName("currency")
//        String currency;
//
//        @Expose
//        @SerializedName("shipping_type")
//        Long shippingType;
//
//        @Expose
//        @SerializedName("payment_type")
//        Long paymentType;
//
//        @Expose
//        @SerializedName("name")
//        String name;
//
//        @Expose
//        @SerializedName("street")
//        String street;
//
//        @Expose
//        @SerializedName("house_number")
//        String houseNumber;
//
//        @Expose
//        @SerializedName("city")
//        String city;
//
//        @Expose
//        @SerializedName("region")
//        RegionVO region;
//
//        @Expose
//        @SerializedName("zip")
//        String zip;
//
//        @Expose
//        @SerializedName("items")
//        List<CartProductItemVO> products;
//
//        @Expose
//        @SerializedName("email")
//        String email;
//
//        @Expose
//        @SerializedName("phone")
//        String phone;
//
//        @Expose
//        @SerializedName("note")
//        String note;
//
//    }
//
//}
