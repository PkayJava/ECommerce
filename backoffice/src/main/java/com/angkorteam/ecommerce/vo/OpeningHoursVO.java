package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class OpeningHoursVO implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpeningHoursVO that = (OpeningHoursVO) o;

        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        return opening != null ? opening.equals(that.opening) : that.opening == null;
    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (opening != null ? opening.hashCode() : 0);
        return result;
    }
}
