package com.angkorteam.ecommerce.controller;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);

//        if (!ECommerce.hasAccess(request, CartDeliveryInfoServiceGet.class)) {
//            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
//        }
//
//        String asset = ECommerce.getSetting("asset");
//        String currency = ECommerce.getSetting("currency");
//        DecimalFormat priceFormat = new DecimalFormat(ECommerce.getSetting("price_format"));
//
//        User currentUser = ECommerce.getCurrentUser(request);
//
//        SelectQuery selectQuery = new SelectQuery("ecommerce_cart");
//        selectQuery.addWhere("user_id = :user_id", currentUser.getUserId());
//        ECommerceCart cartRecord = this.named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCart.class);
//
//        Integer productCount = 0;
//        Double totalPrice = 0d;
//        Double shippingPriceAddon = 0d;
//
//        selectQuery = new SelectQuery("ecommerce_cart_product_item");
//        selectQuery.addWhere("ecommerce_cart_id = :ecommerce_cart_id", cartRecord.getECommerceCartId());
//        List<ECommerceCartProductItem> itemRecords = this.named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceCartProductItem.class);
//
//        for (ECommerceCartProductItem itemRecord : itemRecords) {
//            selectQuery = new SelectQuery("ecommerce_product");
//            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", itemRecord.getECommerceProductId());
//            ECommerceProduct productRecord = this.named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProduct.class);
//            Double price = productRecord.getPrice() == null ? 0d : productRecord.getPrice();
//            Integer quantity = itemRecord.getQuantity() == null ? 0 : itemRecord.getQuantity();
//            Double productShippingPriceAddon = productRecord.getShippingPrice() == null ? 0d : productRecord.getShippingPrice();
//            if (productShippingPriceAddon > 0) {
//                shippingPriceAddon = shippingPriceAddon + (productShippingPriceAddon * quantity);
//            }
//            productCount = quantity + productCount;
//            totalPrice = totalPrice + (quantity * price);
//        }
//
//        Map<Long, ECommercePayment> payments = Maps.newHashMap();
//        for (ECommercePayment payment : this.jdbcTemplate.queryForList("select * from ecommerce_payment", ECommercePayment.class)) {
//            payments.put(payment.getECommercePaymentId(), payment);
//        }
//
//        Map<Long, ECommerceBranch> branches = Maps.newHashMap();
//        for (ECommerceBranch branch : this.jdbcTemplate.queryForList("select * from ecommerce_branch", ECommerceBranch.class)) {
//            branches.put(branch.getECommerceBranchId(), branch);
//        }
//
//        selectQuery = new SelectQuery("ecommerce_shipping");
//        selectQuery.addWhere("type = :type", "Pickup");
//        List<ECommerceShipping> pickupShippings = this.named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceShipping.class);
//        selectQuery = new SelectQuery("ecommerce_shipping");
//        selectQuery.addWhere("type = :type", "Delivery");
//        List<ECommerceShipping> deliveryShippings = this.named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), ECommerceShipping.class);
//
//        DeliveryRequestVO deliveryRequest = new DeliveryRequestVO();
//        DeliveryVO deliveryVO = new DeliveryVO();
//
//        if (pickupShippings != null && !pickupShippings.isEmpty()) {
//            List<ShippingVO> personalPickup = Lists.newArrayList();
//            for (ECommerceShipping shipping : pickupShippings) {
//                ShippingVO shippingVO = lookupShipping(asset, currency, priceFormat, totalPrice, 0d, shipping, payments, branches);
//                personalPickup.add(shippingVO);
//            }
//            deliveryVO.setPersonalPickup(personalPickup);
//        }
//
//        if (deliveryShippings != null && !deliveryShippings.isEmpty()) {
//            List<ShippingVO> shippingPickup = Lists.newArrayList();
//            for (ECommerceShipping shipping : deliveryShippings) {
//                ShippingVO shippingVO = lookupShipping(asset, currency, priceFormat, totalPrice, shippingPriceAddon, shipping, payments, branches);
//                shippingPickup.add(shippingVO);
//            }
//            deliveryVO.setShipping(shippingPickup);
//        }
//        deliveryRequest.setDelivery(deliveryVO);
//
//
//        return ResponseEntity.ok(deliveryRequest);
        return null;
    }

