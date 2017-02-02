package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformPage implements Serializable {

    private Long pageId;
    private Long layoutId;
    private String javaClass;
    private String path;
    private String htmlTitle;
    private String pageTitle;
    private String pageDescription;
    private Integer version;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformPage page = (PlatformPage) o;

        if (pageId != null ? !pageId.equals(page.pageId) : page.pageId != null) return false;
        if (layoutId != null ? !layoutId.equals(page.layoutId) : page.layoutId != null) return false;
        if (javaClass != null ? !javaClass.equals(page.javaClass) : page.javaClass != null) return false;
        if (path != null ? !path.equals(page.path) : page.path != null) return false;
        if (htmlTitle != null ? !htmlTitle.equals(page.htmlTitle) : page.htmlTitle != null) return false;
        if (pageTitle != null ? !pageTitle.equals(page.pageTitle) : page.pageTitle != null) return false;
        if (pageDescription != null ? !pageDescription.equals(page.pageDescription) : page.pageDescription != null)
            return false;
        return version != null ? version.equals(page.version) : page.version == null;
    }

    @Override
    public int hashCode() {
        int result = pageId != null ? pageId.hashCode() : 0;
        result = 31 * result + (layoutId != null ? layoutId.hashCode() : 0);
        result = 31 * result + (javaClass != null ? javaClass.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (htmlTitle != null ? htmlTitle.hashCode() : 0);
        result = 31 * result + (pageTitle != null ? pageTitle.hashCode() : 0);
        result = 31 * result + (pageDescription != null ? pageDescription.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
