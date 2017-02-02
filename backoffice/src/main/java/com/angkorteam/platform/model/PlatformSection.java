package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformSection implements Serializable {

    private Long platformSectionId;
    private String title;
    private Integer order;
    private Integer version;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getPlatformSectionId() {
        return platformSectionId;
    }

    public void setPlatformSectionId(Long platformSectionId) {
        this.platformSectionId = platformSectionId;
    }
}
