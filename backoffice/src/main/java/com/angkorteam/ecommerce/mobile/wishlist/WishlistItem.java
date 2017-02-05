package com.angkorteam.ecommerce.mobile.wishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class WishlistItem implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("variant")
    private WishlistProductVariant variant;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WishlistProductVariant getVariant() {
        return variant;
    }

    public void setVariant(WishlistProductVariant variant) {
        this.variant = variant;
    }
}
