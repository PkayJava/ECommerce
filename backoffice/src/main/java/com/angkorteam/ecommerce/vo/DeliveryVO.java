package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class DeliveryVO implements Serializable {

    @Expose
    @SerializedName("personal_pickup")
    private List<ShippingVO> personalPickup;

    @Expose
    @SerializedName("shipping")
    private List<ShippingVO> shipping;

    public List<ShippingVO> getPersonalPickup() {
        return personalPickup;
    }

    public void setPersonalPickup(List<ShippingVO> personalPickup) {
        this.personalPickup = personalPickup;
    }

    public List<ShippingVO> getShipping() {
        return shipping;
    }

    public void setShipping(List<ShippingVO> shipping) {
        this.shipping = shipping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryVO that = (DeliveryVO) o;

        if (personalPickup != null ? !personalPickup.equals(that.personalPickup) : that.personalPickup != null)
            return false;
        return shipping != null ? shipping.equals(that.shipping) : that.shipping == null;
    }

    @Override
    public int hashCode() {
        int result = personalPickup != null ? personalPickup.hashCode() : 0;
        result = 31 * result + (shipping != null ? shipping.hashCode() : 0);
        return result;
    }
}
