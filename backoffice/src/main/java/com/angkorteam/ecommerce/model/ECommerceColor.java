package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceColor implements Serializable {

    private Long ecommerceColorId;
    private Long imgFileId;
    private String value;
    private String code;
    private String reference;

    public Long getECommerceColorId() {
        return ecommerceColorId;
    }

    public void setECommerceColorId(Long ecommerceColorId) {
        this.ecommerceColorId = ecommerceColorId;
    }

    public Long getImgFileId() {
        return imgFileId;
    }

    public void setImgFileId(Long imgFileId) {
        this.imgFileId = imgFileId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceColor that = (ECommerceColor) o;

        if (ecommerceColorId != null ? !ecommerceColorId.equals(that.ecommerceColorId) : that.ecommerceColorId != null)
            return false;
        if (imgFileId != null ? !imgFileId.equals(that.imgFileId) : that.imgFileId != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return reference != null ? reference.equals(that.reference) : that.reference == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceColorId != null ? ecommerceColorId.hashCode() : 0;
        result = 31 * result + (imgFileId != null ? imgFileId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        return result;
    }
}
