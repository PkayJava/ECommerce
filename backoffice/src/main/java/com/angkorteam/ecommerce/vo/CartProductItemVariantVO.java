package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class CartProductItemVariantVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("remote_id")
    private Long remoteId;

    @Expose
    @SerializedName("product_id")
    private Long productId;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("price")
    private Double price;

    @Expose
    @SerializedName("price_formatted")
    private String priceFormatted;

    @Expose
    @SerializedName("category")
    private Long category;

    @Expose
    @SerializedName("discount_price")
    private Double discountPrice;

    @Expose
    @SerializedName("discount_price_formatted")
    private String discountPriceFormatted;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("main_image")
    private String mainImage;

    @Expose
    @SerializedName("color")
    private ProductColorVO color;

    @Expose
    @SerializedName("size")
    private ProductSizeVO size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPriceFormatted() {
        return priceFormatted;
    }

    public void setPriceFormatted(String priceFormatted) {
        this.priceFormatted = priceFormatted;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPriceFormatted() {
        return discountPriceFormatted;
    }

    public void setDiscountPriceFormatted(String discountPriceFormatted) {
        this.discountPriceFormatted = discountPriceFormatted;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public ProductColorVO getColor() {
        return color;
    }

    public void setColor(ProductColorVO color) {
        this.color = color;
    }

    public ProductSizeVO getSize() {
        return size;
    }

    public void setSize(ProductSizeVO size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartProductItemVariantVO variantVO = (CartProductItemVariantVO) o;

        if (id != null ? !id.equals(variantVO.id) : variantVO.id != null) return false;
        if (remoteId != null ? !remoteId.equals(variantVO.remoteId) : variantVO.remoteId != null) return false;
        if (productId != null ? !productId.equals(variantVO.productId) : variantVO.productId != null) return false;
        if (url != null ? !url.equals(variantVO.url) : variantVO.url != null) return false;
        if (name != null ? !name.equals(variantVO.name) : variantVO.name != null) return false;
        if (price != null ? !price.equals(variantVO.price) : variantVO.price != null) return false;
        if (priceFormatted != null ? !priceFormatted.equals(variantVO.priceFormatted) : variantVO.priceFormatted != null)
            return false;
        if (category != null ? !category.equals(variantVO.category) : variantVO.category != null) return false;
        if (discountPrice != null ? !discountPrice.equals(variantVO.discountPrice) : variantVO.discountPrice != null)
            return false;
        if (discountPriceFormatted != null ? !discountPriceFormatted.equals(variantVO.discountPriceFormatted) : variantVO.discountPriceFormatted != null)
            return false;
        if (currency != null ? !currency.equals(variantVO.currency) : variantVO.currency != null) return false;
        if (code != null ? !code.equals(variantVO.code) : variantVO.code != null) return false;
        if (description != null ? !description.equals(variantVO.description) : variantVO.description != null)
            return false;
        if (mainImage != null ? !mainImage.equals(variantVO.mainImage) : variantVO.mainImage != null) return false;
        if (color != null ? !color.equals(variantVO.color) : variantVO.color != null) return false;
        return size != null ? size.equals(variantVO.size) : variantVO.size == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (remoteId != null ? remoteId.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (priceFormatted != null ? priceFormatted.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (discountPrice != null ? discountPrice.hashCode() : 0);
        result = 31 * result + (discountPriceFormatted != null ? discountPriceFormatted.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (mainImage != null ? mainImage.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }
}
