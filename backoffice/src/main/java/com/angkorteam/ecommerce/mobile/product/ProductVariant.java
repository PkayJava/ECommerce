package com.angkorteam.ecommerce.mobile.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class ProductVariant implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("color")
    private ProductColor color;

    @Expose
    @SerializedName("size")
    private ProductSize size;

    @Expose
    @SerializedName("images")
    private List<String> images;

    @Expose
    @SerializedName("code")
    private String code;

    private Long colorId;

    private Long sizeId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }
}
