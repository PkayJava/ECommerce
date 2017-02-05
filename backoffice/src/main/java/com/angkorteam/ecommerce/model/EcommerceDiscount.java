package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceDiscount implements Serializable {

    private Long ecommerceDiscountId;
    private String name;
    private String type;
    private Double value;
    private Double minCartAmount;

    public Long getEcommerceDiscountId() {
        return ecommerceDiscountId;
    }

    public void setEcommerceDiscountId(Long ecommerceDiscountId) {
        this.ecommerceDiscountId = ecommerceDiscountId;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getMinCartAmount() {
        return minCartAmount;
    }

    public void setMinCartAmount(Double minCartAmount) {
        this.minCartAmount = minCartAmount;
    }
}
