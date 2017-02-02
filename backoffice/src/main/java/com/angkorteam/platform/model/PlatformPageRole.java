package com.angkorteam.platform.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PlatformPageRole implements Serializable {

    private Long platformPageRoleId;
    private Long platformPageId;
    private Long platformRoleId;

    public Long getPlatformPageRoleId() {
        return platformPageRoleId;
    }

    public void setPlatformPageRoleId(Long platformPageRoleId) {
        this.platformPageRoleId = platformPageRoleId;
    }

    public Long getPlatformPageId() {
        return platformPageId;
    }

    public void setPlatformPageId(Long platformPageId) {
        this.platformPageId = platformPageId;
    }

    public Long getPlatformRoleId() {
        return platformRoleId;
    }

    public void setPlatformRoleId(Long platformRoleId) {
        this.platformRoleId = platformRoleId;
    }
}
