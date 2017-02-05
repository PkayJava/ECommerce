package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.cart.CartProductItem;
import com.angkorteam.ecommerce.mobile.cart.CartProductItemVariant;
import com.angkorteam.ecommerce.mobile.order.Order;
import com.angkorteam.ecommerce.mobile.order.OrderResponse;
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
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        if (!Platform.hasAccess(request, OrdersServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));

        PlatformUser currentUser = Platform.getCurrentUser(request);

        OrderResponse data = new OrderResponse();

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery("ecommerce_order");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());

        List<EcommerceOrder> orderRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceOrder.class);

        if (orderRecords != null && !orderRecords.isEmpty()) {
            List<Order> orders = Lists.newArrayList();
            data.setRecords(orders);
            for (EcommerceOrder orderRecord : orderRecords) {
                Order order = new Order();
                order.setId(orderRecord.getEcommerceOrderId());
                order.setRemoteId(String.valueOf(order.getId()));
                order.setDateCreated(datetimeFormat.format(orderRecord.getDateCreated()));
                order.setStatus(orderRecord.getOrderStatus());
                order.setShippingName(orderRecord.getShippingName());
                order.setShippingPrice(orderRecord.getShippingPrice());
                order.setShippingPriceFormatted(priceFormat.format(order.getShippingPrice()));
                order.setCurrency(currency);
                order.setShippingType(orderRecord.getEcommerceShippingId());
                order.setPaymentType(orderRecord.getEcommercePaymentId());
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

                List<CartProductItem> products = Lists.newArrayList();
                order.setProducts(products);

                selectQuery = new SelectQuery("ecommerce_order_item");
                selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderRecord.getEcommerceOrderId());

                List<EcommerceOrderItem> orderItemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceOrderItem.class);
                for (EcommerceOrderItem orderItemRecord : orderItemRecords) {
                    CartProductItem item = new CartProductItem();
                    item.setId(orderItemRecord.getEcommerceOrderItemId());
                    item.setExpiration(0);
                    item.setQuantity(orderItemRecord.getQuantity());
                    item.setRemoteId(0L);
                    item.setTotalItemPrice(orderItemRecord.getTotalPrice());
                    item.setTotalItemPriceFormatted(priceFormat.format(item.getTotalItemPrice()));

                    CartProductItemVariant variant = new CartProductItemVariant();
                    item.setVariant(variant);
                    variant.setRemoteId(0L);
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
                orders.add(order);
            }
        }

        return ResponseEntity.ok(data);
    }

}
