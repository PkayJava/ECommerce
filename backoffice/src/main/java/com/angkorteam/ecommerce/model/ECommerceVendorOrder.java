package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceVendorOrder implements Serializable {

    private Long ecommerceVendorOrderId;
    private Long ecommerceOrderId;
    private Date dateCreated;
    private String orderStatus;
    private String vendorStatus;
    private Long userId;
    private Double total;

    public Long getECommerceVendorOrderId() {
        return ecommerceVendorOrderId;
    }

    public void setECommerceVendorOrderId(Long ecommerceVendorOrderId) {
        this.ecommerceVendorOrderId = ecommerceVendorOrderId;
    }

    public Long getECommerceOrderId() {
        return ecommerceOrderId;
    }

    public void setECommerceOrderId(Long ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(String vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceVendorOrder that = (ECommerceVendorOrder) o;

        if (ecommerceVendorOrderId != null ? !ecommerceVendorOrderId.equals(that.ecommerceVendorOrderId) : that.ecommerceVendorOrderId != null)
            return false;
        if (ecommerceOrderId != null ? !ecommerceOrderId.equals(that.ecommerceOrderId) : that.ecommerceOrderId != null)
            return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        if (orderStatus != null ? !orderStatus.equals(that.orderStatus) : that.orderStatus != null) return false;
        if (vendorStatus != null ? !vendorStatus.equals(that.vendorStatus) : that.vendorStatus != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return total != null ? total.equals(that.total) : that.total == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceVendorOrderId != null ? ecommerceVendorOrderId.hashCode() : 0;
        result = 31 * result + (ecommerceOrderId != null ? ecommerceOrderId.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (vendorStatus != null ? vendorStatus.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }
}
