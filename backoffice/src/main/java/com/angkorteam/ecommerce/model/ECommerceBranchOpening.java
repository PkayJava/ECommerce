package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceBranchOpening implements Serializable {

    private Long ecommerceBranchOpeningId;
    private Long ecommerceBranchId;
    private String day;
    private String opening;

    public Long getECommerceBranchOpeningId() {
        return ecommerceBranchOpeningId;
    }

    public void setECommerceBranchOpeningId(Long ecommerceBranchOpeningId) {
        this.ecommerceBranchOpeningId = ecommerceBranchOpeningId;
    }

    public Long getECommerceBranchId() {
        return ecommerceBranchId;
    }

    public void setECommerceBranchId(Long ecommerceBranchId) {
        this.ecommerceBranchId = ecommerceBranchId;
    }

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

        ECommerceBranchOpening that = (ECommerceBranchOpening) o;

        if (ecommerceBranchOpeningId != null ? !ecommerceBranchOpeningId.equals(that.ecommerceBranchOpeningId) : that.ecommerceBranchOpeningId != null)
            return false;
        if (ecommerceBranchId != null ? !ecommerceBranchId.equals(that.ecommerceBranchId) : that.ecommerceBranchId != null)
            return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        return opening != null ? opening.equals(that.opening) : that.opening == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceBranchOpeningId != null ? ecommerceBranchOpeningId.hashCode() : 0;
        result = 31 * result + (ecommerceBranchId != null ? ecommerceBranchId.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (opening != null ? opening.hashCode() : 0);
        return result;
    }
}
