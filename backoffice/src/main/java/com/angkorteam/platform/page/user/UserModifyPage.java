package com.angkorteam.platform.page.user;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.model.PlatformUser;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 22/1/17.
 */
public class UserModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserModifyPage.class);

    private String userId;

    private String fullName;
    private TextField<String> fullNameField;
    private TextFeedbackPanel fullNameFeedback;

    private String login;
    private Label loginLabel;

    private Option role;
    private Select2SingleChoice<Option> roleField;
    private TextFeedbackPanel roleFeedback;

    private Button saveButton;
    private Form<Void> form;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        PageParameters parameters = getPageParameters();
        this.userId = parameters.get("userId").toString("");

        PlatformUser userRecord = getJdbcTemplate().queryForObject("select * from platform_user where platform_user_id = ?", PlatformUser.class, this.userId);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.fullName = userRecord.getFullName();
        this.fullNameField = new TextField<>("fullNameField", new PropertyModel<>(this, "fullName"));
        this.fullNameField.setRequired(true);
        this.form.add(fullNameField);
        this.fullNameFeedback = new TextFeedbackPanel("fullNameFeedback", this.fullNameField);
        this.form.add(fullNameFeedback);

        this.login = userRecord.getLogin();
        this.loginLabel = new Label("loginLabel", new PropertyModel<>(this, "login"));
        this.form.add(loginLabel);

        if (userRecord.getPlatformRoleId() != null) {
            SelectQuery selectQuery = new SelectQuery("platform_role");
            selectQuery.addField("platform_role_id AS id");
            selectQuery.addField("name AS text");
            selectQuery.addWhere("platform_role_id = :platform_role_id", userRecord.getPlatformRoleId());
            this.role = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
        }
        this.roleField = new Select2SingleChoice<>("roleField", new PropertyModel<>(this, "role"), new OptionSingleChoiceProvider("platform_role", "platform_role_id", "name"));
        this.roleField.setRequired(true);
        this.form.add(this.roleField);
        this.roleFeedback = new TextFeedbackPanel("roleFeedback", this.roleField);
        this.form.add(this.roleFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", UserBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("platform_user");
        updateQuery.addValue("full_name = :full_name", this.fullName);
        updateQuery.addValue("platform_role_id = :role_id", this.role.getId());
        updateQuery.addWhere("platform_user_id = :user_id", this.userId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(UserBrowsePage.class);
    }

}