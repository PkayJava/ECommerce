package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class OrderVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("remote_id")
    private Long remoteId;

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
    private Long shippingType;

    @Expose
    @SerializedName("payment_type")
    private Long paymentType;

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
    private RegionVO region;

    @Expose
    @SerializedName("zip")
    private String zip;

    @Expose
    @SerializedName("items")
    private List<CartProductItemVO> products;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("note")
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
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

    public Long getShippingType() {
        return shippingType;
    }

    public void setShippingType(Long shippingType) {
        this.shippingType = shippingType;
    }

    public Long getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Long paymentType) {
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

    public RegionVO getRegion() {
        return region;
    }

    public void setRegion(RegionVO region) {
        this.region = region;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<CartProductItemVO> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductItemVO> products) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderVO orderVO = (OrderVO) o;

        if (id != null ? !id.equals(orderVO.id) : orderVO.id != null) return false;
        if (remoteId != null ? !remoteId.equals(orderVO.remoteId) : orderVO.remoteId != null) return false;
        if (dateCreated != null ? !dateCreated.equals(orderVO.dateCreated) : orderVO.dateCreated != null) return false;
        if (status != null ? !status.equals(orderVO.status) : orderVO.status != null) return false;
        if (total != null ? !total.equals(orderVO.total) : orderVO.total != null) return false;
        if (totalFormatted != null ? !totalFormatted.equals(orderVO.totalFormatted) : orderVO.totalFormatted != null)
            return false;
        if (shippingName != null ? !shippingName.equals(orderVO.shippingName) : orderVO.shippingName != null)
            return false;
        if (shippingPrice != null ? !shippingPrice.equals(orderVO.shippingPrice) : orderVO.shippingPrice != null)
            return false;
        if (shippingPriceFormatted != null ? !shippingPriceFormatted.equals(orderVO.shippingPriceFormatted) : orderVO.shippingPriceFormatted != null)
            return false;
        if (currency != null ? !currency.equals(orderVO.currency) : orderVO.currency != null) return false;
        if (shippingType != null ? !shippingType.equals(orderVO.shippingType) : orderVO.shippingType != null)
            return false;
        if (paymentType != null ? !paymentType.equals(orderVO.paymentType) : orderVO.paymentType != null) return false;
        if (name != null ? !name.equals(orderVO.name) : orderVO.name != null) return false;
        if (street != null ? !street.equals(orderVO.street) : orderVO.street != null) return false;
        if (houseNumber != null ? !houseNumber.equals(orderVO.houseNumber) : orderVO.houseNumber != null) return false;
        if (city != null ? !city.equals(orderVO.city) : orderVO.city != null) return false;
        if (region != null ? !region.equals(orderVO.region) : orderVO.region != null) return false;
        if (zip != null ? !zip.equals(orderVO.zip) : orderVO.zip != null) return false;
        if (products != null ? !products.equals(orderVO.products) : orderVO.products != null) return false;
        if (email != null ? !email.equals(orderVO.email) : orderVO.email != null) return false;
        if (phone != null ? !phone.equals(orderVO.phone) : orderVO.phone != null) return false;
        return note != null ? note.equals(orderVO.note) : orderVO.note == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (remoteId != null ? remoteId.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (totalFormatted != null ? totalFormatted.hashCode() : 0);
        result = 31 * result + (shippingName != null ? shippingName.hashCode() : 0);
        result = 31 * result + (shippingPrice != null ? shippingPrice.hashCode() : 0);
        result = 31 * result + (shippingPriceFormatted != null ? shippingPriceFormatted.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (shippingType != null ? shippingType.hashCode() : 0);
        result = 31 * result + (paymentType != null ? paymentType.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
