package com.angkorteam.ecommerce.page.category;

import com.angkorteam.ecommerce.model.EcommerceCategory;
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
public class CategoryBrowsePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryBrowsePage.class);

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_category");
        provider.boardField("ecommerce_category_id", "ecommerceCategoryId", Long.class);
        provider.boardField("name", "name", String.class);
        provider.boardField("path", "path", String.class);

        provider.selectField("ecommerceCategoryId", String.class);

        provider.setSort("path", SortOrder.ASCENDING);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceCategoryId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("path"), "path"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", CategoryBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", CategoryCreatePage.class);
        layout.add(createLink);
    }


    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceCategoryId = (Long) object.get("ecommerceCategoryId");
        if ("Delete".equals(link)) {
            deleteChildCategory(ecommerceCategoryId);
            getJdbcTemplate().update("delete from ecommerce_category where ecommerce_category_id = ?", ecommerceCategoryId);
            target.add(dataTable);
        } else if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceCategoryId", ecommerceCategoryId);
            setResponsePage(CategoryModifyPage.class, parameters);
        }
    }

    private void deleteChildCategory(Long categoryId) {
        SelectQuery selectQuery = new SelectQuery("ecommerce_category");
        selectQuery.addWhere("parent_ecommerce_category_id = :parent_ecommerce_category_id", categoryId);
        List<EcommerceCategory> childrenRecords = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCategory.class);
        if (childrenRecords != null && !childrenRecords.isEmpty()) {
            for (EcommerceCategory childrenRecord : childrenRecords) {
                Long id = childrenRecord.getEcommerceCategoryId();
                deleteChildCategory(id);
                getJdbcTemplate().update("delete from ecommerce_category where ecommerce_category_id = ?", id);
            }
        }

    }

}