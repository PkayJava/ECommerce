package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.sort;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by socheat on 4/16/15.
 */
public class OrderByBorder<S> extends org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder<S> {

    public OrderByBorder(String id, S property, ISortStateLocator<S> stateLocator) {
        super(id, property, stateLocator);
    }

    @Override
    protected OrderByLink<S> newOrderByLink(String id, S property, ISortStateLocator<S> stateLocator) {
        OrderByLink<S> link = super.newOrderByLink(id, property, stateLocator);
        Label glyphicon = new Label("glyphicon");
        link.add(glyphicon);

        ISortState<S> sortState = stateLocator.getSortState();
        SortOrder dir = sortState.getPropertySortOrder(property);

        String cssClass;
        if (dir == SortOrder.ASCENDING) {
            glyphicon.add(AttributeModifier.replace("class", "glyphicon glyphicon-sort-by-alphabet"));
        } else if (dir == SortOrder.DESCENDING) {
            glyphicon.add(AttributeModifier.replace("class", "glyphicon glyphicon-sort-by-alphabet-alt"));
        } else {
            glyphicon.setVisible(false);
        }

        return link;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}
