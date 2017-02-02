package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformMenu implements Serializable {

    private Long platformMenuId;
    private String title;
    private String path;
    private String icon;
    private Long platformSectionId;
    private Long parentPlatformMenuId;
    private Integer order;
    private Integer version;

    public Long getPlatformMenuId() {
        return platformMenuId;
    }

    public void setPlatformMenuId(Long platformMenuId) {
        this.platformMenuId = platformMenuId;
    }

    public Long getPlatformSectionId() {
        return platformSectionId;
    }

    public void setPlatformSectionId(Long platformSectionId) {
        this.platformSectionId = platformSectionId;
    }

    public Long getParentPlatformMenuId() {
        return parentPlatformMenuId;
    }

    public void setParentPlatformMenuId(Long parentPlatformMenuId) {
        this.parentPlatformMenuId = parentPlatformMenuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
