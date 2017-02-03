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
public class VendorOrderBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorOrderBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_vendor_order");
        provider.boardField("ecommerce_vendor_order_id", "ecommerceVendorOrderId", Long.class);
        provider.boardField("vendor_status", "vendorStatus", String.class);
        provider.boardField("order_status", "orderStatus", String.class);
        provider.boardField("user_id", "userId", Long.class);
        provider.boardField("total", "total", Double.class);
        provider.boardField("date_created", "dateCreated", Calendar.DateTime);

        provider.selectField("ecommerceVendorOrderId", String.class);
        provider.selectField("userId", String.class);

        provider.addWhere("user_id = '" + getSession().getPlatformUserId() + "'");

        provider.setSort("dateCreated", SortOrder.DESCENDING);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceVendorOrderId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("Vendor Status"), "vendorStatus"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("Order Status"), "orderStatus"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("Total"), "total"));
        columns.add(new TextFilterColumn(provider, ItemClass.DateTime, Model.of("Created Date"), "dateCreated"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        filterForm.add(dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", VendorOrderBrowsePage.class);
        layout.add(refreshLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Review", Model.of("Review"), ItemCss.WARNING));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        if (link == "Review") {
            Long ecommerceVendorOrderId = (Long) object.get("ecommerceVendorOrderId");
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceVendorOrderId", ecommerceVendorOrderId);
            setResponsePage(VendorOrderReviewPage.class, parameters);
        }
    }


}
