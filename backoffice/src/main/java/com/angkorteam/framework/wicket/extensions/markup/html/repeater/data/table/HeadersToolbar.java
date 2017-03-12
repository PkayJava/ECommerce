package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * @author Socheat KHAUV
 */
public class HeadersToolbar<S> extends org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar<S> {

    /**
     *
     */
    private static final long serialVersionUID = 4861965573039353068L;

    public <T> HeadersToolbar(DataTable<T, S> table, ISortStateLocator<S> stateLocator) {
        super(table, stateLocator);
    }

    @Override
    protected WebMarkupContainer newSortableHeader(String headerId, S property, ISortStateLocator<S> locator) {
        return new OrderByBorder<>(headerId, property, locator);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}