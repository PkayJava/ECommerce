package com.angkorteam.ecommerce.mobile.delivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class Branch implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("coordinates")
    private Coordinates coordinates;

    @Expose
    @SerializedName("opening_hours")
    private List<OpeningHours> openingHoursList;

    @Expose
    @SerializedName("note")
    private String note;

    @Expose
    @SerializedName("transports")
    private List<Transport> transports;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<OpeningHours> getOpeningHoursList() {
        return openingHoursList;
    }

    public void setOpeningHoursList(List<OpeningHours> openingHoursList) {
        this.openingHoursList = openingHoursList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Transport> getTransports() {
        return transports;
    }

    public void setTransports(List<Transport> transports) {
        this.transports = transports;
    }
}
