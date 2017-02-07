package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceDiscount implements Serializable {

    private Long ecommerceDiscountId;
    private String name;
    private String type;
    private Double value;
    private Double minCartAmount;
    private Date startDate;
    private Date endDate;
    private Boolean enabled;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
