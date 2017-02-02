package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class ECommerceBranchTransport implements Serializable {

    private Long ecommerceBranchTransportId;
    private Long ecommerceBranchId;
    private Long iconFileId;
    private String text;

    public Long getECommerceBranchTransportId() {
        return ecommerceBranchTransportId;
    }

    public void setECommerceBranchTransportId(Long ecommerceBranchTransportId) {
        this.ecommerceBranchTransportId = ecommerceBranchTransportId;
    }

    public Long getECommerceBranchId() {
        return ecommerceBranchId;
    }

    public void setECommerceBranchId(Long ecommerceBranchId) {
        this.ecommerceBranchId = ecommerceBranchId;
    }

    public Long getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(Long iconFileId) {
        this.iconFileId = iconFileId;
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

        ECommerceBranchTransport that = (ECommerceBranchTransport) o;

        if (ecommerceBranchTransportId != null ? !ecommerceBranchTransportId.equals(that.ecommerceBranchTransportId) : that.ecommerceBranchTransportId != null)
            return false;
        if (ecommerceBranchId != null ? !ecommerceBranchId.equals(that.ecommerceBranchId) : that.ecommerceBranchId != null)
            return false;
        if (iconFileId != null ? !iconFileId.equals(that.iconFileId) : that.iconFileId != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceBranchTransportId != null ? ecommerceBranchTransportId.hashCode() : 0;
        result = 31 * result + (ecommerceBranchId != null ? ecommerceBranchId.hashCode() : 0);
        result = 31 * result + (iconFileId != null ? iconFileId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
