package com.angkorteam.ecommerce.vo;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ProductReviewVO implements Serializable {

    private Long productId;
    private String name;
    private Double normalPrice;
    private String category;
    private String brand;
    private String login;
    private String fullName;
    private Double discountPrice;
    private Double shippingPrice;
    private String reference;
    private String description;
    private String mainImage;
    private String mainImageHighRes;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImageHighRes() {
        return mainImageHighRes;
    }

    public void setMainImageHighRes(String mainImageHighRes) {
        this.mainImageHighRes = mainImageHighRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductReviewVO productVO = (ProductReviewVO) o;

        if (productId != null ? !productId.equals(productVO.productId) : productVO.productId != null) return false;
        if (name != null ? !name.equals(productVO.name) : productVO.name != null) return false;
        if (normalPrice != null ? !normalPrice.equals(productVO.normalPrice) : productVO.normalPrice != null)
            return false;
        if (category != null ? !category.equals(productVO.category) : productVO.category != null) return false;
        if (brand != null ? !brand.equals(productVO.brand) : productVO.brand != null) return false;
        if (login != null ? !login.equals(productVO.login) : productVO.login != null) return false;
        if (fullName != null ? !fullName.equals(productVO.fullName) : productVO.fullName != null) return false;
        if (discountPrice != null ? !discountPrice.equals(productVO.discountPrice) : productVO.discountPrice != null)
            return false;
        if (shippingPrice != null ? !shippingPrice.equals(productVO.shippingPrice) : productVO.shippingPrice != null)
            return false;
        if (reference != null ? !reference.equals(productVO.reference) : productVO.reference != null) return false;
        if (description != null ? !description.equals(productVO.description) : productVO.description != null)
            return false;
        if (mainImage != null ? !mainImage.equals(productVO.mainImage) : productVO.mainImage != null) return false;
        return mainImageHighRes != null ? mainImageHighRes.equals(productVO.mainImageHighRes) : productVO.mainImageHighRes == null;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (normalPrice != null ? normalPrice.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (discountPrice != null ? discountPrice.hashCode() : 0);
        result = 31 * result + (shippingPrice != null ? shippingPrice.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (mainImage != null ? mainImage.hashCode() : 0);
        result = 31 * result + (mainImageHighRes != null ? mainImageHighRes.hashCode() : 0);
        return result;
    }
}
