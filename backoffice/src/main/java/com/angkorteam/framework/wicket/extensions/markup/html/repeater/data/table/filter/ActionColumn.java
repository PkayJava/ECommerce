package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.wicket.WicketBiFunction;
import com.angkorteam.framework.wicket.WicketTriConsumer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class ActionColumn extends AbstractColumn<Map<String, Object>, String> {

    protected WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick;

    protected WicketBiFunction<String, Map<String, Object>, List<ActionItem>> actions;

    protected IModel<String> separator;

    public ActionColumn(IModel<String> displayModel,
                        WicketBiFunction<String, Map<String, Object>, List<ActionItem>> actions,
                        WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        super(displayModel);
        this.actions = actions;
        this.itemClick = itemClick;
        this.separator = Model.of(" ");
    }

    public ActionColumn(IModel<String> displayModel,
                        IModel<String> separator,
                        WicketBiFunction<String, Map<String, Object>, List<ActionItem>> actions,
                        WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        super(displayModel);
        this.actions = actions;
        this.itemClick = itemClick;
        this.separator = separator;
    }

    @Override
    public void populateItem(Item<ICellPopulator<Map<String, Object>>> item, String componentId, IModel<Map<String, Object>> rowModel) {
        List<ActionItem> actionItems = this.actions.apply(getDisplayModel().getObject(), rowModel.getObject());
        if (actionItems == null || actionItems.isEmpty()) {
            Label label = new Label(componentId, "");
            item.add(label);
        } else {
            Collections.sort(actionItems);
            RepeatingView view = new RepeatingView(componentId);
            item.add(view);
            boolean first = true;
            for (ActionItem actionItem : actionItems) {
                if (!first) {
                    Label label = new Label(view.newChildId(), this.separator);
                    view.add(label);
                }
                view.add(new ItemTextLink(view.newChildId(), rowModel, actionItem.getLabel(), actionItem.getCss(), this.itemClick, actionItem.getLink()));
                first = false;
            }
        }
    }

}
