package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformMenuItem implements Serializable {

    private Long menuItemId;
    private Long menuId;
    private String title;
    private String icon;
    private Integer order;
    private Long pageId;
    private Long sectionId;
    private Integer version;

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformMenuItem menuItem = (PlatformMenuItem) o;

        if (menuItemId != null ? !menuItemId.equals(menuItem.menuItemId) : menuItem.menuItemId != null) return false;
        if (menuId != null ? !menuId.equals(menuItem.menuId) : menuItem.menuId != null) return false;
        if (title != null ? !title.equals(menuItem.title) : menuItem.title != null) return false;
        if (icon != null ? !icon.equals(menuItem.icon) : menuItem.icon != null) return false;
        if (order != null ? !order.equals(menuItem.order) : menuItem.order != null) return false;
        if (pageId != null ? !pageId.equals(menuItem.pageId) : menuItem.pageId != null) return false;
        if (sectionId != null ? !sectionId.equals(menuItem.sectionId) : menuItem.sectionId != null) return false;
        return version != null ? version.equals(menuItem.version) : menuItem.version == null;
    }

    @Override
    public int hashCode() {
        int result = menuItemId != null ? menuItemId.hashCode() : 0;
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (pageId != null ? pageId.hashCode() : 0);
        result = 31 * result + (sectionId != null ? sectionId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
