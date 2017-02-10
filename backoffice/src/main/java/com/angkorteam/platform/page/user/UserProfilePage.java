package com.angkorteam.platform.page.user;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.angkorteam.platform.page.DashboardPage;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import java.util.Arrays;

/**
 * Created by socheatkhauv on 10/2/17.
 */
public class UserProfilePage extends MBaaSPage {

    private String login;
    private Label loginLabel;

    private String fullName;
    private TextField<String> fullNameField;
    private TextFeedbackPanel fullNameFeedback;

    private String street;
    private TextField<String> streetField;
    private TextFeedbackPanel streetFeedback;

    private String city;
    private TextField<String> cityField;
    private TextFeedbackPanel cityFeedback;

    private String houseNumber;
    private TextField<String> houseNumberField;
    private TextFeedbackPanel houseNumberFeedback;

    private String zip;
    private TextField<String> zipField;
    private TextFeedbackPanel zipFeedback;

    private String phone;
    private TextField<String> phoneField;
    private TextFeedbackPanel phoneFeedback;

    private String gender;
    private DropDownChoice<String> genderField;
    private TextFeedbackPanel genderFeedback;

    private String country;
    private TextField<String> countryField;
    private TextFeedbackPanel countryFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        PlatformUser platformUser = jdbcTemplate.queryForObject("select * from platform_user where platform_user_id = ?", PlatformUser.class, getSession().getPlatformUserId());

        this.form = new Form<>("form");
        layout.add(this.form);

        this.login = platformUser.getLogin();
        this.loginLabel = new Label("loginLabel", new PropertyModel<>(this, "login"));
        this.form.add(this.loginLabel);

        this.fullName = platformUser.getFullName();
        this.fullNameField = new TextField<>("fullNameField", new PropertyModel<>(this, "fullName"));
        this.fullNameField.setRequired(true);
        this.form.add(this.fullNameField);
        this.fullNameFeedback = new TextFeedbackPanel("fullNameFeedback", this.fullNameField);
        this.form.add(this.fullNameFeedback);

        this.street = platformUser.getStreet();
        this.streetField = new TextField<>("streetField", new PropertyModel<>(this, "street"));
        this.streetField.setRequired(true);
        this.form.add(this.streetField);
        this.streetFeedback = new TextFeedbackPanel("streetFeedback", this.streetField);
        this.form.add(this.streetFeedback);

        this.city = platformUser.getCity();
        this.cityField = new TextField<>("cityField", new PropertyModel<>(this, "city"));
        this.cityField.setRequired(true);
        this.form.add(this.cityField);
        this.cityFeedback = new TextFeedbackPanel("cityFeedback", this.cityField);
        this.form.add(this.cityFeedback);

        this.houseNumber = platformUser.getHouseNumber();
        this.houseNumberField = new TextField<>("houseNumberField", new PropertyModel<>(this, "houseNumber"));
        this.houseNumberField.setRequired(true);
        this.form.add(this.houseNumberField);
        this.houseNumberFeedback = new TextFeedbackPanel("houseNumberFeedback", this.houseNumberField);
        this.form.add(this.houseNumberFeedback);

        this.zip = platformUser.getZip();
        this.zipField = new TextField<>("zipField", new PropertyModel<>(this, "zip"));
        this.zipField.setRequired(true);
        this.form.add(this.zipField);
        this.zipFeedback = new TextFeedbackPanel("zipFeedback", this.zipField);
        this.form.add(this.zipFeedback);

        this.phone = platformUser.getPhone();
        this.phoneField = new TextField<>("phoneField", new PropertyModel<>(this, "phone"));
        this.phoneField.setRequired(true);
        this.form.add(this.phoneField);
        this.phoneFeedback = new TextFeedbackPanel("phoneFeedback", this.phoneField);
        this.form.add(this.phoneFeedback);

        this.gender = platformUser.getGender();
        this.genderField = new DropDownChoice<>("genderField", new PropertyModel<>(this, "gender"), Arrays.asList("male", "female"));
        this.genderField.setRequired(true);
        this.form.add(this.genderField);
        this.genderFeedback = new TextFeedbackPanel("genderFeedback", this.genderField);
        this.form.add(this.genderFeedback);

        this.country = platformUser.getCountry();
        this.countryField = new TextField<>("countryField", new PropertyModel<>(this, "country"));
        this.countryField.setRequired(true);
        this.form.add(this.countryField);
        this.countryFeedback = new TextFeedbackPanel("countryFeedback", this.countryField);
        this.form.add(this.countryFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", DashboardPage.class);
        this.form.add(this.closeButton);

    }

    private void saveButtonOnSubmit(Button button) {
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        UpdateQuery updateQuery = new UpdateQuery("platform_user");
        updateQuery.addValue("full_name = :full_name", this.fullName);
        updateQuery.addValue("street = :street", this.street);
        updateQuery.addValue("city = :city", this.city);
        updateQuery.addValue("house_number = :house_number", this.houseNumber);
        updateQuery.addValue("zip = :zip", this.zip);
        updateQuery.addValue("phone = :phone", this.phone);
        updateQuery.addValue("gender = :gender", this.gender);
        updateQuery.addValue("country = :country", this.country);
        updateQuery.addWhere("platform_user_id = :platform_user_id", getSession().getPlatformUserId());
        named.update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(DashboardPage.class);
    }

}
