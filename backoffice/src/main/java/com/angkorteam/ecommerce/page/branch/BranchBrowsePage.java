package com.angkorteam.ecommerce.page.branch;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 24/1/17.
 */
public class BranchBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_branch");
        provider.boardField("ecommerce_branch_id", "ecommerceBranchId", Long.class);
        provider.boardField("name", "name", String.class);
        provider.boardField("longitude", "longitude", Double.class);
        provider.boardField("latitude", "latitude", Double.class);
        provider.boardField("enabled", "enabled", Boolean.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceBranchId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("longitude"), "longitude"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("latitude"), "latitude"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("enabled"), "enabled"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", BranchBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", BranchCreatePage.class);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Opening", Model.of("Opening")));
        actionItems.add(new ActionItem("Transport", Model.of("Transport")));
        Boolean enabled = (Boolean) object.get("enabled");
        if (enabled != null && enabled) {
            actionItems.add(new ActionItem("Disable", Model.of("Disable"), ItemCss.DANGER));
        } else {
            actionItems.add(new ActionItem("Enable", Model.of("Enable"), ItemCss.DANGER));
        }
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceBranchId = (Long) object.get("ecommerceBranchId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_branch where ecommerce_branch_id = ?", ecommerceBranchId);
            getJdbcTemplate().update("delete from ecommerce_branch_opening where ecommerce_branch_id = ?", ecommerceBranchId);
            getJdbcTemplate().update("delete from ecommerce_branch_transport where ecommerce_branch_id = ?", ecommerceBranchId);
            target.add(this.dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceBranchId", ecommerceBranchId);
            setResponsePage(BranchModifyPage.class, parameters);
        } else if ("Opening".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceBranchId", ecommerceBranchId);
            setResponsePage(BranchOpeningBrowsePage.class, parameters);
        } else if ("Transport".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceBranchId", ecommerceBranchId);
            setResponsePage(BranchTransportBrowsePage.class, parameters);
        } else if ("Enable".equals(link)) {
            getJdbcTemplate().update("update ecommerce_branch set enabled = true where ecommerce_branch_id = ?", ecommerceBranchId);
            target.add(this.dataTable);
        } else if ("Disable".equals(link)) {
            getJdbcTemplate().update("update ecommerce_branch set enabled = false where ecommerce_branch_id = ?", ecommerceBranchId);
            target.add(this.dataTable);
        }
    }

}