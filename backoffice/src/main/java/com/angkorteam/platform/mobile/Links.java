package com.angkorteam.platform.mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 3/2/17.
 */
public class Links implements Serializable {

    @Expose
    @SerializedName("first")
    private String first;

    @Expose
    @SerializedName("last")
    private String last;

    @Expose
    @SerializedName("prev")
    private String previous;

    @Expose
    @SerializedName("next")
    private String next;

    @Expose
    @SerializedName("self")
    private String self;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
