package com.angkorteam.ecommerce.page.shipping;

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
 * Created by socheatkhauv on 25/1/17.
 */
public class ShippingBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_shipping");
        provider.addJoin("LEFT JOIN ecommerce_branch on ecommerce_shipping.ecommerce_branch_id = ecommerce_branch.ecommerce_branch_id");

        provider.boardField("ecommerce_shipping.ecommerce_shipping_id", "ecommerceShippingId", Long.class);
        provider.boardField("ecommerce_shipping.name", "name", String.class);
        provider.boardField("ecommerce_shipping.price", "price", Double.class);
        provider.boardField("ecommerce_shipping.min_cart_amount", "minCartAmount", Double.class);
        provider.boardField("ecommerce_shipping.type", "type", String.class);
        provider.boardField("ecommerce_branch.name", "branch", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceShippingId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("price"), "price"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("minCartAmount"), "minCartAmount"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("type"), "type"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("branch"), "branch"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", ShippingBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", ShippingCreatePage.class);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceShippingId = (Long) object.get("ecommerceShippingId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_shipping where ecommerce_shipping_id = ?", ecommerceShippingId);
            target.add(this.dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceShippingId", ecommerceShippingId);
            setResponsePage(ShippingModifyPage.class, parameters);
        }
    }

}