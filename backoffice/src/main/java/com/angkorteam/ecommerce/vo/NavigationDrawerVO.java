package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class NavigationDrawerVO implements Serializable {

    @Expose
    @SerializedName("navigation")
    private List<NavigationVO> navigation;

    @Expose
    @SerializedName("pages")
    private List<PageVO> pages;

    public List<NavigationVO> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<NavigationVO> navigation) {
        this.navigation = navigation;
    }

    public List<PageVO> getPages() {
        return pages;
    }

    public void setPages(List<PageVO> pages) {
        this.pages = pages;
    }
}
