package com.angkorteam.ecommerce.page.product;

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
public class ProductVariantGalleryBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductVariantGalleryBrowsePage.class);

    private String ecommerceProductId;

    private String ecommerceProductVariantId;

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceProductId = getPageParameters().get("ecommerceProductId").toString("");
        this.ecommerceProductVariantId = getPageParameters().get("ecommerceProductVariantId").toString("");

        JdbcProvider provider = new JdbcProvider("ecommerce_product_variant_image");
        provider.addWhere("ecommerce_product_variant_id = '" + this.ecommerceProductVariantId + "'");
        provider.boardField("ecommerce_product_variant_image_id", "ecommerceProductVariantImageId", Long.class);
        provider.boardField("name", "name", String.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceProductVariantImageId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> closeLink = new BookmarkablePageLink<>("closeLink", ProductBrowsePage.class);
        layout.add(closeLink);

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceProductId", this.ecommerceProductId);
        parameters.add("ecommerceProductVariantId", this.ecommerceProductVariantId);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", ProductVariantGalleryBrowsePage.class, parameters);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", ProductVariantGalleryCreatePage.class, parameters);
        layout.add(createLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        if ("Delete".equals(link)) {
            Long ecommerceProductVariantImageId = (Long) object.get("ecommerceProductVariantImageId");
            getJdbcTemplate().update("delete from ecommerce_product_variant_image where ecommerce_product_variant_image_id = ?", ecommerceProductVariantImageId);
            target.add(this.dataTable);
        }
    }

}