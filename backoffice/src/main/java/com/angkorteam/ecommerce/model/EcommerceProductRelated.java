package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceProductRelated implements Serializable {

    private Long ecommerceProductRelatedId;
    private Long ecommerceProductId;
    private Long relatedEcommerceProductId;

    public Long getEcommerceProductRelatedId() {
        return ecommerceProductRelatedId;
    }

    public void setEcommerceProductRelatedId(Long ecommerceProductRelatedId) {
        this.ecommerceProductRelatedId = ecommerceProductRelatedId;
    }

    public Long getEcommerceProductId() {
        return ecommerceProductId;
    }

    public void setEcommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Long getRelatedEcommerceProductId() {
        return relatedEcommerceProductId;
    }

    public void setRelatedEcommerceProductId(Long relatedEcommerceProductId) {
        this.relatedEcommerceProductId = relatedEcommerceProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceProductRelated that = (EcommerceProductRelated) o;

        if (ecommerceProductRelatedId != null ? !ecommerceProductRelatedId.equals(that.ecommerceProductRelatedId) : that.ecommerceProductRelatedId != null)
            return false;
        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        return relatedEcommerceProductId != null ? relatedEcommerceProductId.equals(that.relatedEcommerceProductId) : that.relatedEcommerceProductId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductRelatedId != null ? ecommerceProductRelatedId.hashCode() : 0;
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        result = 31 * result + (relatedEcommerceProductId != null ? relatedEcommerceProductId.hashCode() : 0);
        return result;
    }
}
