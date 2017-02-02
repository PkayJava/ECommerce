package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class CartProductItemVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("remote_id")
    private Long remoteId;

    @Expose
    @SerializedName("quantity")
    private Integer quantity;

    @Expose
    @SerializedName("total_price")
    private Double totalItemPrice;

    @Expose
    @SerializedName("total_item_price_formatted")
    private String totalItemPriceFormatted;

    @Expose
    @SerializedName("variant")
    private CartProductItemVariantVO variant;

    @Expose
    @SerializedName("expiration")
    private Integer expiration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(Double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getTotalItemPriceFormatted() {
        return totalItemPriceFormatted;
    }

    public void setTotalItemPriceFormatted(String totalItemPriceFormatted) {
        this.totalItemPriceFormatted = totalItemPriceFormatted;
    }

    public CartProductItemVariantVO getVariant() {
        return variant;
    }

    public void setVariant(CartProductItemVariantVO variant) {
        this.variant = variant;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartProductItemVO that = (CartProductItemVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (remoteId != null ? !remoteId.equals(that.remoteId) : that.remoteId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (totalItemPrice != null ? !totalItemPrice.equals(that.totalItemPrice) : that.totalItemPrice != null)
            return false;
        if (totalItemPriceFormatted != null ? !totalItemPriceFormatted.equals(that.totalItemPriceFormatted) : that.totalItemPriceFormatted != null)
            return false;
        if (variant != null ? !variant.equals(that.variant) : that.variant != null) return false;
        return expiration != null ? expiration.equals(that.expiration) : that.expiration == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (remoteId != null ? remoteId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (totalItemPrice != null ? totalItemPrice.hashCode() : 0);
        result = 31 * result + (totalItemPriceFormatted != null ? totalItemPriceFormatted.hashCode() : 0);
        result = 31 * result + (variant != null ? variant.hashCode() : 0);
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
        return result;
    }
}
