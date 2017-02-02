package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformPageRole implements Serializable {

    private Long pageRoleId;
    private Long pageId;
    private Long roleId;

    public Long getPageRoleId() {
        return pageRoleId;
    }

    public void setPageRoleId(Long pageRoleId) {
        this.pageRoleId = pageRoleId;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformPageRole pageRole = (PlatformPageRole) o;

        if (pageRoleId != null ? !pageRoleId.equals(pageRole.pageRoleId) : pageRole.pageRoleId != null) return false;
        if (pageId != null ? !pageId.equals(pageRole.pageId) : pageRole.pageId != null) return false;
        return roleId != null ? roleId.equals(pageRole.roleId) : pageRole.roleId == null;
    }

    @Override
    public int hashCode() {
        int result = pageRoleId != null ? pageRoleId.hashCode() : 0;
        result = 31 * result + (pageId != null ? pageId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
