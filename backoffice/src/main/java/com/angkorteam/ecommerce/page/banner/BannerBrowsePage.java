package com.angkorteam.ecommerce.page.banner;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
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
 * Created by socheatkhauv on 22/1/17.
 */
public class BannerBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BannerBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_banner");
        provider.addJoin("LEFT JOIN ecommerce_product ON ecommerce_banner.ecommerce_product_id = ecommerce_product.ecommerce_product_id");
        provider.addJoin("LEFT JOIN ecommerce_category ON ecommerce_banner.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        provider.boardField("ecommerce_banner.ecommerce_banner_id", "ecommerceBannerId", Long.class);
        provider.boardField("ecommerce_banner.name", "name", String.class);
        provider.boardField("ecommerce_banner.type", "type", String.class);
        provider.boardField("ecommerce_product.name", "product", String.class);
        provider.boardField("ecommerce_category.name", "category", String.class);
        provider.boardField("ecommerce_banner.enabled", "enabled", Boolean.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceBannerId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("type"), "type"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("product"), "product"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("category"), "category"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("enabled"), "enabled"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", BannerBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", BannerCreatePage.class);
        layout.add(createLink);

    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
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
        Long ecommerceBannerId = (Long) object.get("ecommerceBannerId");
        if ("Delete".equals(link)) {
            getJdbcTemplate().update("delete from ecommerce_banner where ecommerce_banner_id = ?", ecommerceBannerId);
            target.add(this.dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceBannerId", ecommerceBannerId);
            setResponsePage(BannerModifyPage.class, parameters);
        } else if ("Enable".equals(link)) {
            getJdbcTemplate().update("update ecommerce_banner set enabled = true where ecommerce_banner_id = ?", ecommerceBannerId);
            target.add(this.dataTable);
        } else if ("Disable".equals(link)) {
            getJdbcTemplate().update("update ecommerce_banner set enabled = false where ecommerce_banner_id = ?", ecommerceBannerId);
            target.add(this.dataTable);
        }
    }

}