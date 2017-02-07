package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 6/2/17.
 */
public class EcommerceDiscountCoupon implements Serializable {

    private Long ecommerceDiscountCouponId;
    private Long ecommerceDiscountId;
    private String code;
    private Boolean used;
    private Date usedDate;
    private Long platformUserId;
    private Long ecommerceOrderId;

    public Long getEcommerceDiscountCouponId() {
        return ecommerceDiscountCouponId;
    }

    public void setEcommerceDiscountCouponId(Long ecommerceDiscountCouponId) {
        this.ecommerceDiscountCouponId = ecommerceDiscountCouponId;
    }

    public Long getEcommerceDiscountId() {
        return ecommerceDiscountId;
    }

    public void setEcommerceDiscountId(Long ecommerceDiscountId) {
        this.ecommerceDiscountId = ecommerceDiscountId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public Long getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
    }

    public Long getEcommerceOrderId() {
        return ecommerceOrderId;
    }

    public void setEcommerceOrderId(Long ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

}
