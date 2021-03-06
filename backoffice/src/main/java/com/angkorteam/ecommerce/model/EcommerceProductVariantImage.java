package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceProductVariantImage implements Serializable {

    private Long ecommerceProductVariantImageId;
    private Long ecommerceProductVariantId;
    private String name;
    private Long platformFileId;
    private Long ecommerceProductId;

    public Long getEcommerceProductVariantImageId() {
        return ecommerceProductVariantImageId;
    }

    public void setEcommerceProductVariantImageId(Long ecommerceProductVariantImageId) {
        this.ecommerceProductVariantImageId = ecommerceProductVariantImageId;
    }

    public Long getEcommerceProductVariantId() {
        return ecommerceProductVariantId;
    }

    public void setEcommerceProductVariantId(Long ecommerceProductVariantId) {
        this.ecommerceProductVariantId = ecommerceProductVariantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlatformFileId() {
        return platformFileId;
    }

    public void setPlatformFileId(Long platformFileId) {
        this.platformFileId = platformFileId;
    }

    public Long getEcommerceProductId() {
        return ecommerceProductId;
    }

    public void setEcommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceProductVariantImage that = (EcommerceProductVariantImage) o;

        if (ecommerceProductVariantImageId != null ? !ecommerceProductVariantImageId.equals(that.ecommerceProductVariantImageId) : that.ecommerceProductVariantImageId != null)
            return false;
        if (ecommerceProductVariantId != null ? !ecommerceProductVariantId.equals(that.ecommerceProductVariantId) : that.ecommerceProductVariantId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (platformFileId != null ? !platformFileId.equals(that.platformFileId) : that.platformFileId != null) return false;
        return ecommerceProductId != null ? ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductVariantImageId != null ? ecommerceProductVariantImageId.hashCode() : 0;
        result = 31 * result + (ecommerceProductVariantId != null ? ecommerceProductVariantId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (platformFileId != null ? platformFileId.hashCode() : 0);
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        return result;
    }
}
