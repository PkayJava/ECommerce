package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformRestRole implements Serializable {

    private Long platformRestRoleId;
    private Long platformRestId;
    private Long platformRoleId;

    public Long getPlatformRestRoleId() {
        return platformRestRoleId;
    }

    public void setPlatformRestRoleId(Long platformRestRoleId) {
        this.platformRestRoleId = platformRestRoleId;
    }

    public Long getPlatformRestId() {
        return platformRestId;
    }

    public void setPlatformRestId(Long platformRestId) {
        this.platformRestId = platformRestId;
    }

    public Long getPlatformRoleId() {
        return platformRoleId;
    }

    public void setPlatformRoleId(Long platformRoleId) {
        this.platformRoleId = platformRoleId;
    }
}
