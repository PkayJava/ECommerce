package com.angkorteam.framework.wicket.markup.html.form.select2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by socheat on 5/25/16.
 */
public class Select2Response implements Serializable {

    @Expose
    @SerializedName("page")
    private int page = 1;

    @Expose
    @SerializedName("more")
    private boolean more = true;

    @Expose
    @SerializedName("items")
    private List<Option> items = new ArrayList<>();

    public boolean hasMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public List<Option> getItems() {
        return items;
    }

    public void setItems(List<Option> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
