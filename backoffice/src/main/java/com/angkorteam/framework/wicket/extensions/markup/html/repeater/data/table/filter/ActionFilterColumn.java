package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.wicket.WicketBiFunction;
import com.angkorteam.framework.wicket.WicketTriConsumer;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class ActionFilterColumn extends ActionColumn implements IFilteredColumn<Map<String, Object>, String> {

    protected IModel<String> filter;

    protected IModel<String> clear;

    public ActionFilterColumn(IModel<String> displayModel,
                              WicketBiFunction<String, Map<String, Object>, List<ActionItem>> actions,
                              WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        this(displayModel, Model.of("Filter"), Model.of("Clear"), actions, itemClick);
    }

    public ActionFilterColumn(IModel<String> displayModel,
                              IModel<String> filter,
                              IModel<String> clear,
                              WicketBiFunction<String, Map<String, Object>, List<ActionItem>> actions,
                              WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        super(displayModel, actions, itemClick);
        this.filter = filter;
        this.clear = clear;
    }

    public ActionFilterColumn(IModel<String> displayModel,
                              IModel<String> separator,
                              WicketBiFunction<String, Map<String, Object>, List<ActionItem>> actions,
                              WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        this(displayModel, separator, Model.of("Filter"), Model.of("Clear"), actions, itemClick);
    }

    public ActionFilterColumn(IModel<String> displayModel,
                              IModel<String> separator,
                              IModel<String> filter,
                              IModel<String> clear,
                              WicketBiFunction<String, Map<String, Object>, List<ActionItem>> actions,
                              WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        super(displayModel, separator, actions, itemClick);
        this.filter = filter;
        this.clear = clear;
    }

    @Override
    public Component getFilter(String componentId, FilterForm<?> form) {
        return new GoAndClearFilter(componentId, form, this.filter, this.clear);
    }

}
