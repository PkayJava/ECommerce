package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceCart implements Serializable {

    private Long ecommerceCartId;
    private Long platformUserId;
    private Long ecommerceDiscountId;
    private Long ecommerceDiscountCouponId;

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

    public Long getEcommerceDiscountId() {
        return ecommerceDiscountId;
    }

    public void setEcommerceDiscountId(Long ecommerceDiscountId) {
        this.ecommerceDiscountId = ecommerceDiscountId;
    }

    public Long getEcommerceDiscountCouponId() {
        return ecommerceDiscountCouponId;
    }

    public void setEcommerceDiscountCouponId(Long ecommerceDiscountCouponId) {
        this.ecommerceDiscountCouponId = ecommerceDiscountCouponId;
    }

}
