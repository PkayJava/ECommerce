package com.angkorteam.platform.page.menuitem;

import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.JdbcProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 10/24/16.
 */
public class MenuItemBrowsePage extends MBaaSPage {

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("platform_menu_item");

        provider.addJoin("LEFT JOIN platform_menu on platform_menu_item.platform_menu_id = platform_menu.platform_menu_id");
        provider.addJoin("LEFT JOIN platform_section on platform_menu_item.platform_section_id = platform_section.platform_section_id");
        provider.addJoin("LEFT JOIN platform_page on platform_menu_item.platform_page_id = platform_page.platform_page_id");

        provider.boardField("platform_menu_item.platform_menu_item_id", "menuItemId", Long.class);
        provider.boardField("platform_menu_item.title", "title", String.class);
        provider.boardField("platform_menu_item.icon", "icon", String.class);
        provider.boardField("platform_page.page_title", "page", String.class);
        provider.boardField("platform_menu.path", "menu", String.class);
        provider.boardField("platform_section.title", "section", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "menuItemId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("title"), "title"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("icon"), "icon"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("page"), "page"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("menu"), "menu"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("section"), "section"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        filterForm.add(dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", MenuItemBrowsePage.class);
        layout.add(refreshLink);
    }

    private Map<String, IModel<String>> actions() {
        Map<String, IModel<String>> actions = Maps.newHashMap();
        actions.put("Edit", Model.of("Edit"));
        return actions;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        if ("Edit".equals(link)) {
            Long menuItemId = (Long) object.get("menuItemId");
            PageParameters parameters = new PageParameters();
            parameters.add("menuItemId", menuItemId);
            setResponsePage(MenuItemModifyPage.class, parameters);
        }
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        return actionItems;
    }
}
