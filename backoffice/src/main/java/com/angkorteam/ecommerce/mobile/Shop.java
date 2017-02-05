package com.angkorteam.ecommerce.mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Shop implements Serializable {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("logo")
    private String logo;

    @Expose
    @SerializedName("googleUa")
    private String googleUa;

    @Expose
    @SerializedName("language")
    private String language;

    @Expose
    @SerializedName("currency")
    private String currency;

    @SerializedName("flagIcon")
    private String flagIcon;

    public Shop() {
    }

    public Shop(String name, String googleUa) {
        this.name = name;
        this.googleUa = googleUa;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGoogleUa() {
        return googleUa;
    }

    public void setGoogleUa(String googleUa) {
        this.googleUa = googleUa;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFlagIcon() {
        return flagIcon;
    }

    public void setFlagIcon(String flagIcon) {
        this.flagIcon = flagIcon;
    }
}