package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table;

import com.angkorteam.framework.extension.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;

/**
 * @author Socheat KHAUV
 */
public class NavigationToolbar extends org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar {

    /**
     *
     */
    private static final long serialVersionUID = 1057961184097803486L;

    public NavigationToolbar(final DataTable<?, ?> table) {
        super(table);
    }

    @Override
    protected PagingNavigator newPagingNavigator(String navigatorId, DataTable<?, ?> table) {
        return new PagingNavigator(navigatorId, table);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}