package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceBanner implements Serializable {

    private Long ecommerceBannerId;
    private Long imageUrlPlatformFileId;
    private Long ecommerceProductId;
    private Long ecommerceCategoryId;
    private String name;
    private String type;
    private Integer order;
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getEcommerceBannerId() {
        return ecommerceBannerId;
    }

    public void setEcommerceBannerId(Long ecommerceBannerId) {
        this.ecommerceBannerId = ecommerceBannerId;
    }

    public Long getImageUrlPlatformFileId() {
        return imageUrlPlatformFileId;
    }

    public void setImageUrlPlatformFileId(Long imageUrlPlatformFileId) {
        this.imageUrlPlatformFileId = imageUrlPlatformFileId;
    }

    public Long getEcommerceProductId() {
        return ecommerceProductId;
    }

    public void setEcommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Long getEcommerceCategoryId() {
        return ecommerceCategoryId;
    }

    public void setEcommerceCategoryId(Long ecommerceCategoryId) {
        this.ecommerceCategoryId = ecommerceCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceBanner that = (EcommerceBanner) o;

        if (ecommerceBannerId != null ? !ecommerceBannerId.equals(that.ecommerceBannerId) : that.ecommerceBannerId != null)
            return false;
        if (imageUrlPlatformFileId != null ? !imageUrlPlatformFileId.equals(that.imageUrlPlatformFileId) : that.imageUrlPlatformFileId != null)
            return false;
        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        if (ecommerceCategoryId != null ? !ecommerceCategoryId.equals(that.ecommerceCategoryId) : that.ecommerceCategoryId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return order != null ? order.equals(that.order) : that.order == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceBannerId != null ? ecommerceBannerId.hashCode() : 0;
        result = 31 * result + (imageUrlPlatformFileId != null ? imageUrlPlatformFileId.hashCode() : 0);
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        result = 31 * result + (ecommerceCategoryId != null ? ecommerceCategoryId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
