package com.angkorteam.platform.page.section;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 10/26/16.
 */
public class SectionBrowsePage extends MBaaSPage {

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("platform_section");
        provider.boardField("platform_section_id", "sectionId", Long.class);
        provider.boardField("title", "title", String.class);
        provider.boardField("`order`", "order", Integer.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "sectionId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("title"), "title"));
        columns.add(new TextFilterColumn(provider, ItemClass.Integer, Model.of("order"), "order"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        DataTable<Map<String, Object>, String> dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        filterForm.add(dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", SectionBrowsePage.class);
        layout.add(refreshLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget ajaxRequestTarget) {
        Long sectionId = (Long) object.get("sectionId");
        PageParameters parameters = new PageParameters();
        parameters.add("sectionId", sectionId);
        setResponsePage(SectionModifyPage.class, parameters);
    }

}
