package com.angkorteam.ecommerce.mobile.order;

import com.angkorteam.platform.mobile.Metadata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class OrderResponse implements Serializable {

    @Expose
    @SerializedName("metadata")
    private Metadata metadata;

    @Expose
    @SerializedName("records")
    private List<Order> records;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Order> getRecords() {
        return records;
    }

    public void setRecords(List<Order> records) {
        this.records = records;
    }

}
