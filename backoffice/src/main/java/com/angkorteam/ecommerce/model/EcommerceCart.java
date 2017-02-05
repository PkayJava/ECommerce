package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceCart implements Serializable {

    private Long ecommerceCartId;
    private Long platformUserId;

    public Long getEcommerceCartId() {
        return ecommerceCartId;
    }

    public void setEcommerceCartId(Long ecommerceCartId) {
        this.ecommerceCartId = ecommerceCartId;
    }

    public Long getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceCart that = (EcommerceCart) o;

        if (ecommerceCartId != null ? !ecommerceCartId.equals(that.ecommerceCartId) : that.ecommerceCartId != null)
            return false;
        return platformUserId != null ? platformUserId.equals(that.platformUserId) : that.platformUserId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceCartId != null ? ecommerceCartId.hashCode() : 0;
        result = 31 * result + (platformUserId != null ? platformUserId.hashCode() : 0);
        return result;
    }
}
