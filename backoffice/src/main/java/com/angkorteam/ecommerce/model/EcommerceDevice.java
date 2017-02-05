package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceDevice implements Serializable {

    private Long ecommerceDeviceId;
    private Long platformUserId;
    private String deviceToken;
    private String platform;

    public Long getEcommerceDeviceId() {
        return ecommerceDeviceId;
    }

    public void setEcommerceDeviceId(Long ecommerceDeviceId) {
        this.ecommerceDeviceId = ecommerceDeviceId;
    }

    public Long getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceDevice that = (EcommerceDevice) o;

        if (ecommerceDeviceId != null ? !ecommerceDeviceId.equals(that.ecommerceDeviceId) : that.ecommerceDeviceId != null)
            return false;
        if (platformUserId != null ? !platformUserId.equals(that.platformUserId) : that.platformUserId != null) return false;
        if (deviceToken != null ? !deviceToken.equals(that.deviceToken) : that.deviceToken != null) return false;
        return platform != null ? platform.equals(that.platform) : that.platform == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceDeviceId != null ? ecommerceDeviceId.hashCode() : 0;
        result = 31 * result + (platformUserId != null ? platformUserId.hashCode() : 0);
        result = 31 * result + (deviceToken != null ? deviceToken.hashCode() : 0);
        result = 31 * result + (platform != null ? platform.hashCode() : 0);
        return result;
    }
}
