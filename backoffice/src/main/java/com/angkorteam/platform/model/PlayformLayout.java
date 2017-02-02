package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlayformLayout implements Serializable{

    private Long layoutId;
    private String name;
    private String description;
    private String javaClass;

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayformLayout layout = (PlayformLayout) o;

        if (layoutId != null ? !layoutId.equals(layout.layoutId) : layout.layoutId != null) return false;
        if (name != null ? !name.equals(layout.name) : layout.name != null) return false;
        if (description != null ? !description.equals(layout.description) : layout.description != null) return false;
        return javaClass != null ? javaClass.equals(layout.javaClass) : layout.javaClass == null;
    }

    @Override
    public int hashCode() {
        int result = layoutId != null ? layoutId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (javaClass != null ? javaClass.hashCode() : 0);
        return result;
    }
}
