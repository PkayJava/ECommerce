package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformMenuItem implements Serializable {

    private Long platformMenuItemId;
    private Long platformMenuId;
    private String title;
    private String icon;
    private Integer order;
    private Long platformPageId;
    private Long platformSectionId;
    private Integer version;

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

    public Long getPlatformMenuItemId() {
        return platformMenuItemId;
    }

    public void setPlatformMenuItemId(Long platformMenuItemId) {
        this.platformMenuItemId = platformMenuItemId;
    }

    public Long getPlatformMenuId() {
        return platformMenuId;
    }

    public void setPlatformMenuId(Long platformMenuId) {
        this.platformMenuId = platformMenuId;
    }

    public Long getPlatformPageId() {
        return platformPageId;
    }

    public void setPlatformPageId(Long platformPageId) {
        this.platformPageId = platformPageId;
    }

    public Long getPlatformSectionId() {
        return platformSectionId;
    }

    public void setPlatformSectionId(Long platformSectionId) {
        this.platformSectionId = platformSectionId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
