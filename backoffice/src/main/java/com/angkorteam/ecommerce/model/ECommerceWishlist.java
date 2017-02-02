package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceWishlist implements Serializable {

    private Long ecommerceWishlistId;
    private Long userId;
    private Long ecommerceProductId;

    public Long getECommerceWishlistId() {
        return ecommerceWishlistId;
    }

    public void setECommerceWishlistId(Long ecommerceWishlistId) {
        this.ecommerceWishlistId = ecommerceWishlistId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getECommerceProductId() {
        return ecommerceProductId;
    }

    public void setECommerceProductId(Long ecommerceProductId) {
        this.ecommerceProductId = ecommerceProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceWishlist that = (ECommerceWishlist) o;

        if (ecommerceWishlistId != null ? !ecommerceWishlistId.equals(that.ecommerceWishlistId) : that.ecommerceWishlistId != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return ecommerceProductId != null ? ecommerceProductId.equals(that.ecommerceProductId) : that.ecommerceProductId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceWishlistId != null ? ecommerceWishlistId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (ecommerceProductId != null ? ecommerceProductId.hashCode() : 0);
        return result;
    }
}
