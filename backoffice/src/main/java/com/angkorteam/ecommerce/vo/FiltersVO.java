package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class FiltersVO implements Serializable {

    @Expose
    @SerializedName("filters")
    private List<FilterTypeVO> filters;

    public List<FilterTypeVO> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterTypeVO> filters) {
        this.filters = filters;
    }
}
