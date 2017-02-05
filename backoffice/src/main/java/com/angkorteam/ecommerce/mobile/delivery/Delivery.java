package com.angkorteam.ecommerce.mobile.delivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class Delivery implements Serializable {

    @Expose
    @SerializedName("personal_pickup")
    private List<Shipping> personalPickup;

    @Expose
    @SerializedName("shipping")
    private List<Shipping> shipping;

    public List<Shipping> getPersonalPickup() {
        return personalPickup;
    }

    public void setPersonalPickup(List<Shipping> personalPickup) {
        this.personalPickup = personalPickup;
    }

    public List<Shipping> getShipping() {
        return shipping;
    }

    public void setShipping(List<Shipping> shipping) {
        this.shipping = shipping;
    }

}
