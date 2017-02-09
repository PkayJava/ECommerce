package com.angkorteam.ecommerce.page.order;

import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.JdbcProvider;
import com.google.common.collect.Lists;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
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
 * Created by socheatkhauv on 26/1/17.
 */
public class OrderBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderBrowsePage.class);

    DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_order");
        provider.boardField("ecommerce_order_id", "ecommerceOrderId", Long.class);
        provider.boardField("platform_user_id", "userId", String.class);
        provider.boardField("name", "name", String.class);
        provider.boardField("email", "email", String.class);
        provider.boardField("shipping_name", "shippingName", String.class);
        provider.boardField("payment_name", "paymentName", String.class);
        provider.boardField("order_status", "orderStatus", String.class);
        provider.boardField("buyer_status", "buyerStatus", String.class);
        provider.boardField("grand_total_amount", "total", Double.class);
        provider.boardField("date_created", "dateCreated", Calendar.DateTime);

        provider.selectField("ecommerceOrderId", Long.class);
        provider.selectField("userId", Long.class);

        provider.setSort("dateCreated", SortOrder.DESCENDING);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Integer, Model.of("ID"), "ecommerceOrderId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("email"), "email"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("shipping"), "shippingName"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("payment"), "paymentName"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("total"), "total"));
        columns.add(new TextFilterColumn(provider, ItemClass.DateTime, Model.of("order date"), "dateCreated"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("order status"), "orderStatus"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("buyer status"), "buyerStatus"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        filterForm.add(dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", OrderBrowsePage.class);
        layout.add(refreshLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Review", Model.of("Review"), ItemCss.WARNING));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        if ("Review".equals(link)) {
            Long ecommerceOrderId = (Long) object.get("ecommerceOrderId");
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceOrderId", ecommerceOrderId);
            setResponsePage(OrderReviewPage.class, parameters);
        }
    }

}