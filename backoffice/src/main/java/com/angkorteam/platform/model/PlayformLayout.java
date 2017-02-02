package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlayformLayout implements Serializable {

    private Long platformLayoutId;
    private String name;
    private String description;
    private String javaClass;

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

    public Long getPlatformLayoutId() {
        return platformLayoutId;
    }

    public void setPlatformLayoutId(Long platformLayoutId) {
        this.platformLayoutId = platformLayoutId;
    }
}
