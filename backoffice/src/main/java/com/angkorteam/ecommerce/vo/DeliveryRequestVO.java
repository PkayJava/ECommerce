package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class DeliveryRequestVO implements Serializable {

    @Expose
    @SerializedName("delivery")
    private DeliveryVO delivery;

    public DeliveryVO getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryVO delivery) {
        this.delivery = delivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryRequestVO that = (DeliveryRequestVO) o;

        return delivery != null ? delivery.equals(that.delivery) : that.delivery == null;
    }

    @Override
    public int hashCode() {
        return delivery != null ? delivery.hashCode() : 0;
    }
}
