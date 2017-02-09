package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceOrder implements Serializable {

    private Long ecommerceOrderId;
    private Long ecommerceShippingId;
    private Long ecommercePaymentId;
    private Long platformUserId;
    private String name;
    private String street;
    private String houseNumber;
    private String city;
    private String zip;
    private String email;
    private String phone;
    private String note;
    private Date dateCreated;
    private String buyerStatus;
    private String orderStatus;
    private String shippingName;
    private Double shippingPrice;
    private Double shippingPriceAddon;
    private String paymentName;
    private Double paymentPrice;
    private Long ecommerceRegionId;
    private Long ecommerceDiscountCouponId;
    private Long ecommerceDiscountId;
    private String couponType;
    private Double couponValue;
    private Double discountAmount;
    private Double subTotalAmount;
    private Double totalAmount;
    private Double grandTotalAmount;

    public Double getShippingPriceAddon() {
        return shippingPriceAddon;
    }

    public void setShippingPriceAddon(Double shippingPriceAddon) {
        this.shippingPriceAddon = shippingPriceAddon;
    }

    public Long getEcommerceDiscountCouponId() {
        return ecommerceDiscountCouponId;
    }

    public void setEcommerceDiscountCouponId(Long ecommerceDiscountCouponId) {
        this.ecommerceDiscountCouponId = ecommerceDiscountCouponId;
    }

    public Long getEcommerceDiscountId() {
        return ecommerceDiscountId;
    }

    public void setEcommerceDiscountId(Long ecommerceDiscountId) {
        this.ecommerceDiscountId = ecommerceDiscountId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public Double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(Double couponValue) {
        this.couponValue = couponValue;
    }

    public Long getEcommerceOrderId() {
        return ecommerceOrderId;
    }

    public void setEcommerceOrderId(Long ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

    public Long getEcommerceShippingId() {
        return ecommerceShippingId;
    }

    public void setEcommerceShippingId(Long ecommerceShippingId) {
        this.ecommerceShippingId = ecommerceShippingId;
    }

    public Long getEcommercePaymentId() {
        return ecommercePaymentId;
    }

    public void setEcommercePaymentId(Long ecommercePaymentId) {
        this.ecommercePaymentId = ecommercePaymentId;
    }

    public Long getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(String buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Double getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Double paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public Long getEcommerceRegionId() {
        return ecommerceRegionId;
    }

    public void setEcommerceRegionId(Long ecommerceRegionId) {
        this.ecommerceRegionId = ecommerceRegionId;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(Double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public void setGrandTotalAmount(Double grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }
}
