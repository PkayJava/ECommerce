package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceDiscount implements Serializable {

    private Long ecommerceDiscountId;
    private String name;
    private String type;
    private String value;
    private String valueFormatted;
    private String minCartAmount;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceDiscount that = (EcommerceDiscount) o;

        if (ecommerceDiscountId != null ? !ecommerceDiscountId.equals(that.ecommerceDiscountId) : that.ecommerceDiscountId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (valueFormatted != null ? !valueFormatted.equals(that.valueFormatted) : that.valueFormatted != null)
            return false;
        return minCartAmount != null ? minCartAmount.equals(that.minCartAmount) : that.minCartAmount == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceDiscountId != null ? ecommerceDiscountId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (valueFormatted != null ? valueFormatted.hashCode() : 0);
        result = 31 * result + (minCartAmount != null ? minCartAmount.hashCode() : 0);
        return result;
    }
}
