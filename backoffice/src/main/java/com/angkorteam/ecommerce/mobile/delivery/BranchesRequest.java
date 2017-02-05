package com.angkorteam.ecommerce.mobile.delivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class BranchesRequest implements Serializable {

    @Expose
    @SerializedName("records")
    private List<Branch> records;

    public List<Branch> getRecords() {
        return records;
    }

    public void setRecords(List<Branch> records) {
        this.records = records;
    }

}
