package com.angkorteam.ecommerce.page.color;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 22/1/17.
 */
public class ColorBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_color");
        provider.boardField("ecommerce_color_id", "ecommerceColorId", Long.class);
        provider.boardField("reference", "reference", String.class);
        provider.boardField("code", "code", String.class);
        provider.boardField("value", "value", String.class);
        provider.boardField("enabled", "enabled", Boolean.class);

        provider.selectField("ecommerceColorId", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceColorId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("reference"), "reference"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("code"), "code"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("enabled"), "enabled"));

        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", ColorBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", ColorCreatePage.class);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
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
        Long ecommerceColorId = (Long) object.get("ecommerceColorId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_color where ecommerce_color_id = ?", ecommerceColorId);
            target.add(this.dataTable);
        } else if ("Enable".equals(link)) {
            getJdbcTemplate().update("update ecommerce_color set enabled = true where ecommerce_color_id = ?", ecommerceColorId);
            target.add(this.dataTable);
        } else if ("Disable".equals(link)) {
            getJdbcTemplate().update("update ecommerce_color set enabled = false where ecommerce_color_id = ?", ecommerceColorId);
            target.add(this.dataTable);
        }
    }

}