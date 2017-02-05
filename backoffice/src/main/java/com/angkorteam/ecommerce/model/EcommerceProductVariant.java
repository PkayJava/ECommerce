package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceProductVariant implements Serializable {

    private Long ecommerceProductVariantId;
    private Long ecommerceProductId;
    private Integer quantity;
    private String reference;
    private Long ecommerceColorId;
    private Long ecommerceSizeId;

    public Long getEcommerceProductVariantId() {
        return ecommerceProductVariantId;
    }

    public void setEcommerceProductVariantId(Long ecommerceProductVariantId) {
        this.ecommerceProductVariantId = ecommerceProductVariantId;
    }

    public Long getEcommerceProductId() {
        return ecommerceProductId;
    }

    public void setEcommerceProductId(Long ecommerceProductId) {
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

    public Long getEcommerceColorId() {
        return ecommerceColorId;
    }

    public void setEcommerceColorId(Long ecommerceColorId) {
        this.ecommerceColorId = ecommerceColorId;
    }

    public Long getEcommerceSizeId() {
        return ecommerceSizeId;
    }

    public void setEcommerceSizeId(Long ecommerceSizeId) {
        this.ecommerceSizeId = ecommerceSizeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceProductVariant that = (EcommerceProductVariant) o;

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
