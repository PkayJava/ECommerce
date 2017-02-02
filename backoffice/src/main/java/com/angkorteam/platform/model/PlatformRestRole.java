package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformRestRole implements Serializable {

    private Long restRoleId;
    private Long restId;
    private Long roleId;

    public Long getRestRoleId() {
        return restRoleId;
    }

    public void setRestRoleId(Long restRoleId) {
        this.restRoleId = restRoleId;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
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

        PlatformRestRole restRole = (PlatformRestRole) o;

        if (restRoleId != null ? !restRoleId.equals(restRole.restRoleId) : restRole.restRoleId != null) return false;
        if (restId != null ? !restId.equals(restRole.restId) : restRole.restId != null) return false;
        return roleId != null ? roleId.equals(restRole.roleId) : restRole.roleId == null;
    }

    @Override
    public int hashCode() {
        int result = restRoleId != null ? restRoleId.hashCode() : 0;
        result = 31 * result + (restId != null ? restId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
