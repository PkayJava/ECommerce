package com.angkorteam.ecommerce.page.discount;

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
public class DiscountBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_discount");
        provider.boardField("ecommerce_discount_id", "ecommerceDiscountId", Long.class);
        provider.boardField("name", "name", String.class);
        provider.boardField("type", "type", String.class);
        provider.boardField("value", "value", Double.class);
        provider.boardField("min_cart_amount", "minCartAmount", Double.class);

        provider.selectField("ecommerceDiscountId", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceDiscountId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("type"), "type"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("value"), "value"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("min cart amount"), "minCartAmount"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", DiscountBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", DiscountCreatePage.class);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceDiscountId = (Long) object.get("ecommerceDiscountId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_discount where ecommerce_discount_id = ?", ecommerceDiscountId);
            target.add(this.dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceDiscountId", ecommerceDiscountId);
            setResponsePage(DiscountModifyPage.class, parameters);
        }
    }

}