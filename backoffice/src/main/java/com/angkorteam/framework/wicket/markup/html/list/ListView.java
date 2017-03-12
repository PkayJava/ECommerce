package com.angkorteam.framework.wicket.markup.html.list;

import com.angkorteam.framework.wicket.WicketBiConsumer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by socheat on 10/14/15.
 */
public class ListView<T> extends org.apache.wicket.markup.html.list.ListView<T> {

    private WicketBiConsumer<ListView, ListItem> populateItem;

    public ListView(String id) {
        super(id);
    }

    public ListView(String id, IModel<? extends List<T>> model) {
        super(id, model);
    }

    public ListView(String id, List<T> list) {
        super(id, list);
    }

    @Override
    protected final void populateItem(ListItem<T> item) {
        if (this.populateItem != null) {
            this.populateItem.accept(this, item);
        } else {
            throw new WicketRuntimeException("You need to setPopulateItem");
        }
    }

    public void setPopulateItem(WicketBiConsumer<ListView, ListItem> populateItem) {
        this.populateItem = populateItem;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}
