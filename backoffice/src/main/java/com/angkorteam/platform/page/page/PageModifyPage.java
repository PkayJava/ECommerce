package com.angkorteam.platform.page.page;

import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.OptionDropDownChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformPage;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionMultipleChoiceProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by socheat on 10/27/16.
 */
public class PageModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageModifyPage.class);

    private String pageId;

    private String mountPath;
    private Label pathLabel;

    private String htmlTitle;
    private TextField<String> htmlTitleField;
    private TextFeedbackPanel htmlTitleFeedback;

    private String pageTitle;
    private TextField<String> pageTitleField;
    private TextFeedbackPanel pageTitleFeedback;

    private String pageDescription;
    private TextField<String> pageDescriptionField;
    private TextFeedbackPanel pageDescriptionFeedback;

    private List<Option> role;
    private Select2MultipleChoice<Option> roleField;
    private TextFeedbackPanel roleFeedback;

    private Option layout;
    private OptionDropDownChoice layoutField;
    private TextFeedbackPanel layoutFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        PageParameters parameters = getPageParameters();

        this.pageId = parameters.get("pageId").toString("");

        PlatformPage page = getJdbcTemplate().queryForObject("select * from platform_page where platform_page_id = ?", PlatformPage.class, this.pageId);
        this.pageTitle = page.getPageTitle();
        this.pageDescription = page.getPageDescription();
        this.htmlTitle = page.getHtmlTitle();
        this.mountPath = page.getPath();

        if (page.getPlatformLayoutId() != null) {
            SelectQuery selectQuery = new SelectQuery("platform_layout");
            selectQuery.addField("platform_layout_id id", "name text");
            selectQuery.addWhere("platform_layout_id = :platform_layout_id", page.getPlatformLayoutId());
            this.layout = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
        }

        SelectQuery selectQuery = new SelectQuery("platform_role");
        selectQuery.addField("platform_role.platform_role_id AS id");
        selectQuery.addField("platform_role.name AS text");
        selectQuery.addJoin(JoinType.InnerJoin, "platform_page_role", "platform_page_role.platform_role_id = platform_role.platform_role_id");
        selectQuery.addWhere("platform_page_role.platform_page_id = :page_id", this.pageId);
        this.role = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.roleField = new Select2MultipleChoice<>("roleField", new PropertyModel<>(this, "role"), new OptionMultipleChoiceProvider("platform_role", "platform_role_id", "name"));
        this.form.add(this.roleField);
        this.roleFeedback = new TextFeedbackPanel("roleFeedback", this.roleField);
        this.form.add(this.roleFeedback);

        this.pathLabel = new Label("pathLabel", new PropertyModel<>(this, "mountPath"));
        this.form.add(this.pathLabel);

        this.layoutField = new OptionDropDownChoice("layoutField", new PropertyModel<>(this, "layout"), getJdbcTemplate(), "platform_layout", "platform_layout_id", "name");
        this.layoutField.setRequired(true);
        this.form.add(this.layoutField);
        this.layoutFeedback = new TextFeedbackPanel("layoutFeedback", this.layoutField);
        this.form.add(this.layoutFeedback);

        this.pageTitleField = new TextField<>("pageTitleField", new PropertyModel<>(this, "pageTitle"));
        this.pageTitleField.setRequired(true);
        this.form.add(this.pageTitleField);
        this.pageTitleFeedback = new TextFeedbackPanel("pageTitleFeedback", this.pageTitleField);
        this.form.add(this.pageTitleFeedback);

        this.pageDescriptionField = new TextField<>("pageDescriptionField", new PropertyModel<>(this, "pageDescription"));
        this.pageDescriptionField.setRequired(true);
        this.form.add(this.pageDescriptionField);
        this.pageDescriptionFeedback = new TextFeedbackPanel("pageDescriptionFeedback", this.pageDescriptionField);
        this.form.add(this.pageDescriptionFeedback);

        this.htmlTitleField = new TextField<>("htmlTitleField", new PropertyModel<>(this, "htmlTitle"));
        this.htmlTitleField.setRequired(true);
        this.form.add(this.htmlTitleField);
        this.htmlTitleFeedback = new TextFeedbackPanel("htmlTitleFeedback", this.htmlTitleField);
        this.form.add(this.htmlTitleFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", PageBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("platform_page");
        updateQuery.addValue("html_title = :html_title", this.htmlTitle);
        updateQuery.addValue("page_title = :page_title", this.pageTitle);
        updateQuery.addValue("page_description = :page_description", this.pageDescription);
        if (this.layout == null) {
            updateQuery.addValue("platform_layout_id = NULL");
        } else {
            updateQuery.addValue("platform_layout_id = :platform_layout_id", this.layout.getId());
        }
        updateQuery.addWhere("platform_page_id = :platform_page_id", this.pageId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        getJdbcTemplate().update("delete from platform_page_role where platform_page_id = ?", this.pageId);

        if (this.role != null) {
            for (Option role : this.role) {
                InsertQuery insertQuery = new InsertQuery("platform_page_role");
                insertQuery.addValue("platform_page_role_id = :page_role_id", Platform.randomUUIDLong("platform_page_role"));
                insertQuery.addValue("platform_role_id = :role_id", role.getId());
                insertQuery.addValue("platform_page_id = :page_id", this.pageId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        setResponsePage(PageBrowsePage.class);
    }
}
