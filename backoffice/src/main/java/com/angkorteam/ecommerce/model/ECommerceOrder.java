package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceOrder implements Serializable{

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
    private Double total;
    private String shippingName;
    private Double shippingPrice;
    private String paymentName;
    private Double paymentPrice;
    private Long ecommerceRegionId;

    public Long getECommerceOrderId() {
        return ecommerceOrderId;
    }

    public void setECommerceOrderId(Long ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

    public Long getECommerceShippingId() {
        return ecommerceShippingId;
    }

    public void setECommerceShippingId(Long ecommerceShippingId) {
        this.ecommerceShippingId = ecommerceShippingId;
    }

    public Long getECommercePaymentId() {
        return ecommercePaymentId;
    }

    public void setECommercePaymentId(Long ecommercePaymentId) {
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Long getECommerceRegionId() {
        return ecommerceRegionId;
    }

    public void setECommerceRegionId(Long ecommerceRegionId) {
        this.ecommerceRegionId = ecommerceRegionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceOrder that = (ECommerceOrder) o;

        if (ecommerceOrderId != null ? !ecommerceOrderId.equals(that.ecommerceOrderId) : that.ecommerceOrderId != null)
            return false;
        if (ecommerceShippingId != null ? !ecommerceShippingId.equals(that.ecommerceShippingId) : that.ecommerceShippingId != null)
            return false;
        if (ecommercePaymentId != null ? !ecommercePaymentId.equals(that.ecommercePaymentId) : that.ecommercePaymentId != null)
            return false;
        if (platformUserId != null ? !platformUserId.equals(that.platformUserId) : that.platformUserId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (houseNumber != null ? !houseNumber.equals(that.houseNumber) : that.houseNumber != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        if (buyerStatus != null ? !buyerStatus.equals(that.buyerStatus) : that.buyerStatus != null) return false;
        if (orderStatus != null ? !orderStatus.equals(that.orderStatus) : that.orderStatus != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (shippingName != null ? !shippingName.equals(that.shippingName) : that.shippingName != null) return false;
        if (shippingPrice != null ? !shippingPrice.equals(that.shippingPrice) : that.shippingPrice != null)
            return false;
        if (paymentName != null ? !paymentName.equals(that.paymentName) : that.paymentName != null) return false;
        if (paymentPrice != null ? !paymentPrice.equals(that.paymentPrice) : that.paymentPrice != null) return false;
        return ecommerceRegionId != null ? ecommerceRegionId.equals(that.ecommerceRegionId) : that.ecommerceRegionId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceOrderId != null ? ecommerceOrderId.hashCode() : 0;
        result = 31 * result + (ecommerceShippingId != null ? ecommerceShippingId.hashCode() : 0);
        result = 31 * result + (ecommercePaymentId != null ? ecommercePaymentId.hashCode() : 0);
        result = 31 * result + (platformUserId != null ? platformUserId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (buyerStatus != null ? buyerStatus.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (shippingName != null ? shippingName.hashCode() : 0);
        result = 31 * result + (shippingPrice != null ? shippingPrice.hashCode() : 0);
        result = 31 * result + (paymentName != null ? paymentName.hashCode() : 0);
        result = 31 * result + (paymentPrice != null ? paymentPrice.hashCode() : 0);
        result = 31 * result + (ecommerceRegionId != null ? ecommerceRegionId.hashCode() : 0);
        return result;
    }
}
