package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformSetting implements Serializable {

    private Long settingId;
    private String key;
    private String description;
    private String value;
    private Integer version;

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformSetting setting = (PlatformSetting) o;

        if (settingId != null ? !settingId.equals(setting.settingId) : setting.settingId != null) return false;
        if (key != null ? !key.equals(setting.key) : setting.key != null) return false;
        if (description != null ? !description.equals(setting.description) : setting.description != null) return false;
        if (value != null ? !value.equals(setting.value) : setting.value != null) return false;
        return version != null ? version.equals(setting.version) : setting.version == null;
    }

    @Override
    public int hashCode() {
        int result = settingId != null ? settingId.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
