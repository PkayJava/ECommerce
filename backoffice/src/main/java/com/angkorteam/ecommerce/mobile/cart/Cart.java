package com.angkorteam.ecommerce.mobile.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class Cart implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("product_count")
    private int productCount;

    @Expose
    @SerializedName("total_price")
    private double totalPrice;

    @Expose
    @SerializedName("total_price_formatted")
    private String totalPriceFormatted;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("items")
    private List<CartProductItem> items;

    @Expose
    @SerializedName("discounts")
    private List<CartDiscountItem> discounts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
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

    public List<CartProductItem> getItems() {
        return items;
    }

    public void setItems(List<CartProductItem> items) {
        this.items = items;
    }

    public List<CartDiscountItem> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<CartDiscountItem> discounts) {
        this.discounts = discounts;
    }
}
