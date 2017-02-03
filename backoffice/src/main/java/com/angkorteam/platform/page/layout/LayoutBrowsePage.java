package com.angkorteam.platform.page.layout;

import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.JdbcProvider;
import com.google.common.collect.Lists;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 10/26/16.
 */
public class LayoutBrowsePage extends MBaaSPage {

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("platform_layout");

        provider.boardField("platform_layout_id", "layoutId", Long.class);
        provider.boardField("name", "name", String.class);
        provider.boardField("java_class", "javaClass", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "layoutId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("javaClass"), "javaClass"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", LayoutBrowsePage.class);
        layout.add(refreshLink);
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        if ("Edit".equals(link)) {
            Long layoutId = (Long) object.get("layoutId");
            PageParameters parameters = new PageParameters();
            parameters.add("layoutId", layoutId);
            setResponsePage(LayoutModifyPage.class, parameters);
        }
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        return actionItems;
    }

}
