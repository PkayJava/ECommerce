package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceSize implements Serializable {

    private Long ecommerceSizeId;
    private String value;
    private String reference;
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getEcommerceSizeId() {
        return ecommerceSizeId;
    }

    public void setEcommerceSizeId(Long ecommerceSizeId) {
        this.ecommerceSizeId = ecommerceSizeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

        EcommerceSize that = (EcommerceSize) o;

        if (ecommerceSizeId != null ? !ecommerceSizeId.equals(that.ecommerceSizeId) : that.ecommerceSizeId != null)
            return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return reference != null ? reference.equals(that.reference) : that.reference == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceSizeId != null ? ecommerceSizeId.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        return result;
    }
}
