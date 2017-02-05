package com.angkorteam.platform.mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 3/2/17.
 */
public class Metadata implements Serializable {

    @Expose
    @SerializedName("links")
    private Links links;

    @Expose
    @SerializedName("filters")
    private List<Object> filters;

    @Expose
    @SerializedName("sorting")
    private String sorting;

    @Expose
    @SerializedName("records_count")
    private int recordsCount;

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<Object> getFilters() {
        return filters;
    }

    public void setFilters(List<Object> filters) {
        this.filters = filters;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    public int getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

}
