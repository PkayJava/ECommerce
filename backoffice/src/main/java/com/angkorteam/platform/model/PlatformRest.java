package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformRest implements Serializable {

    private Long restId;
    private String name;
    private String javaClass;
    private Integer version;
    private String description;

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformRest rest = (PlatformRest) o;

        if (restId != null ? !restId.equals(rest.restId) : rest.restId != null) return false;
        if (name != null ? !name.equals(rest.name) : rest.name != null) return false;
        if (javaClass != null ? !javaClass.equals(rest.javaClass) : rest.javaClass != null) return false;
        if (version != null ? !version.equals(rest.version) : rest.version != null) return false;
        return description != null ? description.equals(rest.description) : rest.description == null;
    }

    @Override
    public int hashCode() {
        int result = restId != null ? restId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (javaClass != null ? javaClass.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
