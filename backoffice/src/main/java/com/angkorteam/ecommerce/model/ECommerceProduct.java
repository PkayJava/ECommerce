package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceProduct implements Serializable {

    private Long ecommerceProductId;
    private Long userId;
    private String name;
    private Double price;
    private String url;
    private Boolean ready;
    private Double shippingPrice;
    private Integer quantity;
    private Long ecommerceCategoryId;
    private Long ecommerceBrandId;
    private Double discountPrice;
    private String reference;
    private String description;
    private Long mainImageFileId;
    private Long mainImageHighResFileId;
    private Date lastModified;
    private Integer popularity;
    private Double normalPrice;

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getECommerceCategoryId() {
        return ecommerceCategoryId;
    }

    public void setECommerceCategoryId(Long ecommerceCategoryId) {
        this.ecommerceCategoryId = ecommerceCategoryId;
    }

    public Long getECommerceBrandId() {
        return ecommerceBrandId;
    }

    public void setECommerceBrandId(Long ecommerceBrandId) {
        this.ecommerceBrandId = ecommerceBrandId;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMainImageFileId() {
        return mainImageFileId;
    }

    public void setMainImageFileId(Long mainImageFileId) {
        this.mainImageFileId = mainImageFileId;
    }

    public Long getMainImageHighResFileId() {
        return mainImageHighResFileId;
    }

    public void setMainImageHighResFileId(Long mainImageHighResFileId) {
        this.mainImageHighResFileId = mainImageHighResFileId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceProduct that = (ECommerceProduct) o;

        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (ready != null ? !ready.equals(that.ready) : that.ready != null) return false;
        if (shippingPrice != null ? !shippingPrice.equals(that.shippingPrice) : that.shippingPrice != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (ecommerceCategoryId != null ? !ecommerceCategoryId.equals(that.ecommerceCategoryId) : that.ecommerceCategoryId != null)
            return false;
        if (ecommerceBrandId != null ? !ecommerceBrandId.equals(that.ecommerceBrandId) : that.ecommerceBrandId != null)
            return false;
        if (discountPrice != null ? !discountPrice.equals(that.discountPrice) : that.discountPrice != null)
            return false;
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (mainImageFileId != null ? !mainImageFileId.equals(that.mainImageFileId) : that.mainImageFileId != null)
            return false;
        if (mainImageHighResFileId != null ? !mainImageHighResFileId.equals(that.mainImageHighResFileId) : that.mainImageHighResFileId != null)
            return false;
        if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;
        if (popularity != null ? !popularity.equals(that.popularity) : that.popularity != null) return false;
        return normalPrice != null ? normalPrice.equals(that.normalPrice) : that.normalPrice == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductId != null ? ecommerceProductId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (ready != null ? ready.hashCode() : 0);
        result = 31 * result + (shippingPrice != null ? shippingPrice.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (ecommerceCategoryId != null ? ecommerceCategoryId.hashCode() : 0);
        result = 31 * result + (ecommerceBrandId != null ? ecommerceBrandId.hashCode() : 0);
        result = 31 * result + (discountPrice != null ? discountPrice.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (mainImageFileId != null ? mainImageFileId.hashCode() : 0);
        result = 31 * result + (mainImageHighResFileId != null ? mainImageHighResFileId.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (popularity != null ? popularity.hashCode() : 0);
        result = 31 * result + (normalPrice != null ? normalPrice.hashCode() : 0);
        return result;
    }
}
