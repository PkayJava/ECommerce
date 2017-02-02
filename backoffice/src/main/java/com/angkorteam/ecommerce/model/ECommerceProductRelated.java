package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceProductRelated implements Serializable {

    private Long ecommerceProductRelatedId;
    private Long ecommerceProductId;
    private Long relatedECommerceProductId;

    public Long getECommerceProductRelatedId() {
        return ecommerceProductRelatedId;
    }

    public void setECommerceProductRelatedId(Long ecommerceProductRelatedId) {
        this.ecommerceProductRelatedId = ecommerceProductRelatedId;
    }

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Long getRelatedECommerceProductId() {
        return relatedECommerceProductId;
    }

    public void setRelatedECommerceProductId(Long relatedECommerceProductId) {
        this.relatedECommerceProductId = relatedECommerceProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceProductRelated that = (ECommerceProductRelated) o;

        if (ecommerceProductRelatedId != null ? !ecommerceProductRelatedId.equals(that.ecommerceProductRelatedId) : that.ecommerceProductRelatedId != null)
            return false;
        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        return relatedECommerceProductId != null ? relatedECommerceProductId.equals(that.relatedECommerceProductId) : that.relatedECommerceProductId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductRelatedId != null ? ecommerceProductRelatedId.hashCode() : 0;
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        result = 31 * result + (relatedECommerceProductId != null ? relatedECommerceProductId.hashCode() : 0);
        return result;
    }
}
