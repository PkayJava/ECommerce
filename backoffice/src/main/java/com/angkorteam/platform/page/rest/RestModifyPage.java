package com.angkorteam.platform.page.rest;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.model.PlatformRest;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionMultipleChoiceProvider;
import com.angkorteam.platform.validator.RestNameValidator;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by socheat on 8/3/16.
 */
public class RestModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestModifyPage.class);

    private String restId;

    private List<Option> role;
    private Select2MultipleChoice<Option> roleField;
    private TextFeedbackPanel roleFeedback;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.restId = getPageParameters().get("restId").toString("");

        PlatformRest rest = getJdbcTemplate().queryForObject("select * from platform_rest where platform_rest_id = ?", PlatformRest.class, this.restId);

        this.form = new Form<>("form");
        layout.add(this.form);

        SelectQuery selectQuery = new SelectQuery("platform_role");
        selectQuery.addField("platform_role.platform_role_id AS id");
        selectQuery.addField("platform_role.name AS text");
        selectQuery.addJoin(JoinType.InnerJoin, "platform_rest_role", "platform_rest_role.platform_role_id = platform_role.platform_role_id");
        selectQuery.addWhere("platform_rest_role.platform_rest_id = :rest_id", this.restId);

        this.role = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
        this.roleField = new Select2MultipleChoice<>("roleField", new PropertyModel<>(this, "role"), new OptionMultipleChoiceProvider("platform_role", "platform_role_id", "name"));
        this.form.add(this.roleField);
        this.roleFeedback = new TextFeedbackPanel("roleFeedback", this.roleField);
        this.form.add(this.roleFeedback);

        this.description = rest.getDescription();
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.descriptionField.setOutputMarkupId(true);
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.name = rest.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setOutputMarkupId(true);
        this.nameField.add(new RestNameValidator(this.restId));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.closeButton = new BookmarkablePageLink<>("closeButton", RestBrowsePage.class);
        this.form.add(this.closeButton);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);
    }

    private void saveButtonOnSubmit(Button button) {

        UpdateQuery updateQuery = new UpdateQuery("platform_rest");
        updateQuery.addValue("name = :name", this.name);
        updateQuery.addValue("description = :description", this.description);
        updateQuery.addWhere("platform_rest_id = :platform_rest_id", this.restId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        getJdbcTemplate().update("delete from platform_rest_role where platform_rest_id = ?", this.restId);

        if (this.role != null) {
            for (Option role : this.role) {
                InsertQuery insertQuery = new InsertQuery("platform_rest_role");
                insertQuery.addValue("platform_rest_role_id = :rest_role", randomUUIDLong());
                insertQuery.addValue("platform_role_id = :role_id", role.getId());
                insertQuery.addValue("platform_rest_id = :rest_id", this.restId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        setResponsePage(RestBrowsePage.class);
    }

}
