package com.angkorteam.ecommerce.mobile.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class CartInfo implements Serializable {

    @Expose
    @SerializedName("product_count")
    private int productCount;

    @Expose
    @SerializedName("total_price")
    private double totalPrice;

    @Expose
    @SerializedName("total_price_formatted")
    private String totalPriceFormatted;

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
}
