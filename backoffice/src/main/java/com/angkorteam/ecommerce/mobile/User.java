package com.angkorteam.ecommerce.mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class User implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("fb_id")
    private String fbId;

    @Expose
    @SerializedName("access_token")
    private String accessToken;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("street")
    private String street;

    @Expose
    @SerializedName("city")
    private String city;

    @Expose
    @SerializedName("house_number")
    private String houseNumber;

    @Expose
    @SerializedName("zip")
    private String zip;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("gender")
    private String gender;

    @Expose
    @SerializedName("country")
    private String country;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
