package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommercePayment implements Serializable {

    private Long ecommercePaymentId;
    private String name;
    private String description;
    private Double price;

    public Long getECommercePaymentId() {
        return ecommercePaymentId;
    }

    public void setECommercePaymentId(Long ecommercePaymentId) {
        this.ecommercePaymentId = ecommercePaymentId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommercePayment that = (ECommercePayment) o;

        if (ecommercePaymentId != null ? !ecommercePaymentId.equals(that.ecommercePaymentId) : that.ecommercePaymentId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = ecommercePaymentId != null ? ecommercePaymentId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
