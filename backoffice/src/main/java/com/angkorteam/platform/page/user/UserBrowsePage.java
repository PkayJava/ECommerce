package com.angkorteam.platform.page.user;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 21/1/17.
 */
public class UserBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("platform_user");
        provider.addJoin("LEFT JOIN role ON platform_user.platform_role_id = platform_role.platform_role_id");
        provider.boardField("platform_user.platform_user_id", "userId", Long.class);
        provider.boardField("platform_user.full_name", "fullName", String.class);
        provider.boardField("platform_user.login", "login", String.class);
        provider.boardField("platform_role.name", "roleName", String.class);
        provider.boardField("platform_user.status", "status", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "userId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("fullName"), "fullName"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("login"), "login"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("roleName"), "roleName"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("status"), "status"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        filterForm.add(dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", UserBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", UserCreatePage.class);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Reset PWD", Model.of("Reset PWD")));
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }


    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long userId = (Long) object.get("userId");
        if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("userId", userId);
            setResponsePage(UserModifyPage.class, parameters);
        } else if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from platform_user where platform_user_id = ?", userId);
            target.add(this.dataTable);
        } else if ("Reset PWD".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("userId", userId);
            setResponsePage(UserPasswordPage.class, parameters);
        }
    }
}
