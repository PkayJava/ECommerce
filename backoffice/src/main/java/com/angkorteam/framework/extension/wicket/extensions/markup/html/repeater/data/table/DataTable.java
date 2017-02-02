package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.util.List;

/**
 * @author Socheat KHAUV
 */
public class DataTable<T, S> extends org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable<T, S> {

    /**
     *
     */
    private static final long serialVersionUID = -1703445429005849512L;

    public DataTable(String id, List<? extends IColumn<T, S>> columns, IDataProvider<T> dataProvider, long rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        this.add(AttributeModifier.replace("class", "table table-hover table-striped table-condensed"));
    }
}