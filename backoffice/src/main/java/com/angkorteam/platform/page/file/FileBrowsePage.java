package com.angkorteam.platform.page.file;

import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.Configuration;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformFile;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.JdbcProvider;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 3/11/16.
 */
public class FileBrowsePage extends MBaaSPage {

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("platform_file");
        provider.boardField("platform_file_id", "fileId", Long.class);
        provider.boardField("name", "name", String.class);
        provider.boardField("mime", "mime", String.class);
        provider.boardField("length", "length", Integer.class);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "fileId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("name"), "name"));
        columns.add(new TextFilterColumn(provider, ItemClass.Integer, Model.of("length"), "length"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("mime"), "mime"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 17);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", FileBrowsePage.class);
        layout.add(refreshLink);

        BookmarkablePageLink<Void> createLink = new BookmarkablePageLink<>("createLink", FileCreatePage.class);
        layout.add(createLink);
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);

        Long fileId = (Long) object.get("fileId");
        PlatformFile fileRecord = jdbcTemplate.queryForObject("select * from platform_file where platform_file_id = ?", PlatformFile.class, fileId);

        if ("Delete".equals(link)) {
            String repo = jdbcTemplate.queryForObject("select value from platform_setting where `key` = ?", String.class, Configuration.RESOURCE_REPO);
            String path = fileRecord.getPath();
            String name = fileRecord.getName();
            File file = new File(repo, path + "/" + name);
            FileUtils.deleteQuietly(file);
            jdbcTemplate.update("delete from platform_file where platform_file_id = ?", fileId);
            target.add(this.dataTable);
            return;
        }
        if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("fileId", fileId);
            setResponsePage(FileModifyPage.class, parameters);
            return;
        }
        if ("View".equals(link)) {
            StringBuffer address = new StringBuffer();
            address.append(getHttpAddress()).append("/api/resource").append(fileRecord.getPath()).append("/").append(fileRecord.getName());
            RedirectPage page = new RedirectPage(address);
            setResponsePage(page);
            return;
        }
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        actionItems.add(new ActionItem("View", Model.of("View")));
        actionItems.add(new ActionItem("Edit", Model.of("Edit")));
        actionItems.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.DANGER));
        return actionItems;
    }

}
