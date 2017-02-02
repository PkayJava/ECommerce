package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceProductVariantImage implements Serializable {

    private Long ecommerceProductVariantImageId;
    private Long ecommerceProductVariantId;
    private String name;
    private Long fileId;
    private Long ecommerceProductId;

    public Long getECommerceProductVariantImageId() {
        return ecommerceProductVariantImageId;
    }

    public void setECommerceProductVariantImageId(Long ecommerceProductVariantImageId) {
        this.ecommerceProductVariantImageId = ecommerceProductVariantImageId;
    }

    public Long getECommerceProductVariantId() {
        return ecommerceProductVariantId;
    }

    public void setECommerceProductVariantId(Long ecommerceProductVariantId) {
        this.ecommerceProductVariantId = ecommerceProductVariantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceProductVariantImage that = (ECommerceProductVariantImage) o;

        if (ecommerceProductVariantImageId != null ? !ecommerceProductVariantImageId.equals(that.ecommerceProductVariantImageId) : that.ecommerceProductVariantImageId != null)
            return false;
        if (ecommerceProductVariantId != null ? !ecommerceProductVariantId.equals(that.ecommerceProductVariantId) : that.ecommerceProductVariantId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false;
        return ecommerceProductId != null ? ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductVariantImageId != null ? ecommerceProductVariantImageId.hashCode() : 0;
        result = 31 * result + (ecommerceProductVariantId != null ? ecommerceProductVariantId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        return result;
    }
}
