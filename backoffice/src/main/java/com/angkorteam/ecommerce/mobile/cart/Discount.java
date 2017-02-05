package com.angkorteam.ecommerce.mobile.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class Discount implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("value")
    private String value;

    @Expose
    @SerializedName("value_formatted")
    private String valueFormatted;

    @Expose
    @SerializedName("min_cart_amount")
    private String minCartAmount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueFormatted() {
        return valueFormatted;
    }

    public void setValueFormatted(String valueFormatted) {
        this.valueFormatted = valueFormatted;
    }

    public String getMinCartAmount() {
        return minCartAmount;
    }

    public void setMinCartAmount(String minCartAmount) {
        this.minCartAmount = minCartAmount;
    }
}
