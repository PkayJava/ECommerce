package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceShop implements Serializable {

    private Long ecommerceShopId;
    private String name;
    private String description;
    private String url;
    private String logoFileId;
    private String googleUa;
    private String language;
    private String flagIconFileId;

    public Long getECommerceShopId() {
        return ecommerceShopId;
    }

    public void setECommerceShopId(Long ecommerceShopId) {
        this.ecommerceShopId = ecommerceShopId;
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

    public String getLogoFileId() {
        return logoFileId;
    }

    public void setLogoFileId(String logoFileId) {
        this.logoFileId = logoFileId;
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

    public String getFlagIconFileId() {
        return flagIconFileId;
    }

    public void setFlagIconFileId(String flagIconFileId) {
        this.flagIconFileId = flagIconFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECommerceShop that = (ECommerceShop) o;

        if (ecommerceShopId != null ? !ecommerceShopId.equals(that.ecommerceShopId) : that.ecommerceShopId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (logoFileId != null ? !logoFileId.equals(that.logoFileId) : that.logoFileId != null) return false;
        if (googleUa != null ? !googleUa.equals(that.googleUa) : that.googleUa != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        return flagIconFileId != null ? flagIconFileId.equals(that.flagIconFileId) : that.flagIconFileId == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceShopId != null ? ecommerceShopId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (logoFileId != null ? logoFileId.hashCode() : 0);
        result = 31 * result + (googleUa != null ? googleUa.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (flagIconFileId != null ? flagIconFileId.hashCode() : 0);
        return result;
    }
}
