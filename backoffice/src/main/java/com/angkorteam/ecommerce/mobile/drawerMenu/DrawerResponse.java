package com.angkorteam.ecommerce.mobile.drawerMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class DrawerResponse implements Serializable {

    @Expose
    @SerializedName("navigation")
    private List<DrawerItemCategory> navigation;

    @Expose
    @SerializedName("pages")
    private List<DrawerItemPage> pages;

    public List<DrawerItemCategory> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<DrawerItemCategory> navigation) {
        this.navigation = navigation;
    }

    public List<DrawerItemPage> getPages() {
        return pages;
    }

    public void setPages(List<DrawerItemPage> pages) {
        this.pages = pages;
    }
}
