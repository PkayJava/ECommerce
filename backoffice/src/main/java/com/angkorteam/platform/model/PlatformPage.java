package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformPage implements Serializable {

    private Long platformPageId;
    private Long platformLayoutId;
    private String javaClass;
    private String path;
    private String htmlTitle;
    private String pageTitle;
    private String pageDescription;
    private Integer version;

    public Long getPlatformLayoutId() {
        return platformLayoutId;
    }

    public void setPlatformLayoutId(Long platformLayoutId) {
        this.platformLayoutId = platformLayoutId;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHtmlTitle() {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getPlatformPageId() {
        return platformPageId;
    }

    public void setPlatformPageId(Long platformPageId) {
        this.platformPageId = platformPageId;
    }
}
