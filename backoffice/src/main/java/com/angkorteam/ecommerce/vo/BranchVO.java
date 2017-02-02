package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class BranchVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("coordinates")
    private CoordinatesVO coordinates;

    @Expose
    @SerializedName("opening_hours")
    private List<OpeningHoursVO> openingHours;

    @Expose
    @SerializedName("note")
    private String note;

    @Expose
    @SerializedName("transports")
    private List<TransportVO> transports;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CoordinatesVO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesVO coordinates) {
        this.coordinates = coordinates;
    }

    public List<OpeningHoursVO> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHoursVO> openingHours) {
        this.openingHours = openingHours;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<TransportVO> getTransports() {
        return transports;
    }

    public void setTransports(List<TransportVO> transports) {
        this.transports = transports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchVO branchVO = (BranchVO) o;

        if (id != null ? !id.equals(branchVO.id) : branchVO.id != null) return false;
        if (name != null ? !name.equals(branchVO.name) : branchVO.name != null) return false;
        if (address != null ? !address.equals(branchVO.address) : branchVO.address != null) return false;
        if (coordinates != null ? !coordinates.equals(branchVO.coordinates) : branchVO.coordinates != null)
            return false;
        if (openingHours != null ? !openingHours.equals(branchVO.openingHours) : branchVO.openingHours != null)
            return false;
        if (note != null ? !note.equals(branchVO.note) : branchVO.note != null) return false;
        return transports != null ? transports.equals(branchVO.transports) : branchVO.transports == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        result = 31 * result + (openingHours != null ? openingHours.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (transports != null ? transports.hashCode() : 0);
        return result;
    }
}
