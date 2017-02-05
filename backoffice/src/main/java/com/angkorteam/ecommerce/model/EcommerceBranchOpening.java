package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceBranchOpening implements Serializable {

    private Long ecommerceBranchOpeningId;
    private Long ecommerceBranchId;
    private String day;
    private String opening;

    public Long getEcommerceBranchOpeningId() {
        return ecommerceBranchOpeningId;
    }

    public void setEcommerceBranchOpeningId(Long ecommerceBranchOpeningId) {
        this.ecommerceBranchOpeningId = ecommerceBranchOpeningId;
    }

    public Long getEcommerceBranchId() {
        return ecommerceBranchId;
    }

    public void setEcommerceBranchId(Long ecommerceBranchId) {
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

        EcommerceBranchOpening that = (EcommerceBranchOpening) o;

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
