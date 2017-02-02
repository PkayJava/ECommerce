package com.angkorteam.framework.extension.wicket.extensions.markup.html.tabs;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by socheat on 7/4/16.
 */
public class TabbedPanel<T extends ITab> extends AjaxTabbedPanel<T> {

    public TabbedPanel(String id, List<T> tabs) {
        super(id, tabs);
    }

    public TabbedPanel(String id, List<T> tabs, IModel<Integer> model) {
        super(id, tabs, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        add(AttributeModifier.replace("class", "nav-tabs-custom"));
    }

    @Override
    protected String getSelectedTabCssClass() {
        return "active";
    }

    @Override
    protected WebMarkupContainer newTabsContainer(String id) {
        WebMarkupContainer container = super.newTabsContainer(id);
        container.setRenderBodyOnly(true);
        return container;
    }
}
