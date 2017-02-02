package com.angkorteam.platform.page;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 21/1/17.
 */
public class SettingPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingPage.class);

    private Option homePage;
    private Select2SingleChoice<Option> homePageField;
    private TextFeedbackPanel homePageFeedback;

    private String googleApiKey;
    private TextField<String> googleApiKeyField;
    private TextFeedbackPanel googleApiKeyFeedback;

    private String asset;
    private TextField<String> assetField;
    private TextFeedbackPanel assetFeedback;

    private String priceFormat;
    private TextField<String> priceFormatField;
    private TextFeedbackPanel priceFormatFeedback;

    private String currency;
    private TextField<String> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private String dateFormat;
    private TextField<String> dateFormatField;
    private TextFeedbackPanel dateFormatFeedback;

    private String datetimeFormat;
    private TextField<String> datetimeFormatField;
    private TextFeedbackPanel datetimeFormatFeedback;

    private String timeFormat;
    private TextField<String> timeFormatField;
    private TextFeedbackPanel timeFormatFeedback;

    private Button saveButton;
    private Form<Void> form;

    public final String HOME_PAGE = "home_page";

    public final String GOOGLE_API_KEY = "google_api_key";

    public final String ASSET = "asset";

    public final String CURRENCY = "currency";

    public final String PRICE_FORMAT = "price_format";

    public final String DATE_FORMAT = "date_format";

    public final String DATETIME_FORMAT = "datetime_format";

    public final String TIME_FORMAT = "time_format";

    @Override
    protected void doInitialize(Border layout) {
        add(layout);
        // place your initialization logic here

        this.form = new Form<>("form");
        layout.add(this.form);

        this.homePageField = new Select2SingleChoice<>("homePageField", new PropertyModel<>(this, "homePage"), new OptionSingleChoiceProvider("page", "page_id", "page_title"));
        this.homePageField.setRequired(true);
        this.form.add(this.homePageField);
        this.homePageFeedback = new TextFeedbackPanel("homePageFeedback", this.homePageField);
        this.form.add(this.homePageFeedback);

        this.googleApiKeyField = new TextField<>("googleApiKeyField", new PropertyModel<>(this, "googleApiKey"));
        this.googleApiKeyField.setRequired(true);
        this.form.add(this.googleApiKeyField);
        this.googleApiKeyFeedback = new TextFeedbackPanel("googleApiKeyFeedback", this.googleApiKeyField);
        this.form.add(this.googleApiKeyFeedback);

        this.assetField = new TextField<>("assetField", new PropertyModel<>(this, "asset"));
        this.assetField.setRequired(true);
        this.form.add(this.assetField);
        this.assetFeedback = new TextFeedbackPanel("assetFeedback", this.assetField);
        this.form.add(this.assetFeedback);

        this.priceFormatField = new TextField<>("priceFormatField", new PropertyModel<>(this, "priceFormat"));
        this.priceFormatField.setRequired(true);
        this.form.add(this.priceFormatField);
        this.priceFormatFeedback = new TextFeedbackPanel("priceFormatFeedback", this.priceFormatField);
        this.form.add(this.priceFormatFeedback);

        this.currencyField = new TextField<>("currencyField", new PropertyModel<>(this, "currency"));
        this.currencyField.setRequired(true);
        this.form.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.form.add(this.currencyFeedback);

        this.dateFormatField = new TextField<>("dateFormatField", new PropertyModel<>(this, "dateFormat"));
        this.dateFormatField.setRequired(true);
        this.form.add(this.dateFormatField);
        this.dateFormatFeedback = new TextFeedbackPanel("dateFormatFeedback", this.dateFormatField);
        this.form.add(this.dateFormatFeedback);

        this.timeFormatField = new TextField<>("timeFormatField", new PropertyModel<>(this, "timeFormat"));
        this.timeFormatField.setRequired(true);
        this.form.add(this.timeFormatField);
        this.timeFormatFeedback = new TextFeedbackPanel("timeFormatFeedback", this.timeFormatField);
        this.form.add(this.timeFormatFeedback);

        this.datetimeFormatField = new TextField<>("datetimeFormatField", new PropertyModel<>(this, "datetimeFormat"));
        this.datetimeFormatField.setRequired(true);
        this.form.add(this.datetimeFormatField);
        this.datetimeFormatFeedback = new TextFeedbackPanel("datetimeFormatFeedback", this.datetimeFormatField);
        this.form.add(this.datetimeFormatFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        loadSetting();
    }

    void loadSetting() {
        loadHomePage(HOME_PAGE);
        loadSetting(GOOGLE_API_KEY, new PropertyModel<>(this, "googleApiKey"));
        loadSetting(ASSET, new PropertyModel<>(this, "asset"));
        loadSetting(PRICE_FORMAT, new PropertyModel<>(this, "priceFormat"));
        loadSetting(CURRENCY, new PropertyModel<>(this, "currency"));
        loadSetting(TIME_FORMAT, new PropertyModel<>(this, "timeFormat"));
        loadSetting(DATE_FORMAT, new PropertyModel<>(this, "dateFormat"));
        loadSetting(DATETIME_FORMAT, new PropertyModel<>(this, "datetimeFormat"));
    }

    void loadSetting(String key, PropertyModel<String> model) {
        verify(key);
        String value = getJdbcTemplate().queryForObject("select value from setting where `key` = ?", String.class, key);
        model.setObject(value);
    }

    void verify(String key) {
        int count = getJdbcTemplate().queryForObject("select count(*) from setting where `key` = ?", int.class, key);
        if (count == 0) {
            InsertQuery insertQuery = new InsertQuery("setting");
            insertQuery.addValue("setting_id = :setting_id", randomUUIDLong());
            insertQuery.addValue("`key` = :key", key);
            insertQuery.addValue("`value` = :value", "");
            insertQuery.addValue("`version` = :version", 1);
            getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
        }
    }

    void loadHomePage(String key) {
        verify(key);
        SelectQuery selectQuery = new SelectQuery("setting");
        selectQuery.addField("page.page_id AS id");
        selectQuery.addField("page.page_title AS text");
        selectQuery.addJoin(JoinType.InnerJoin, "page", "setting.value = page.page_id");
        selectQuery.addWhere("setting.key = :keu", key);
        this.homePage = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
    }

    private void saveButtonSubmit(Button button) {

        saveSetting(HOME_PAGE, this.homePage.getId());
        saveSetting(GOOGLE_API_KEY, this.googleApiKey);
        saveSetting(ASSET, this.asset);
        saveSetting(CURRENCY, this.currency);
        saveSetting(PRICE_FORMAT, this.priceFormat);
        saveSetting(TIME_FORMAT, this.timeFormat);
        saveSetting(DATE_FORMAT, this.dateFormat);
        saveSetting(DATETIME_FORMAT, this.datetimeFormat);

        setResponsePage(SettingPage.class);
    }

    void saveSetting(String key, String value) {
        UpdateQuery updateQuery = new UpdateQuery("setting");
        updateQuery.addValue("`value` = :value", value);
        updateQuery.addWhere("`key` = :key", key);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
    }

}