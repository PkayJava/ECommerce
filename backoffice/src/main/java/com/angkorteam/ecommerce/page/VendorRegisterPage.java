package com.angkorteam.ecommerce.page;

import com.angkorteam.ecommerce.page.product.ProductBrowsePage;
import com.angkorteam.framework.extension.wicket.AdminLTEPage;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformRole;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 5/2/17.
 */
public class VendorRegisterPage extends AdminLTEPage {

    private String fullName;
    private TextField<String> fullNameField;

    private String login;
    private TextField<String> loginField;

    private String password;
    private PasswordTextField passwordField;

    private String retypePassword;
    private PasswordTextField retypePasswordField;

    private boolean term;
    private CheckBox termField;

    private Form<Void> form;
    private Button registerButton;

    private BookmarkablePageLink<Void> dashboardLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.fullNameField = new TextField<>("fullNameField", new PropertyModel<>(this, "fullName"));
        this.fullNameField.setRequired(true);
        this.form.add(this.fullNameField);

        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "login"));
        this.loginField.add(new UniqueRecordValidator<>("platform_user", "login"));
        this.loginField.setRequired(true);
        this.form.add(this.loginField);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "password"));
        this.passwordField.setRequired(true);
        this.form.add(this.passwordField);

        this.retypePasswordField = new PasswordTextField("retypePasswordField", new PropertyModel<>(this, "retypePassword"));
        this.retypePasswordField.setRequired(true);
        this.form.add(this.retypePasswordField);

        this.termField = new CheckBox("termField", new PropertyModel<>(this, "term"));
        this.termField.setRequired(true);
        this.form.add(this.termField);

        this.form.add(new EqualPasswordInputValidator(this.passwordField, this.retypePasswordField));

        this.registerButton = new Button("registerButton");
        this.registerButton.setOnSubmit(this::registerButtonOnSubmit);
        this.form.add(this.registerButton);

        this.dashboardLink = new BookmarkablePageLink<Void>("dashboardLink", ProductBrowsePage.class);
        add(this.dashboardLink);
    }

    private void registerButtonOnSubmit(Button button) {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        SelectQuery selectQuery = new SelectQuery("platform_role");
        selectQuery.addWhere("name = :name", "ecommerce_vendor");
        PlatformRole roleRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), PlatformRole.class);

        InsertQuery insertQuery = new InsertQuery("platform_user");
        insertQuery.addValue("platform_user_id = :platform_user_id", Platform.randomUUIDLong());
        insertQuery.addValue("login = :login", this.login);
        insertQuery.addValue("password = MD5(:password)", "password", this.password);
        insertQuery.addValue("platform_role_id = :platform_role_id", roleRecord.getPlatformRoleId());
        insertQuery.addValue("system = :system", false);
        insertQuery.addValue("status = :status", "ACTIVE");
        insertQuery.addValue("full_name = :name", "");
        insertQuery.addValue("gender = :gender", "");
        insertQuery.addValue("access_token = :access_token", Platform.randomUUIDString());

        named.update(insertQuery.toSQL(), insertQuery.getParam());
        setResponsePage(ProductBrowsePage.class);

    }
}
