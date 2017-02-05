package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceProduct implements Serializable {

    private Long ecommerceProductId;
    private Long platformUserId;
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
    private Long mainImagePlatformFileId;
    private Long mainImageHighResPlatformFileId;
    private Date lastModified;
    private Integer popularity;
    private Double normalPrice;

    public Long getEcommerceProductId() {
        return ecommerceProductId;
    }

    public void setEcommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Long getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
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

    public Long getEcommerceCategoryId() {
        return ecommerceCategoryId;
    }

    public void setEcommerceCategoryId(Long ecommerceCategoryId) {
        this.ecommerceCategoryId = ecommerceCategoryId;
    }

    public Long getEcommerceBrandId() {
        return ecommerceBrandId;
    }

    public void setEcommerceBrandId(Long ecommerceBrandId) {
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

    public Long getMainImagePlatformFileId() {
        return mainImagePlatformFileId;
    }

    public void setMainImagePlatformFileId(Long mainImagePlatformFileId) {
        this.mainImagePlatformFileId = mainImagePlatformFileId;
    }

    public Long getMainImageHighResPlatformFileId() {
        return mainImageHighResPlatformFileId;
    }

    public void setMainImageHighResPlatformFileId(Long mainImageHighResPlatformFileId) {
        this.mainImageHighResPlatformFileId = mainImageHighResPlatformFileId;
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

        EcommerceProduct that = (EcommerceProduct) o;

        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        if (platformUserId != null ? !platformUserId.equals(that.platformUserId) : that.platformUserId != null)
            return false;
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
        if (mainImagePlatformFileId != null ? !mainImagePlatformFileId.equals(that.mainImagePlatformFileId) : that.mainImagePlatformFileId != null)
            return false;
        if (mainImageHighResPlatformFileId != null ? !mainImageHighResPlatformFileId.equals(that.mainImageHighResPlatformFileId) : that.mainImageHighResPlatformFileId != null)
            return false;
        if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;
        if (popularity != null ? !popularity.equals(that.popularity) : that.popularity != null) return false;
        return normalPrice != null ? normalPrice.equals(that.normalPrice) : that.normalPrice == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceProductId != null ? ecommerceProductId.hashCode() : 0;
        result = 31 * result + (platformUserId != null ? platformUserId.hashCode() : 0);
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
        result = 31 * result + (mainImagePlatformFileId != null ? mainImagePlatformFileId.hashCode() : 0);
        result = 31 * result + (mainImageHighResPlatformFileId != null ? mainImageHighResPlatformFileId.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (popularity != null ? popularity.hashCode() : 0);
        result = 31 * result + (normalPrice != null ? normalPrice.hashCode() : 0);
        return result;
    }
}
