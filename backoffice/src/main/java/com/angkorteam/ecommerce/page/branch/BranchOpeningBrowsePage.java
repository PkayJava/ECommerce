package com.angkorteam.ecommerce.page.branch;

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
 * Created by socheatkhauv on 24/1/17.
 */
public class BranchOpeningBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchOpeningBrowsePage.class);

    private String ecommerceBranchId;

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);
        this.ecommerceBranchId = getPageParameters().get("ecommerceBranchId").toString("");

        JdbcProvider provider = new JdbcProvider("ecommerce_branch_opening");
        provider.addWhere("ecommerce_branch_id = '" + this.ecommerceBranchId + "'");

        provider.boardField("ecommerce_branch_opening_id", "ecommerceBranchOpeningId", Long.class);
        provider.boardField("day", "day", String.class);
        provider.boardField("opening", "opening", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceBranchOpeningId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("day"), "day"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("opening"), "opening"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> closeLink = new BookmarkablePageLink<>("closeLink", BranchBrowsePage.class);
        layout.add(closeLink);

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceBranchId", this.ecommerceBranchId);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", BranchOpeningBrowsePage.class, parameters);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", BranchOpeningCreatePage.class, parameters);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceBranchOpeningId = (Long) object.get("ecommerceBranchOpeningId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_branch_opening where ecommerce_branch_opening_id = ?", ecommerceBranchOpeningId);
            target.add(this.dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceBranchId", this.ecommerceBranchId);
            parameters.add("ecommerceBranchOpeningId", ecommerceBranchOpeningId);
            setResponsePage(BranchOpeningModifyPage.class, parameters);
        }
    }

}