package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformLocalization implements Serializable {

    private Long platformLocalizationId;
    private String key;
    private String language;
    private String label;
    private Integer version;

    public Long getPlatformLocalizationId() {
        return platformLocalizationId;
    }

    public void setPlatformLocalizationId(Long platformLocalizationId) {
        this.platformLocalizationId = platformLocalizationId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
