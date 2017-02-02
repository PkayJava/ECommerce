package com.angkorteam.ecommerce.vo;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 1/2/17.
 */
public class ProductVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("remote_id")
    private Long remoteId = 0L;

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
    @SerializedName("brand")
    private String brand;

    @Expose
    @SerializedName("discount_price")
    private Double discountPrice;

    @Expose
    @SerializedName("discount_price_formatted")
    private String discountPriceFormatted;

    @Expose
    @SerializedName("currency")
    private String currency = "USD";

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
    @SerializedName("main_image_high_res")
    private String mainImageHighRes;

    @Expose
    @SerializedName("images")
    private List<String> images = Lists.newArrayList();

    @Expose
    @SerializedName("variants")
    private List<VariantVO> variants = Lists.newArrayList();

    private String productId;

    @Expose
    @SerializedName("shipping_price")
    private Double shippingPrice;

    @Expose
    @SerializedName("related")
    private List<RelatedVO> related;


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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getMainImageHighRes() {
        return mainImageHighRes;
    }

    public void setMainImageHighRes(String mainImageHighRes) {
        this.mainImageHighRes = mainImageHighRes;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<VariantVO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantVO> variants) {
        this.variants = variants;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public List<RelatedVO> getRelated() {
        return related;
    }

    public void setRelated(List<RelatedVO> related) {
        this.related = related;
    }
}
