package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceBranchTransport implements Serializable {

    private Long ecommerceBranchTransportId;
    private Long ecommerceBranchId;
    private Long iconPlatformFileId;
    private String text;

    public Long getEcommerceBranchTransportId() {
        return ecommerceBranchTransportId;
    }

    public void setEcommerceBranchTransportId(Long ecommerceBranchTransportId) {
        this.ecommerceBranchTransportId = ecommerceBranchTransportId;
    }

    public Long getEcommerceBranchId() {
        return ecommerceBranchId;
    }

    public void setEcommerceBranchId(Long ecommerceBranchId) {
        this.ecommerceBranchId = ecommerceBranchId;
    }

    public Long getIconPlatformFileId() {
        return iconPlatformFileId;
    }

    public void setIconPlatformFileId(Long iconPlatformFileId) {
        this.iconPlatformFileId = iconPlatformFileId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceBranchTransport that = (EcommerceBranchTransport) o;

        if (ecommerceBranchTransportId != null ? !ecommerceBranchTransportId.equals(that.ecommerceBranchTransportId) : that.ecommerceBranchTransportId != null)
            return false;
        if (ecommerceBranchId != null ? !ecommerceBranchId.equals(that.ecommerceBranchId) : that.ecommerceBranchId != null)
            return false;
        if (iconPlatformFileId != null ? !iconPlatformFileId.equals(that.iconPlatformFileId) : that.iconPlatformFileId != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceBranchTransportId != null ? ecommerceBranchTransportId.hashCode() : 0;
        result = 31 * result + (ecommerceBranchId != null ? ecommerceBranchId.hashCode() : 0);
        result = 31 * result + (iconPlatformFileId != null ? iconPlatformFileId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
