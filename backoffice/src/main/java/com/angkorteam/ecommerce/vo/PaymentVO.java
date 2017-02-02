package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PaymentVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("price")
    private Double price;

    @Expose
    @SerializedName("price_formatted")
    private String priceFormatted;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("total_price")
    private Double totalPrice;

    @Expose
    @SerializedName("total_price_formatted")
    private String totalPriceFormatted;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentVO paymentVO = (PaymentVO) o;

        if (id != null ? !id.equals(paymentVO.id) : paymentVO.id != null) return false;
        if (name != null ? !name.equals(paymentVO.name) : paymentVO.name != null) return false;
        if (description != null ? !description.equals(paymentVO.description) : paymentVO.description != null)
            return false;
        if (price != null ? !price.equals(paymentVO.price) : paymentVO.price != null) return false;
        if (priceFormatted != null ? !priceFormatted.equals(paymentVO.priceFormatted) : paymentVO.priceFormatted != null)
            return false;
        if (currency != null ? !currency.equals(paymentVO.currency) : paymentVO.currency != null) return false;
        if (totalPrice != null ? !totalPrice.equals(paymentVO.totalPrice) : paymentVO.totalPrice != null) return false;
        return totalPriceFormatted != null ? totalPriceFormatted.equals(paymentVO.totalPriceFormatted) : paymentVO.totalPriceFormatted == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (priceFormatted != null ? priceFormatted.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (totalPriceFormatted != null ? totalPriceFormatted.hashCode() : 0);
        return result;
    }
}
