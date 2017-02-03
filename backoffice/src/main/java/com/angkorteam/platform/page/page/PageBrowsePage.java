package com.angkorteam.platform.page.page;

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
 * Created by socheat on 10/27/16.
 */
public class PageBrowsePage extends MBaaSPage {

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("platform_page");
        provider.addJoin("LEFT JOIN platform_layout on platform_page.platform_layout_id = platform_layout.platform_layout_id");

        provider.boardField("platform_page.platform_page_id", "pageId", Long.class);
        provider.boardField("platform_page.page_title", "title", String.class);
        provider.boardField("platform_page.path", "path", String.class);
        provider.boardField("platform_layout.name", "layout", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Integer, Model.of("ID"), "pageId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("title"), "title"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("path"), "path"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("layout"), "layout"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        filterForm.add(dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", PageBrowsePage.class);
        layout.add(refreshLink);
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        if ("Edit".equals(link)) {
            Long pageId = (Long) object.get("pageId");
            PageParameters parameters = new PageParameters();
            parameters.add("pageId", pageId);
            setResponsePage(PageModifyPage.class, parameters);
        }
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        return actionItems;
    }
}
