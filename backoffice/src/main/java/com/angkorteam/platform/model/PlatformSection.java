package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformSection implements Serializable {

    private Long sectionId;
    private String title;
    private Integer order;
    private Integer version;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformSection section = (PlatformSection) o;

        if (sectionId != null ? !sectionId.equals(section.sectionId) : section.sectionId != null) return false;
        if (title != null ? !title.equals(section.title) : section.title != null) return false;
        if (order != null ? !order.equals(section.order) : section.order != null) return false;
        return version != null ? version.equals(section.version) : section.version == null;
    }

    @Override
    public int hashCode() {
        int result = sectionId != null ? sectionId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
