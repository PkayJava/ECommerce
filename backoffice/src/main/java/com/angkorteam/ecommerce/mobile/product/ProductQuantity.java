package com.angkorteam.ecommerce.mobile.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class ProductQuantity implements Serializable {

    @Expose
    @SerializedName("quantity")
    private int quantity;

    @Expose
    @SerializedName("value")
    private String value;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
