package com.angkorteam.ecommerce.mobile.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class ProductColor implements Serializable {

    @Expose
    @SerializedName("id")
    private long id = 0;

    @Expose
    @SerializedName("remote_id")
    private long remoteId;

    @Expose
    @SerializedName("value")
    private String value;

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("img")
    private String img;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
