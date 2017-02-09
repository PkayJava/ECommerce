package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.cart.Discount;
import com.angkorteam.ecommerce.mobile.delivery.*;
import com.angkorteam.ecommerce.model.*;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class CartDeliveryInfoServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartDeliveryInfoServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(path = "/{shop}/cart/delivery-info", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        if (!Platform.hasAccess(request, CartDeliveryInfoServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = new SelectQuery("ecommerce_cart");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        EcommerceCart ecommerceCart = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCart.class);

        Integer productCount = 0;
        Double totalPrice = 0d;
        Double shippingPriceAddon = 0d;

        selectQuery = new SelectQuery("ecommerce_cart_product_item");
        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", ecommerceCart.getEcommerceCartId());
        List<EcommerceCartProductItem> itemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCartProductItem.class);

        for (EcommerceCartProductItem cartItem : itemRecords) {
            selectQuery = new SelectQuery("ecommerce_product");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", cartItem.getEcommerceProductId());
            EcommerceProduct productRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProduct.class);
            Double price = productRecord.getPrice() == null ? 0d : productRecord.getPrice();
            Integer quantity = cartItem.getQuantity() == null ? 0 : cartItem.getQuantity();
            Double productShippingPriceAddon = productRecord.getShippingPrice() == null ? 0d : productRecord.getShippingPrice();
            if (productShippingPriceAddon > 0) {
                shippingPriceAddon = shippingPriceAddon + (productShippingPriceAddon * quantity);
            }
            productCount = quantity + productCount;
            totalPrice = totalPrice + (quantity * price);
        }

        Map<Long, EcommercePayment> payments = Maps.newHashMap();
        for (EcommercePayment payment : jdbcTemplate.queryForList("select * from ecommerce_payment", EcommercePayment.class)) {
            payments.put(payment.getEcommercePaymentId(), payment);
        }

        Map<Long, EcommerceBranch> branches = Maps.newHashMap();
        for (EcommerceBranch branch : jdbcTemplate.queryForList("select * from ecommerce_branch", EcommerceBranch.class)) {
            branches.put(branch.getEcommerceBranchId(), branch);
        }

        selectQuery = new SelectQuery("ecommerce_shipping");
        selectQuery.addWhere("type = :type", "Pickup");
        List<EcommerceShipping> pickupShippings = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceShipping.class);
        selectQuery = new SelectQuery("ecommerce_shipping");
        selectQuery.addWhere("type = :type", "Delivery");
        List<EcommerceShipping> deliveryShippings = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceShipping.class);

        Date now = new Date();

        Double couponValue = 0d;
        if (ecommerceCart.getEcommerceDiscountCouponId() != null) {

            selectQuery = new SelectQuery("ecommerce_discount_coupon");
            selectQuery.addWhere("ecommerce_discount_coupon_id = :ecommerce_discount_coupon_id", ecommerceCart.getEcommerceDiscountCouponId());
            EcommerceDiscountCoupon ecommerceDiscountCoupon = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceDiscountCoupon.class);

            selectQuery = new SelectQuery("ecommerce_discount");
            selectQuery.addWhere("ecommerce_discount_id = :ecommerce_discount_id", ecommerceCart.getEcommerceDiscountId());
            EcommerceDiscount ecommerceDiscount = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceDiscount.class);

            if (ecommerceDiscountCoupon == null || ecommerceDiscountCoupon.getUsed() || ecommerceDiscount == null || !ecommerceDiscount.getEnabled() || now.before(ecommerceDiscount.getStartDate()) || now.after(ecommerceDiscount.getEndDate())) {
            } else {
                if (ecommerceDiscount.getType().equals(Discount.TYPE_PERCENTAGE)) {
                    couponValue = totalPrice * (ecommerceDiscount.getValue() / 100D);
                } else if (ecommerceDiscount.getType().equals(Discount.TYPE_FIXED)) {
                    couponValue = ecommerceDiscount.getValue();
                }
            }
        }
        totalPrice = totalPrice - couponValue;
        if (totalPrice < 0) {
            totalPrice = 0D;
        }

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        Delivery delivery = new Delivery();

        if (pickupShippings != null && !pickupShippings.isEmpty()) {
            List<Shipping> personalPickup = Lists.newArrayList();
            for (EcommerceShipping shipping : pickupShippings) {
                Shipping shippingVO = lookupShipping(asset, currency, priceFormat, totalPrice, 0d, shipping, payments, branches);
                personalPickup.add(shippingVO);
            }
            delivery.setPersonalPickup(personalPickup);
        }

        if (deliveryShippings != null && !deliveryShippings.isEmpty()) {
            List<Shipping> shippingPickup = Lists.newArrayList();
            for (EcommerceShipping shipping : deliveryShippings) {
                Shipping shippingVO = lookupShipping(asset, currency, priceFormat, totalPrice, shippingPriceAddon, shipping, payments, branches);
                shippingPickup.add(shippingVO);
            }
            delivery.setShipping(shippingPickup);
        }
        deliveryRequest.setDelivery(delivery);


        return ResponseEntity.ok(deliveryRequest);
    }

    protected Shipping lookupShipping(String asset, String currency, DecimalFormat priceFormat, Double totalPrice, Double shippingAddonPrice, EcommerceShipping shippingRecord, Map<Long, EcommercePayment> paymentRecords, Map<Long, EcommerceBranch> branchRecords) {
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        SelectQuery selectQuery = null;
        Shipping shipping = new Shipping();
        shipping.setId(shippingRecord.getEcommerceShippingId());
        shipping.setName(shippingRecord.getName());
        shipping.setCurrency(currency);
        shipping.setAvailabilityDate(shippingRecord.getAvailabilityDate());
        shipping.setAvailabilityTime(shippingRecord.getAvailabilityTime());
        shipping.setDescription(shippingRecord.getDescription());
        shipping.setMinCartAmount(shippingRecord.getMinCartAmount());
        shipping.setPrice((shippingRecord.getPrice() == null ? 0d : shippingRecord.getPrice()) + shippingAddonPrice);
        shipping.setPriceFormatted(priceFormat.format(shipping.getPrice()));
        shipping.setTotalPrice(totalPrice + (shipping.getPrice() == null ? 0d : shipping.getPrice()));
        shipping.setTotalPriceFormatted(priceFormat.format(shipping.getTotalPrice()));
        if (shippingRecord.getEcommerceBranchId() != null) {
            EcommerceBranch branchRecord = branchRecords.get(shippingRecord.getEcommerceBranchId());
            if (branchRecord != null) {
                Branch branchVO = new Branch();
                branchVO.setId(branchRecord.getEcommerceBranchId());
                branchVO.setAddress(branchRecord.getAddress());
                branchVO.setName(branchRecord.getName());
                branchVO.setNote(branchRecord.getNote());
                Coordinates coordinatesVO = new Coordinates();
                coordinatesVO.setLongitude(branchRecord.getLongitude());
                coordinatesVO.setLatitude(branchRecord.getLatitude());
                branchVO.setCoordinates(coordinatesVO);

                selectQuery = new SelectQuery("ecommerce_branch_opening");
                selectQuery.addField("day");
                selectQuery.addField("opening");
                selectQuery.addWhere("ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getEcommerceBranchId());
                List<OpeningHours> openingRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), OpeningHours.class);
                if (openingRecords != null && !openingRecords.isEmpty()) {
                    branchVO.setOpeningHoursList(openingRecords);
                }
                selectQuery = new SelectQuery("ecommerce_branch_transport");
                selectQuery.addField("ecommerce_branch_transport.text AS text");
                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) AS icon");
                selectQuery.addJoin(JoinType.InnerJoin, "platform_file", "ecommerce_branch_transport.icon_platform_file_id = platform_file.platform_file_id");
                selectQuery.addWhere("ecommerce_branch_transport.ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getEcommerceBranchId());
                List<Transport> transportRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), Transport.class);
                if (transportRecords != null && !transportRecords.isEmpty()) {
                    branchVO.setTransports(transportRecords);
                }
                shipping.setBranch(branchVO);
            }

        }

        selectQuery = new SelectQuery("ecommerce_shipping_payment");
        selectQuery.addField("ecommerce_payment_id");
        selectQuery.addWhere("ecommerce_shipping_id = :ecommerce_shipping_id", shippingRecord.getEcommerceShippingId());
        List<Long> payments = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), Long.class);
        if (payments != null && !payments.isEmpty()) {
            List<Payment> listPayments = Lists.newArrayList();
            for (Long p : payments) {
                EcommercePayment paymentRecord = paymentRecords.get(p);
                Payment payment = new Payment();
                payment.setId(paymentRecord.getEcommercePaymentId());
                payment.setName(paymentRecord.getName());
                payment.setCurrency(currency);
                payment.setDescription(paymentRecord.getDescription());
                payment.setPrice(paymentRecord.getPrice());
                payment.setPriceFormatted(priceFormat.format(payment.getPrice()));
                payment.setTotalPrice(totalPrice + shipping.getPrice() + (payment.getPrice() == null ? 0d : payment.getPrice()));
                payment.setTotalPriceFormatted(priceFormat.format(payment.getTotalPrice()));
                payment.setType(paymentRecord.getType());
                payment.setParam1(paymentRecord.getClientParam1());
                payment.setParam2(paymentRecord.getClientParam2());
                payment.setParam3(paymentRecord.getClientParam3());
                payment.setParam4(paymentRecord.getClientParam4());
                payment.setParam5(paymentRecord.getClientParam5());
                listPayments.add(payment);
            }
            shipping.setPayment(listPayments);
        }

        return shipping;
    }
}
