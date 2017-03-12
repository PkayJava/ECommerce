package com.angkorteam.platform.page.menu;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
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
 * Created by socheat on 10/24/16.
 */
public class MenuBrowsePage extends MBaaSPage {

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("platform_menu menu");
        provider.addJoin("LEFT JOIN platform_menu parent on menu.parent_platform_menu_id = parent.platform_menu_id");
        provider.addJoin("LEFT JOIN platform_section on menu.platform_section_id = platform_section.platform_section_id");

        provider.boardField("menu.platform_menu_id", "menuId", Long.class);
        provider.boardField("menu.title", "title", String.class);
        provider.boardField("menu.icon", "icon", String.class);
        provider.boardField("parent.path", "parent", String.class);
        provider.boardField("platform_section.title", "section", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "menuId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("title"), "title"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("icon"), "icon"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("parent"), "parent"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("section"), "section"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        DataTable<Map<String, Object>, String> dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        filterForm.add(dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", MenuBrowsePage.class);
        layout.add(refreshLink);
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long menuId = (Long) object.get("menuId");
        PageParameters parameters = new PageParameters();
        parameters.add("menuId", menuId);
        setResponsePage(MenuModifyPage.class, parameters);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        return actionItems;
    }
}
