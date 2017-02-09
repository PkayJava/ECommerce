package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceBranch implements Serializable{

    private Long ecommerceBranchId;
    private String name;
    private String address;
    private String note;
    private Double longitude;
    private Double latitude;
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getEcommerceBranchId() {
        return ecommerceBranchId;
    }

    public void setEcommerceBranchId(Long ecommerceBranchId) {
        this.ecommerceBranchId = ecommerceBranchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceBranch that = (EcommerceBranch) o;

        if (ecommerceBranchId != null ? !ecommerceBranchId.equals(that.ecommerceBranchId) : that.ecommerceBranchId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        return latitude != null ? latitude.equals(that.latitude) : that.latitude == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceBranchId != null ? ecommerceBranchId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        return result;
    }
}
