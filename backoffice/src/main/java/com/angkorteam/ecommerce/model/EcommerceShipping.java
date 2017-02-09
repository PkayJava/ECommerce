package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceShipping implements Serializable {

    private Long ecommerceShippingId;
    private String name;
    private Double minCartAmount;
    private Long ecommerceBranchId;
    private String description;
    private String availabilityTime;
    private String availabilityDate;
    private String type;
    private Double price;
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getEcommerceShippingId() {
        return ecommerceShippingId;
    }

    public void setEcommerceShippingId(Long ecommerceShippingId) {
        this.ecommerceShippingId = ecommerceShippingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinCartAmount() {
        return minCartAmount;
    }

    public void setMinCartAmount(Double minCartAmount) {
        this.minCartAmount = minCartAmount;
    }

    public Long getEcommerceBranchId() {
        return ecommerceBranchId;
    }

    public void setEcommerceBranchId(Long ecommerceBranchId) {
        this.ecommerceBranchId = ecommerceBranchId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceShipping that = (EcommerceShipping) o;

        if (ecommerceShippingId != null ? !ecommerceShippingId.equals(that.ecommerceShippingId) : that.ecommerceShippingId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (minCartAmount != null ? !minCartAmount.equals(that.minCartAmount) : that.minCartAmount != null)
            return false;
        if (ecommerceBranchId != null ? !ecommerceBranchId.equals(that.ecommerceBranchId) : that.ecommerceBranchId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (availabilityTime != null ? !availabilityTime.equals(that.availabilityTime) : that.availabilityTime != null)
            return false;
        if (availabilityDate != null ? !availabilityDate.equals(that.availabilityDate) : that.availabilityDate != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceShippingId != null ? ecommerceShippingId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (minCartAmount != null ? minCartAmount.hashCode() : 0);
        result = 31 * result + (ecommerceBranchId != null ? ecommerceBranchId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (availabilityTime != null ? availabilityTime.hashCode() : 0);
        result = 31 * result + (availabilityDate != null ? availabilityDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
