package com.angkorteam.ecommerce.mobile.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class Product implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("remote_id")
    private long remoteId;

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
    @SerializedName("discount_price")
    private Double discountPrice;

    @Expose
    @SerializedName("discount_price_formatted")
    private String discountPriceFormatted;

    @Expose
    @SerializedName("category")
    private Long category;

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
    @SerializedName("main_image_high_res")
    private String mainImageHighRes;

    @Expose
    @SerializedName("variants")
    private List<ProductVariant> variants;

    @Expose
    @SerializedName("related")
    private List<Product> related;

    @Expose
    @SerializedName("phone_number")
    private String phoneNumber;

    @Expose
    @SerializedName("verified")
    private Boolean verified;

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    private Double shippingPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
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

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
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

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    public List<Product> getRelated() {
        return related;
    }

    public void setRelated(List<Product> related) {
        this.related = related;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
