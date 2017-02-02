package com.angkorteam.platform.page.role;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionMultipleChoiceProvider;
import com.angkorteam.platform.model.PlatformRole;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

/**
 * Created by socheat on 10/24/16.
 */
public class RoleModifyPage extends MBaaSPage {

    private String roleId;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private List<Option> pageValue;
    private Select2MultipleChoice<Option> pageField;
    private TextFeedbackPanel pageFeedback;

    private List<Option> rest;
    private Select2MultipleChoice<Option> restField;
    private TextFeedbackPanel restFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        PageParameters parameters = getPageParameters();
        this.roleId = parameters.get("roleId").toString("");

        PlatformRole role = getJdbcTemplate().queryForObject("select * from role where role_id = ?", PlatformRole.class, this.roleId);
        this.name = role.getName();
        this.description = role.getDescription();

        SelectQuery selectQuery = new SelectQuery("page");
        selectQuery.addField("page.page_id AS id");
        selectQuery.addField("page.page_title AS text");
        selectQuery.addJoin(JoinType.InnerJoin, "page_role", "page_role.page_id = page.page_id");
        selectQuery.addWhere("page_role.role_id = :role_id", this.roleId);
        this.pageValue = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);

        selectQuery = new SelectQuery("rest");
        selectQuery.addField("rest.rest_id AS id");
        selectQuery.addField("rest.name AS text");
        selectQuery.addJoin(JoinType.InnerJoin, "rest_role", "rest_role.rest_id = rest.rest_id");
        selectQuery.addWhere("rest_role.role_id = :role_id", this.roleId);
        this.rest = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.pageField = new Select2MultipleChoice<>("pageField", new PropertyModel<>(this, "pageValue"), new OptionMultipleChoiceProvider("page", "page_id", "page_title"));
        this.form.add(this.pageField);
        this.pageFeedback = new TextFeedbackPanel("pageFeedback", this.pageField);
        this.form.add(this.pageFeedback);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.restField = new Select2MultipleChoice<>("restField", new PropertyModel<>(this, "rest"), new OptionMultipleChoiceProvider("rest", "rest_id", "name"));
        this.form.add(this.restField);
        this.restFeedback = new TextFeedbackPanel("restFeedback", this.restField);
        this.form.add(this.restFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", RoleBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {

        UpdateQuery updateQuery = new UpdateQuery("role");
        updateQuery.addValue("role_id = :role_id", randomUUIDLong());
        updateQuery.addValue("name = :name", this.name);
        updateQuery.addValue("description = :description", this.description);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        getJdbcTemplate().update("delete from page_role where role_id = ?", this.roleId);

        if (this.pageValue != null && !this.pageValue.isEmpty()) {
            for (Option page : this.pageValue) {
                InsertQuery insertQuery = new InsertQuery("page_role");
                insertQuery.addValue("page_role_id = :page_role_id", randomUUIDLong());
                insertQuery.addValue("page_id = :page_id", page.getId());
                insertQuery.addValue("role_id = :role_id", this.roleId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        getJdbcTemplate().update("delete from rest_role where role_id = ?", this.roleId);

        if (this.rest != null && !this.rest.isEmpty()) {
            for (Option rest : this.rest) {
                InsertQuery insertQuery = new InsertQuery("rest_role");
                insertQuery.addValue("rest_role_id = :rest_role_id", randomUUIDLong());
                insertQuery.addValue("rest_id = :rest_id", rest.getId());
                insertQuery.addValue("role_id = :role_id", this.roleId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        setResponsePage(RoleBrowsePage.class);
    }

}
