package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class LinksVO implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinksVO linksVO = (LinksVO) o;

        if (first != null ? !first.equals(linksVO.first) : linksVO.first != null) return false;
        if (last != null ? !last.equals(linksVO.last) : linksVO.last != null) return false;
        if (previous != null ? !previous.equals(linksVO.previous) : linksVO.previous != null) return false;
        if (next != null ? !next.equals(linksVO.next) : linksVO.next != null) return false;
        return self != null ? self.equals(linksVO.self) : linksVO.self == null;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (last != null ? last.hashCode() : 0);
        result = 31 * result + (previous != null ? previous.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + (self != null ? self.hashCode() : 0);
        return result;
    }
}
