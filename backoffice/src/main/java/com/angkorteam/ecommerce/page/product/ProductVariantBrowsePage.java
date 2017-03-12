package com.angkorteam.ecommerce.page.product;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
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
public class ProductVariantBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductVariantBrowsePage.class);

    private String ecommerceProductId;

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceProductId = getPageParameters().get("ecommerceProductId").toString("");

        JdbcProvider provider = new JdbcProvider("ecommerce_product_variant");
        provider.addJoin("LEFT JOIN ecommerce_color on ecommerce_product_variant.ecommerce_color_id = ecommerce_color.ecommerce_color_id");
        provider.addJoin("LEFT JOIN ecommerce_size on ecommerce_product_variant.ecommerce_size_id = ecommerce_size.ecommerce_size_id");
        provider.addWhere("ecommerce_product_variant.ecommerce_product_id = '" + this.ecommerceProductId + "'");

        provider.boardField("ecommerce_product_variant.ecommerce_product_variant_id", "ecommerceProductVariantId", Long.class);
        provider.boardField("ecommerce_product_variant.reference", "reference", String.class);
        provider.boardField("ecommerce_product_variant.quantity", "quantity", Integer.class);
        provider.boardField("CONCAT(ecommerce_color.value, ' > ', ecommerce_color.reference)", "color", String.class);
        provider.boardField("CONCAT(ecommerce_size.value, ' > ', ecommerce_size.reference)", "size", String.class);

        provider.selectField("ecommerceProductVariantId", Long.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Integer, Model.of("ID"), "ecommerceProductVariantId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("reference"), "reference"));
        columns.add(new TextFilterColumn(provider, ItemClass.Integer, Model.of("quantity"), "quantity"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("color"), "color"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("size"), "size"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> closeLink = new BookmarkablePageLink<>("closeLink", ProductBrowsePage.class);
        layout.add(closeLink);

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceProductId", this.ecommerceProductId);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", ProductVariantBrowsePage.class, parameters);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", ProductVariantCreatePage.class, parameters);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Gallery", Model.of("Gallery")));
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceProductVariantId = (Long) object.get("ecommerceProductVariantId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_product_variant where ecommerce_product_variant_id = ?", ecommerceProductVariantId);
            SelectQuery selectQuery = new SelectQuery("ecommerce_product_variant");
            selectQuery.addField("sum(quantity)");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
            Integer quantity = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            UpdateQuery updateQuery = new UpdateQuery("ecommerce_product");
            updateQuery.addValue("quantity = :quantity", quantity);
            updateQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
            getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
            target.add(this.dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceProductId", this.ecommerceProductId);
            parameters.add("ecommerceProductVariantId", ecommerceProductVariantId);
            setResponsePage(ProductVariantModifyPage.class, parameters);
        } else if ("Gallery".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceProductId", this.ecommerceProductId);
            parameters.add("ecommerceProductVariantId", ecommerceProductVariantId);
            setResponsePage(ProductVariantGalleryBrowsePage.class, parameters);
        }
    }

}