//    protected ShippingVO lookupShipping(String asset, String currency, DecimalFormat priceFormat, Double totalPrice, Double shippingAddonPrice, ECommerceShipping shippingRecord, Map<Long, ECommercePayment> paymentRecords, Map<Long, ECommerceBranch> branchRecords) {
//        SelectQuery selectQuery = null;
//        ShippingVO shipping = new ShippingVO();
//        shipping.setId(shippingRecord.getECommerceShippingId());
//        shipping.setName(shippingRecord.getName());
//        shipping.setCurrency(currency);
//        shipping.setAvailabilityDate(shippingRecord.getAvailabilityDate());
//        shipping.setAvailabilityTime(shippingRecord.getAvailabilityTime());
//        shipping.setDescription(shippingRecord.getDescription());
//        shipping.setMinCartAmount(shippingRecord.getMinCartAmount());
//        shipping.setPrice((shippingRecord.getPrice() == null ? 0d : shippingRecord.getPrice()) + shippingAddonPrice);
//        shipping.setPriceFormatted(priceFormat.format(shipping.getPrice()));
//        shipping.setTotalPrice(totalPrice + (shipping.getPrice() == null ? 0d : shipping.getPrice()));
//        shipping.setTotalPriceFormatted(priceFormat.format(shipping.getTotalPrice()));
//        if (shippingRecord.getECommerceBranchId() != null) {
//            ECommerceBranch branchRecord = branchRecords.get(shippingRecord.getECommerceBranchId());
//            if (branchRecord != null) {
//                BranchVO branchVO = new BranchVO();
//                branchVO.setId(branchRecord.getECommerceBranchId());
//                branchVO.setAddress(branchRecord.getAddress());
//                branchVO.setName(branchRecord.getName());
//                branchVO.setNote(branchRecord.getNote());
//                CoordinatesVO coordinatesVO = new CoordinatesVO();
//                coordinatesVO.setLongitude(branchRecord.getLongitue());
//                coordinatesVO.setLatitude(branchRecord.getLatitude());
//                branchVO.setCoordinates(coordinatesVO);
//
//                selectQuery = new SelectQuery("ecommerce_branch_opening");
//                selectQuery.addField("day");
//                selectQuery.addField("opening");
//                selectQuery.addWhere("ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getECommerceBranchId());
//                List<OpeningHoursVO> openingRecords = this.named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), OpeningHoursVO.class);
//                if (openingRecords != null && !openingRecords.isEmpty()) {
//                    branchVO.setOpeningHoursList(openingRecords);
//                }
//                selectQuery = new SelectQuery("ecommerce_branch_transport");
//                selectQuery.addField("ecommerce_branch_transport.text AS text");
//                selectQuery.addField("CONCAT('" + asset + "', '/api/resource', file.path, '/', file.name) AS icon");
//                selectQuery.addJoin(JoinType.InnerJoin, "file", "ecommerce_branch_transport.icon_file_id = file.file_id");
//                selectQuery.addWhere("ecommerce_branch_transport.ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getECommerceBranchId());
//                List<TransportVO> transportRecords = this.named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), TransportVO.class);
//                if (transportRecords != null && !transportRecords.isEmpty()) {
//                    branchVO.setTransports(transportRecords);
//                }
//                shipping.setBranch(branchVO);
//            }
//
//        }
//
//        selectQuery = new SelectQuery("ecommerce_shipping_payment");
//        selectQuery.addField("ecommerce_payment_id");
//        selectQuery.addWhere("ecommerce_shipping_id = :ecommerce_shipping_id", shippingRecord.getECommerceShippingId());
//        List<Long> payments = this.named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), Long.class);
//        if (payments != null && !payments.isEmpty()) {
//            List<PaymentVO> listPayments = Lists.newArrayList();
//            for (Long p : payments) {
//                ECommercePayment paymentRecord = paymentRecords.get(p);
//                PaymentVO payment = new PaymentVO();
//                payment.setId(paymentRecord.getECommercePaymentId());
//                payment.setName(paymentRecord.getName());
//                payment.setCurrency(currency);
//                payment.setDescription(paymentRecord.getDescription());
//                payment.setPrice(paymentRecord.getPrice());
//                payment.setPriceFormatted(priceFormat.format(payment.getPrice()));
//                payment.setTotalPrice(totalPrice + shipping.getPrice() + (payment.getPrice() == null ? 0d : payment.getPrice()));
//                payment.setTotalPriceFormatted(priceFormat.format(payment.getTotalPrice()));
//                listPayments.add(payment);
//            }
//            shipping.setPayment(listPayments);
//        }
//
//        return shipping;
//    }
}
