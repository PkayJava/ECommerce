package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ShippingVO {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("price")
    private Double price;

    @Expose
    @SerializedName("price_formatted")
    private String priceFormatted;

    @Expose
    @SerializedName("total_price")
    private Double totalPrice;

    @Expose
    @SerializedName("total_price_formatted")
    private String totalPriceFormatted;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("min_cart_amount")
    private Double minCartAmount;

    @Expose
    @SerializedName("payment")
    private List<PaymentVO> payment;

    @Expose
    @SerializedName("branch")
    private BranchVO branch;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("availabilityTime")
    private String availabilityTime;

    @Expose
    @SerializedName("availabilityDate")
    private String availabilityDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceFormatted() {
        return priceFormatted;
    }

    public void setPriceFormatted(String priceFormatted) {
        this.priceFormatted = priceFormatted;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPriceFormatted() {
        return totalPriceFormatted;
    }

    public void setTotalPriceFormatted(String totalPriceFormatted) {
        this.totalPriceFormatted = totalPriceFormatted;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMinCartAmount() {
        return minCartAmount;
    }

    public void setMinCartAmount(Double minCartAmount) {
        this.minCartAmount = minCartAmount;
    }

    public List<PaymentVO> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentVO> payment) {
        this.payment = payment;
    }

    public BranchVO getBranch() {
        return branch;
    }

    public void setBranch(BranchVO branch) {
        this.branch = branch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailabilityTime() {
        return availabilityTime;
    }

    public void setAvailabilityTime(String availabilityTime) {
        this.availabilityTime = availabilityTime;
    }

    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippingVO that = (ShippingVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (priceFormatted != null ? !priceFormatted.equals(that.priceFormatted) : that.priceFormatted != null)
            return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
        if (totalPriceFormatted != null ? !totalPriceFormatted.equals(that.totalPriceFormatted) : that.totalPriceFormatted != null)
            return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (minCartAmount != null ? !minCartAmount.equals(that.minCartAmount) : that.minCartAmount != null)
            return false;
        if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;
        if (branch != null ? !branch.equals(that.branch) : that.branch != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (availabilityTime != null ? !availabilityTime.equals(that.availabilityTime) : that.availabilityTime != null)
            return false;
        return availabilityDate != null ? availabilityDate.equals(that.availabilityDate) : that.availabilityDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (priceFormatted != null ? priceFormatted.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (totalPriceFormatted != null ? totalPriceFormatted.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (minCartAmount != null ? minCartAmount.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (availabilityTime != null ? availabilityTime.hashCode() : 0);
        result = 31 * result + (availabilityDate != null ? availabilityDate.hashCode() : 0);
        return result;
    }
}
