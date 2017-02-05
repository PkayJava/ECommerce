package com.angkorteam.ecommerce.mobile.delivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class Transport implements Serializable {

    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("text")
    private String text;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
