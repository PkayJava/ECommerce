package com.angkorteam.ecommerce.mobile;

import com.angkorteam.platform.mobile.Metadata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShopResponse implements Serializable {

    @Expose
    @SerializedName("metadata")
    private Metadata metadata;

    @Expose
    @SerializedName("records")
    private List<Shop> shopList;

    public ShopResponse() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

}
