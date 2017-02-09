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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/2/17.
 */
public class DiscountCouponBrowsePage extends MBaaSPage {

    private String ecommerceDiscountId;

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceDiscountId = getPageParameters().get("ecommerceDiscountId").toString("");

        JdbcProvider provider = new JdbcProvider("ecommerce_discount_coupon");
        provider.addJoin("LEFT JOIN platform_user on ecommerce_discount_coupon.platform_user_id = platform_user.platform_user_id");
        provider.boardField("ecommerce_discount_coupon.ecommerce_discount_coupon_id", "ecommerceDiscountCouponId", Long.class);
        provider.boardField("ecommerce_discount_coupon.used", "used", Boolean.class);
        provider.boardField("ecommerce_discount_coupon.used_date", "usedDate", Calendar.Date);
        provider.boardField("ecommerce_discount_coupon.code", "code", String.class);
        provider.boardField("platform_user.login", "login", String.class);
        provider.boardField("ecommerce_discount_coupon.ecommerce_order_id", "ecommerceOrderId", Long.class);
        provider.addWhere("ecommerce_discount_coupon.ecommerce_discount_id = " + ecommerceDiscountId);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceDiscountCouponId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("code"), "code"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("login"), "login"));
        columns.add(new TextFilterColumn(provider, ItemClass.Date, Model.of("when"), "usedDate"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("used"), "used"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> closeLink = new BookmarkablePageLink<>("closeLink", DiscountBrowsePage.class);
        layout.add(closeLink);

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceDiscountId", ecommerceDiscountId);
        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", DiscountCouponBrowsePage.class, parameters);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> generateLink = new BookmarkablePageLink<>("generateLink", DiscountCouponGeneratePage.class, parameters);
        layout.add(generateLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        if (object.get("login") == null) {
            actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        }
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceDiscountCouponId = (Long) object.get("ecommerceDiscountCouponId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_discount_coupon where ecommerce_discount_coupon_id = ?", ecommerceDiscountCouponId);
            target.add(this.dataTable);
        }
    }

}
