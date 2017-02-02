package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceProductImage implements Serializable {

    private Long ecommerceProductImageId;
    private Long ecommerceProductId;
    private String name;
    private Long fileId;

    public Long getECommerceProductImageId() {
        return ecommerceProductImageId;
    }

    public void setECommerceProductImageId(Long ecommerceProductImageId) {
        this.ecommerceProductImageId = ecommerceProductImageId;
    }

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceProductImage that = (ECommerceProductImage) o;

        if (ecommerceProductImageId != null ? !ecommerceProductImageId.equals(that.ecommerceProductImageId) : that.ecommerceProductImageId != null)
            return false;
        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return fileId != null ? fileId.equals(that.fileId) : that.fileId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductImageId != null ? ecommerceProductImageId.hashCode() : 0;
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        return result;
    }
}
