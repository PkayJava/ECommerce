package com.angkorteam.ecommerce.mobile.cart;

import com.angkorteam.ecommerce.mobile.product.ProductColor;
import com.angkorteam.ecommerce.mobile.product.ProductSize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class CartProductItemVariant implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("remote_id")
    private long remoteId;

    @Expose
    @SerializedName("product_id")
    private long productId;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("price")
    private double price;

    @Expose
    @SerializedName("price_formatted")
    private String priceFormatted;

    @Expose
    @SerializedName("category")
    private long category;

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
    private ProductColor color;

    @Expose
    @SerializedName("size")
    private ProductSize size;

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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceFormatted() {
        return priceFormatted;
    }

    public void setPriceFormatted(String priceFormatted) {
        this.priceFormatted = priceFormatted;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
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

    public ProductColor getColor() {
        return color;
    }

    public void setColor(ProductColor color) {
        this.color = color;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }
}
