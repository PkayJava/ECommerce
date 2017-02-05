package com.angkorteam.ecommerce.mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class Banner implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("target")
    private String target;

    @Expose
    @SerializedName("image_url")
    private String imageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
