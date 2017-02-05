package com.angkorteam.ecommerce.mobile.drawerMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class DrawerItemCategory implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("original_id")
    private long originalId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("children")
    private List<DrawerItemCategory> children;

    @Expose
    @SerializedName("type")
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DrawerItemCategory> getChildren() {
        return children;
    }

    public void setChildren(List<DrawerItemCategory> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
