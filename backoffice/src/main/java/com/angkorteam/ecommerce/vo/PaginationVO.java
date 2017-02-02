package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PaginationVO<T> implements Serializable {

    @Expose
    @SerializedName("metadata")
    private MetadataVO metadata;

    @Expose
    @SerializedName("records")
    private List<T> records;

    public MetadataVO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataVO metadata) {
        this.metadata = metadata;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaginationVO<?> paginationVO = (PaginationVO<?>) o;

        if (metadata != null ? !metadata.equals(paginationVO.metadata) : paginationVO.metadata != null) return false;
        return records != null ? records.equals(paginationVO.records) : paginationVO.records == null;
    }

    @Override
    public int hashCode() {
        int result = metadata != null ? metadata.hashCode() : 0;
        result = 31 * result + (records != null ? records.hashCode() : 0);
        return result;
    }
}
