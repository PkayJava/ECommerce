package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 1/2/17.
 */
public class VariantVO implements Serializable {

    private String variantId;

    private String colorId;

    private String sizeId;

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("color")
    private ColorVO color;

    @Expose
    @SerializedName("size")
    private SizeVO size;

    @Expose
    @SerializedName("images")
    private List<String> images;

    @Expose
    @SerializedName("code")
    private String code;

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ColorVO getColor() {
        return color;
    }

    public void setColor(ColorVO color) {
        this.color = color;
    }

    public SizeVO getSize() {
        return size;
    }

    public void setSize(SizeVO size) {
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
}
