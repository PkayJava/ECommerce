package com.angkorteam.platform.page.user;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.model.PlatformUser;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 22/1/17.
 */
public class UserPasswordPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPasswordPage.class);

    private String userId;

    private String fullName;
    private Label fullNameLabel;

    private String login;
    private Label loginLabel;

    private String password;
    private TextField<String> passwordField;
    private TextFeedbackPanel passwordFeedback;

    private String retypePassword;
    private TextField<String> retypePasswordField;
    private TextFeedbackPanel retypePasswordFeedback;

    private Button saveButton;
    private Form<Void> form;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        PageParameters parameters = getPageParameters();
        this.userId = parameters.get("userId").toString("");

        PlatformUser userRecord = getJdbcTemplate().queryForObject("select * from platform_user where platform_user_id = ?", PlatformUser.class, this.userId);

        this.fullName = userRecord.getFullName();
        this.fullNameLabel = new Label("fullNameLabel", new PropertyModel<>(this, "fullName"));
        this.form.add(fullNameLabel);

        this.login = userRecord.getLogin();
        this.loginLabel = new Label("loginLabel", new PropertyModel<>(this, "login"));
        this.form.add(loginLabel);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "password"));
        this.form.add(this.passwordField);
        this.passwordFeedback = new TextFeedbackPanel("passwordFeedback", this.passwordField);
        this.form.add(this.passwordFeedback);

        this.retypePasswordField = new PasswordTextField("retypePasswordField", new PropertyModel<>(this, "retypePassword"));
        this.form.add(retypePasswordField);
        this.retypePasswordFeedback = new TextFeedbackPanel("retypePasswordFeedback", this.retypePasswordField);
        this.form.add(retypePasswordFeedback);

        this.form.add(new EqualPasswordInputValidator(this.passwordField, this.retypePasswordField));

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", UserBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("platform_user");
        updateQuery.addValue("password = MD5(:password)", "password", this.password);
        updateQuery.addWhere("user_id = :user_id", this.userId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(UserBrowsePage.class);
    }

}