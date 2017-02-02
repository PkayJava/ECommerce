package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceProductVariant implements Serializable {

    private Long ecommerceProductVariantId;
    private Long ecommerceProductId;
    private Integer quantity;
    private String reference;
    private Long ecommerceColorId;
    private Long ecommerceSizeId;

    public Long getECommerceProductVariantId() {
        return ecommerceProductVariantId;
    }

    public void setECommerceProductVariantId(Long ecommerceProductVariantId) {
        this.ecommerceProductVariantId = ecommerceProductVariantId;
    }

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getECommerceColorId() {
        return ecommerceColorId;
    }

    public void setECommerceColorId(Long ecommerceColorId) {
        this.ecommerceColorId = ecommerceColorId;
    }

    public Long getECommerceSizeId() {
        return ecommerceSizeId;
    }

    public void setECommerceSizeId(Long ecommerceSizeId) {
        this.ecommerceSizeId = ecommerceSizeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceProductVariant that = (ECommerceProductVariant) o;

        if (ecommerceProductVariantId != null ? !ecommerceProductVariantId.equals(that.ecommerceProductVariantId) : that.ecommerceProductVariantId != null)
            return false;
        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) return false;
        if (ecommerceColorId != null ? !ecommerceColorId.equals(that.ecommerceColorId) : that.ecommerceColorId != null)
            return false;
        return ecommerceSizeId != null ? ecommerceSizeId.equals(that.ecommerceSizeId) : that.ecommerceSizeId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductVariantId != null ? ecommerceProductVariantId.hashCode() : 0;
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + (ecommerceColorId != null ? ecommerceColorId.hashCode() : 0);
        result = 31 * result + (ecommerceSizeId != null ? ecommerceSizeId.hashCode() : 0);
        return result;
    }
}
