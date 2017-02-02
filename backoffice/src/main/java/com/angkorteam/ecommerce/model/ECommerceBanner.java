package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceBanner implements Serializable {

    private Long ecommerceBannerId;
    private Long imageUrlFileId;
    private Long ecommerceProductId;
    private Long ecommerceCategoryId;
    private String name;
    private String type;
    private Integer order;

    public Long getECommerceBannerId() {
        return ecommerceBannerId;
    }

    public void setECommerceBannerId(Long ecommerceBannerId) {
        this.ecommerceBannerId = ecommerceBannerId;
    }

    public Long getImageUrlFileId() {
        return imageUrlFileId;
    }

    public void setImageUrlFileId(Long imageUrlFileId) {
        this.imageUrlFileId = imageUrlFileId;
    }

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Long getECommerceCategoryId() {
        return ecommerceCategoryId;
    }

    public void setECommerceCategoryId(Long ecommerceCategoryId) {
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

        ECommerceBanner that = (ECommerceBanner) o;

        if (ecommerceBannerId != null ? !ecommerceBannerId.equals(that.ecommerceBannerId) : that.ecommerceBannerId != null)
            return false;
        if (imageUrlFileId != null ? !imageUrlFileId.equals(that.imageUrlFileId) : that.imageUrlFileId != null)
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
        result = 31 * result + (imageUrlFileId != null ? imageUrlFileId.hashCode() : 0);
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        result = 31 * result + (ecommerceCategoryId != null ? ecommerceCategoryId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
