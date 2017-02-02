package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class MetadataVO implements Serializable {

    @Expose
    @SerializedName("links")
    private LinksVO links;

    @Expose
    @SerializedName("filters")
    private List<Object> filters;

    @Expose
    @SerializedName("sorting")
    private String sorting;

    @Expose
    @SerializedName("records_count")
    private Integer recordsCount;

    public LinksVO getLinks() {
        return links;
    }

    public void setLinks(LinksVO links) {
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

    public Integer getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Integer recordsCount) {
        this.recordsCount = recordsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetadataVO that = (MetadataVO) o;

        if (links != null ? !links.equals(that.links) : that.links != null) return false;
        if (filters != null ? !filters.equals(that.filters) : that.filters != null) return false;
        if (sorting != null ? !sorting.equals(that.sorting) : that.sorting != null) return false;
        return recordsCount != null ? recordsCount.equals(that.recordsCount) : that.recordsCount == null;
    }

    @Override
    public int hashCode() {
        int result = links != null ? links.hashCode() : 0;
        result = 31 * result + (filters != null ? filters.hashCode() : 0);
        result = 31 * result + (sorting != null ? sorting.hashCode() : 0);
        result = 31 * result + (recordsCount != null ? recordsCount.hashCode() : 0);
        return result;
    }
}
