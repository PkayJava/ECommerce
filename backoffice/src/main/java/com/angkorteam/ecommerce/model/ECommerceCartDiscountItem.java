package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceCartDiscountItem implements Serializable {

    private Long ecommerceCartDiscountItemId;
    private Long ecommerceCartId;
    private Long ecommerceDiscountId;
    private Integer quantity;

    public Long getECommerceCartDiscountItemId() {
        return ecommerceCartDiscountItemId;
    }

    public void setECommerceCartDiscountItemId(Long ecommerceCartDiscountItemId) {
        this.ecommerceCartDiscountItemId = ecommerceCartDiscountItemId;
    }

    public Long getECommerceCartId() {
        return ecommerceCartId;
    }

    public void setECommerceCartId(Long ecommerceCartId) {
        this.ecommerceCartId = ecommerceCartId;
    }

    public Long getECommerceDiscountId() {
        return ecommerceDiscountId;
    }

    public void setECommerceDiscountId(Long ecommerceDiscountId) {
        this.ecommerceDiscountId = ecommerceDiscountId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceCartDiscountItem that = (ECommerceCartDiscountItem) o;

        if (ecommerceCartDiscountItemId != null ? !ecommerceCartDiscountItemId.equals(that.ecommerceCartDiscountItemId) : that.ecommerceCartDiscountItemId != null)
            return false;
        if (ecommerceCartId != null ? !ecommerceCartId.equals(that.ecommerceCartId) : that.ecommerceCartId != null)
            return false;
        if (ecommerceDiscountId != null ? !ecommerceDiscountId.equals(that.ecommerceDiscountId) : that.ecommerceDiscountId != null)
            return false;
        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceCartDiscountItemId != null ? ecommerceCartDiscountItemId.hashCode() : 0;
        result = 31 * result + (ecommerceCartId != null ? ecommerceCartId.hashCode() : 0);
        result = 31 * result + (ecommerceDiscountId != null ? ecommerceDiscountId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
