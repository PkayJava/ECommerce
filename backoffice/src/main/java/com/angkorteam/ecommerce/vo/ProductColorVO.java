package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ProductColorVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("remote_id")
    private Long remoteId;

    @Expose
    @SerializedName("value")
    private String value;

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("img")
    private String img;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductColorVO colorVO = (ProductColorVO) o;

        if (id != null ? !id.equals(colorVO.id) : colorVO.id != null) return false;
        if (remoteId != null ? !remoteId.equals(colorVO.remoteId) : colorVO.remoteId != null) return false;
        if (value != null ? !value.equals(colorVO.value) : colorVO.value != null) return false;
        if (code != null ? !code.equals(colorVO.code) : colorVO.code != null) return false;
        return img != null ? img.equals(colorVO.img) : colorVO.img == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (remoteId != null ? remoteId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        return result;
    }
}
