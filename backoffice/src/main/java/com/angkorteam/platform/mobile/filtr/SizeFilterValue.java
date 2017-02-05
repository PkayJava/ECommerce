package com.angkorteam.platform.mobile.filtr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SizeFilterValue implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("value")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SizeFilterValue sizeValue = (SizeFilterValue) o;

        if (id != null ? !id.equals(sizeValue.id) : sizeValue.id != null) return false;
        return value != null ? value.equals(sizeValue.value) : sizeValue.value == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}