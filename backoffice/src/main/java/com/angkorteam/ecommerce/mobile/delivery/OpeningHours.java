package com.angkorteam.ecommerce.mobile.delivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class OpeningHours implements Serializable {

    @Expose
    @SerializedName("day")
    private String day;

    @Expose
    @SerializedName("opening")
    private String opening;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }
}
