package com.angkorteam.ecommerce.page.product;

import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.jdbc.SelectQuery;
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
 * Created by socheatkhauv on 25/1/17.
 */
public class ProductBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_product");
        provider.addJoin("LEFT JOIN ecommerce_category ON ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        provider.addJoin("LEFT JOIN ecommerce_brand on ecommerce_product.ecommerce_brand_id = ecommerce_brand.ecommerce_brand_id");
        if (getSession().getRoles().hasRole("administrator")) {
            provider.addJoin("LEFT JOIN user on ecommerce_product.user_id = user.user_id");
        }

        provider.addJoin("LEFT JOIN ecommerce_product_variant on ecommerce_product.ecommerce_product_id = ecommerce_product_variant.ecommerce_product_id");
        provider.setGroupBy("ecommerce_product.ecommerce_product_id");

        if (getSession().getRoles().hasRole("administrator")) {
            provider.boardField("IF(max(user.full_name) is null or max(user.full_name) = '', max(user.login), max(user.full_name))", "fullName", String.class);
            provider.boardField("max(ecommerce_product.shipping_price)", "shipping", Double.class);
        }
        provider.boardField("count(ecommerce_product_variant.ecommerce_product_variant_id)", "variant", Integer.class);
        provider.boardField("max(ecommerce_product.ecommerce_product_id)", "ecommerceProductId", Long.class);
        provider.boardField("max(ecommerce_product.name)", "name", String.class);
        provider.boardField("max(ecommerce_product.price)", "price", Double.class);
        provider.boardField("max(ecommerce_product.ready)", "ready", Boolean.class);
        provider.boardField("max(ecommerce_product.reference)", "reference", String.class);
        provider.boardField("max(ecommerce_product.quantity)", "quantity", Integer.class);
        provider.boardField("max(ecommerce_brand.name)", "brand", String.class);
        provider.boardField("max(ecommerce_category.name)", "category", String.class);

        provider.setSort("ready", SortOrder.ASCENDING);

        if (!getSession().getRoles().hasRole("administrator")) {
            provider.addWhere("ecommerce_product.user_id = '" + getSession().getUserId() + "'");
        }

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceProductId"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("ready"), "ready"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("price"), "price"));
        if (getSession().getRoles().hasRole("administrator")) {
            columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("shipping"), "shipping"));
        }
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("brand"), "brand"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("reference"), "reference"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("quantity"), "quantity"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("category"), "category"));
        if (getSession().getRoles().hasRole("administrator")) {
            columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("fullName"), "fullName"));
        }
        columns.add(new TextFilterColumn(provider, ItemClass.Integer, Model.of("variant"), "variant"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", ProductBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", ProductCreatePage.class);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Variant", Model.of("Variant")));
        actionItems.add(new ActionItem("Gallery", Model.of("Gallery")));
        if (getSession().getRoles().hasRole("administrator")) {
            Long ecommerceProductId = (Long) object.get("ecommerceProductId");
            SelectQuery selectQuery = new SelectQuery("ecommerce_product_variant");
            selectQuery.addField("count(*)");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", ecommerceProductId);
            int count = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            if (count > 0) {
                actionItems.add(new ActionItem("Review", Model.of("Review"), ItemCss.WARNING));
            }
        }
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceProductId = (Long) object.get("ecommerceProductId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_product where ecommerce_product_id = ?", ecommerceProductId);
            getJdbcTemplate().update("delete from ecommerce_product_variant where ecommerce_product_id = ?", ecommerceProductId);
            getJdbcTemplate().update("delete from ecommerce_product_related where ecommerce_product_id = ?", ecommerceProductId);
            getJdbcTemplate().update("delete from ecommerce_product_related where related_ecommerce_product_id = ?", ecommerceProductId);
            getJdbcTemplate().update("delete from ecommerce_product_image where ecommerce_product_id = ?", ecommerceProductId);
            getJdbcTemplate().update("delete from ecommerce_product_variant_image where ecommerce_product_id = ?", ecommerceProductId);
            target.add(this.dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceProductId", ecommerceProductId);
            setResponsePage(ProductModifyPage.class, parameters);
        } else if ("Gallery".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceProductId", ecommerceProductId);
            setResponsePage(ProductGalleryBrowsePage.class, parameters);
        } else if ("Variant".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceProductId", ecommerceProductId);
            setResponsePage(ProductVariantBrowsePage.class, parameters);
        } else if ("Review".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceProductId", ecommerceProductId);
            setResponsePage(ProductReviewPage.class, parameters);
        }
    }
}