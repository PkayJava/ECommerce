package com.angkorteam.ecommerce.mobile.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class CartProductItem implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("remote_id")
    private long remoteId;

    @Expose
    @SerializedName("quantity")
    private int quantity;

    @Expose
    @SerializedName("total_price")
    private double totalItemPrice;

    @Expose
    @SerializedName("total_item_price_formatted")
    private String totalItemPriceFormatted;

    @Expose
    @SerializedName("variant")
    private CartProductItemVariant variant;

    @Expose
    @SerializedName("expiration")
    private int expiration = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getTotalItemPriceFormatted() {
        return totalItemPriceFormatted;
    }

    public void setTotalItemPriceFormatted(String totalItemPriceFormatted) {
        this.totalItemPriceFormatted = totalItemPriceFormatted;
    }

    public CartProductItemVariant getVariant() {
        return variant;
    }

    public void setVariant(CartProductItemVariant variant) {
        this.variant = variant;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }
}
