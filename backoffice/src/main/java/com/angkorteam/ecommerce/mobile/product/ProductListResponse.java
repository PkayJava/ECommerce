package com.angkorteam.ecommerce.mobile.product;

import com.angkorteam.platform.mobile.Metadata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 4/2/17.
 */
public class ProductListResponse implements Serializable {

    @Expose
    @SerializedName("metadata")
    private Metadata metadata;

    @Expose
    @SerializedName("records")
    private List<Product> records;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Product> getRecords() {
        return records;
    }

    public void setRecords(List<Product> records) {
        this.records = records;
    }
}
