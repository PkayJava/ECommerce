package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformSetting implements Serializable {

    private Long platformSettingId;
    private String key;
    private String description;
    private String value;
    private Integer version;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getPlatformSettingId() {
        return platformSettingId;
    }

    public void setPlatformSettingId(Long platformSettingId) {
        this.platformSettingId = platformSettingId;
    }
}
