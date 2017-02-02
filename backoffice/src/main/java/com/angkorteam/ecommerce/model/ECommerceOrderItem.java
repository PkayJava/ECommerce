package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceOrderItem implements Serializable {

    private Long ecommerceOrderItemId;
    private Long ecommerceOrderId;
    private Long ecommerceCategoryId;
    private Integer quantity;
    private Double totalPrice;
    private Long ecommerceProductId;
    private Long ecommerceProductVariantId;
    private String productUrl;
    private String productName;
    private Double productPrice;
    private Double productShippingPrice;
    private String productReference;
    private Double productDiscountPrice;
    private String productDescription;
    private String productMainImage;
    private Long productMainImageFileId;
    private String variantReference;
    private Long ecommerceColorId;
    private String colorValue;
    private String colorCode;
    private String colorImg;
    private String colorReference;
    private Long colorImgFileId;
    private Long ecommerceSizeId;
    private String sizeValue;
    private String sizeReference;

    public Long getECommerceOrderItemId() {
        return ecommerceOrderItemId;
    }

    public void setECommerceOrderItemId(Long ecommerceOrderItemId) {
        this.ecommerceOrderItemId = ecommerceOrderItemId;
    }

    public Long getECommerceOrderId() {
        return ecommerceOrderId;
    }

    public void setECommerceOrderId(Long ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

    public Long getECommerceCategoryId() {
        return ecommerceCategoryId;
    }

    public void setECommerceCategoryId(Long ecommerceCategoryId) {
        this.ecommerceCategoryId = ecommerceCategoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    public Long getECommerceProductVariantId() {
        return ecommerceProductVariantId;
    }

    public void setECommerceProductVariantId(Long ecommerceProductVariantId) {
        this.ecommerceProductVariantId = ecommerceProductVariantId;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductShippingPrice() {
        return productShippingPrice;
    }

    public void setProductShippingPrice(Double productShippingPrice) {
        this.productShippingPrice = productShippingPrice;
    }

    public String getProductReference() {
        return productReference;
    }

    public void setProductReference(String productReference) {
        this.productReference = productReference;
    }

    public Double getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(Double productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public Long getProductMainImageFileId() {
        return productMainImageFileId;
    }

    public void setProductMainImageFileId(Long productMainImageFileId) {
        this.productMainImageFileId = productMainImageFileId;
    }

    public String getVariantReference() {
        return variantReference;
    }

    public void setVariantReference(String variantReference) {
        this.variantReference = variantReference;
    }

    public Long getECommerceColorId() {
        return ecommerceColorId;
    }

    public void setECommerceColorId(Long ecommerceColorId) {
        this.ecommerceColorId = ecommerceColorId;
    }

    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    public String getColorReference() {
        return colorReference;
    }

    public void setColorReference(String colorReference) {
        this.colorReference = colorReference;
    }

    public Long getColorImgFileId() {
        return colorImgFileId;
    }

    public void setColorImgFileId(Long colorImgFileId) {
        this.colorImgFileId = colorImgFileId;
    }

    public Long getECommerceSizeId() {
        return ecommerceSizeId;
    }

    public void setECommerceSizeId(Long ecommerceSizeId) {
        this.ecommerceSizeId = ecommerceSizeId;
    }

    public String getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(String sizeValue) {
        this.sizeValue = sizeValue;
    }

    public String getSizeReference() {
        return sizeReference;
    }

    public void setSizeReference(String sizeReference) {
        this.sizeReference = sizeReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceOrderItem that = (ECommerceOrderItem) o;

        if (ecommerceOrderItemId != null ? !ecommerceOrderItemId.equals(that.ecommerceOrderItemId) : that.ecommerceOrderItemId != null)
            return false;
        if (ecommerceOrderId != null ? !ecommerceOrderId.equals(that.ecommerceOrderId) : that.ecommerceOrderId != null)
            return false;
        if (ecommerceCategoryId != null ? !ecommerceCategoryId.equals(that.ecommerceCategoryId) : that.ecommerceCategoryId != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
        if (ecommerceProductId != null ? !ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId != null)
            return false;
        if (ecommerceProductVariantId != null ? !ecommerceProductVariantId.equals(that.ecommerceProductVariantId) : that.ecommerceProductVariantId != null)
            return false;
        if (productUrl != null ? !productUrl.equals(that.productUrl) : that.productUrl != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (productPrice != null ? !productPrice.equals(that.productPrice) : that.productPrice != null) return false;
        if (productShippingPrice != null ? !productShippingPrice.equals(that.productShippingPrice) : that.productShippingPrice != null)
            return false;
        if (productReference != null ? !productReference.equals(that.productReference) : that.productReference != null)
            return false;
        if (productDiscountPrice != null ? !productDiscountPrice.equals(that.productDiscountPrice) : that.productDiscountPrice != null)
            return false;
        if (productDescription != null ? !productDescription.equals(that.productDescription) : that.productDescription != null)
            return false;
        if (productMainImage != null ? !productMainImage.equals(that.productMainImage) : that.productMainImage != null)
            return false;
        if (productMainImageFileId != null ? !productMainImageFileId.equals(that.productMainImageFileId) : that.productMainImageFileId != null)
            return false;
        if (variantReference != null ? !variantReference.equals(that.variantReference) : that.variantReference != null)
            return false;
        if (ecommerceColorId != null ? !ecommerceColorId.equals(that.ecommerceColorId) : that.ecommerceColorId != null)
            return false;
        if (colorValue != null ? !colorValue.equals(that.colorValue) : that.colorValue != null) return false;
        if (colorCode != null ? !colorCode.equals(that.colorCode) : that.colorCode != null) return false;
        if (colorImg != null ? !colorImg.equals(that.colorImg) : that.colorImg != null) return false;
        if (colorReference != null ? !colorReference.equals(that.colorReference) : that.colorReference != null)
            return false;
        if (colorImgFileId != null ? !colorImgFileId.equals(that.colorImgFileId) : that.colorImgFileId != null)
            return false;
        if (ecommerceSizeId != null ? !ecommerceSizeId.equals(that.ecommerceSizeId) : that.ecommerceSizeId != null)
            return false;
        if (sizeValue != null ? !sizeValue.equals(that.sizeValue) : that.sizeValue != null) return false;
        return sizeReference != null ? sizeReference.equals(that.sizeReference) : that.sizeReference == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceOrderItemId != null ? ecommerceOrderItemId.hashCode() : 0;
        result = 31 * result + (ecommerceOrderId != null ? ecommerceOrderId.hashCode() : 0);
        result = 31 * result + (ecommerceCategoryId != null ? ecommerceCategoryId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        result = 31 * result + (ecommerceProductVariantId != null ? ecommerceProductVariantId.hashCode() : 0);
        result = 31 * result + (productUrl != null ? productUrl.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productPrice != null ? productPrice.hashCode() : 0);
        result = 31 * result + (productShippingPrice != null ? productShippingPrice.hashCode() : 0);
        result = 31 * result + (productReference != null ? productReference.hashCode() : 0);
        result = 31 * result + (productDiscountPrice != null ? productDiscountPrice.hashCode() : 0);
        result = 31 * result + (productDescription != null ? productDescription.hashCode() : 0);
        result = 31 * result + (productMainImage != null ? productMainImage.hashCode() : 0);
        result = 31 * result + (productMainImageFileId != null ? productMainImageFileId.hashCode() : 0);
        result = 31 * result + (variantReference != null ? variantReference.hashCode() : 0);
        result = 31 * result + (ecommerceColorId != null ? ecommerceColorId.hashCode() : 0);
        result = 31 * result + (colorValue != null ? colorValue.hashCode() : 0);
        result = 31 * result + (colorCode != null ? colorCode.hashCode() : 0);
        result = 31 * result + (colorImg != null ? colorImg.hashCode() : 0);
        result = 31 * result + (colorReference != null ? colorReference.hashCode() : 0);
        result = 31 * result + (colorImgFileId != null ? colorImgFileId.hashCode() : 0);
        result = 31 * result + (ecommerceSizeId != null ? ecommerceSizeId.hashCode() : 0);
        result = 31 * result + (sizeValue != null ? sizeValue.hashCode() : 0);
        result = 31 * result + (sizeReference != null ? sizeReference.hashCode() : 0);
        return result;
    }
}
