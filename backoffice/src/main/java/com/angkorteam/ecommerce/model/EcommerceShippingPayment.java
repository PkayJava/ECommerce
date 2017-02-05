package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceShippingPayment implements Serializable {

    private Long ecommerceShippingPaymentId;
    private Long ecommerceShippingId;
    private Long ecommercePaymentId;

    public Long getEcommerceShippingPaymentId() {
        return ecommerceShippingPaymentId;
    }

    public void setEcommerceShippingPaymentId(Long ecommerceShippingPaymentId) {
        this.ecommerceShippingPaymentId = ecommerceShippingPaymentId;
    }

    public Long getEcommerceShippingId() {
        return ecommerceShippingId;
    }

    public void setEcommerceShippingId(Long ecommerceShippingId) {
        this.ecommerceShippingId = ecommerceShippingId;
    }

    public Long getEcommercePaymentId() {
        return ecommercePaymentId;
    }

    public void setEcommercePaymentId(Long ecommercePaymentId) {
        this.ecommercePaymentId = ecommercePaymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceShippingPayment that = (EcommerceShippingPayment) o;

        if (ecommerceShippingPaymentId != null ? !ecommerceShippingPaymentId.equals(that.ecommerceShippingPaymentId) : that.ecommerceShippingPaymentId != null)
            return false;
        if (ecommerceShippingId != null ? !ecommerceShippingId.equals(that.ecommerceShippingId) : that.ecommerceShippingId != null)
            return false;
        return ecommercePaymentId != null ? ecommercePaymentId.equals(that.ecommercePaymentId) : that.ecommercePaymentId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceShippingPaymentId != null ? ecommerceShippingPaymentId.hashCode() : 0;
        result = 31 * result + (ecommerceShippingId != null ? ecommerceShippingId.hashCode() : 0);
        result = 31 * result + (ecommercePaymentId != null ? ecommercePaymentId.hashCode() : 0);
        return result;
    }
}
