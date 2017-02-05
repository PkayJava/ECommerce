package com.angkorteam.ecommerce.mobile.order;

import com.angkorteam.ecommerce.mobile.Region;
import com.angkorteam.ecommerce.mobile.cart.CartProductItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class Order implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("remote_id")
    private String remoteId;

    @Expose
    @SerializedName("date_created")
    private String dateCreated;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("total")
    private Double total;

    @Expose
    @SerializedName("total_formatted")
    private String totalFormatted;

    @Expose
    @SerializedName("shipping_name")
    private String shippingName;

    @Expose
    @SerializedName("shipping_price")
    private Double shippingPrice;

    @Expose
    @SerializedName("shipping_price_formatted")
    private String shippingPriceFormatted;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("shipping_type")
    private long shippingType;

    @Expose
    @SerializedName("payment_type")
    private long paymentType;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("street")
    private String street;

    @Expose
    @SerializedName("house_number")
    private String houseNumber;

    @Expose
    @SerializedName("city")
    private String city;

    @Expose
    @SerializedName("region")
    private Region region;

    @Expose
    @SerializedName("zip")
    private String zip;

    @Expose
    @SerializedName("items")
    private List<CartProductItem> products;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("note")
    private String note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getTotalFormatted() {
        return totalFormatted;
    }

    public void setTotalFormatted(String totalFormatted) {
        this.totalFormatted = totalFormatted;
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

    public String getShippingPriceFormatted() {
        return shippingPriceFormatted;
    }

    public void setShippingPriceFormatted(String shippingPriceFormatted) {
        this.shippingPriceFormatted = shippingPriceFormatted;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getShippingType() {
        return shippingType;
    }

    public void setShippingType(long shippingType) {
        this.shippingType = shippingType;
    }

    public long getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(long paymentType) {
        this.paymentType = paymentType;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<CartProductItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductItem> products) {
        this.products = products;
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
}
