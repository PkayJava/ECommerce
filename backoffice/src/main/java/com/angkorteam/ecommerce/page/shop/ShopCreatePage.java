package com.angkorteam.ecommerce.page.shop;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by socheatkhauv on 26/1/17.
 */
public class ShopCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopCreatePage.class);

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private String google;
    private TextField<String> googleField;
    private TextFeedbackPanel googleFeedback;

    private List<FileUpload> logo;
    private FileUploadField logoField;
    private TextFeedbackPanel logoFeedback;

    private String language;
    private TextField<String> languageField;
    private TextFeedbackPanel languageFeedback;

    private String url;
    private TextField<String> urlField;
    private TextFeedbackPanel urlFeedback;

    private List<FileUpload> flagIcon;
    private FileUploadField flagIconField;
    private TextFeedbackPanel flagIconFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_shop", "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.languageField = new TextField<>("languageField", new PropertyModel<>(this, "language"));
        this.languageField.setRequired(true);
        this.form.add(this.languageField);
        this.languageFeedback = new TextFeedbackPanel("languageFeedback", this.languageField);
        this.form.add(this.languageFeedback);

        this.logoField = new FileUploadField("logoField", new PropertyModel<>(this, "logo"));
        this.form.add(this.logoField);
        this.logoFeedback = new TextFeedbackPanel("logoFeedback", this.logoField);
        this.form.add(this.logoFeedback);

        this.googleField = new TextField<>("googleField", new PropertyModel<>(this, "google"));
        this.googleField.setRequired(true);
        this.form.add(this.googleField);
        this.googleFeedback = new TextFeedbackPanel("googleFeedback", this.googleField);
        this.form.add(this.googleFeedback);

        this.urlField = new TextField<>("urlField", new PropertyModel<>(this, "url"));
        this.form.add(this.urlField);
        this.urlFeedback = new TextFeedbackPanel("urlFeedback", this.urlField);
        this.form.add(this.urlFeedback);

        this.flagIconField = new FileUploadField("flagIconField", new PropertyModel<>(this, "flagIcon"));
        this.flagIconField.setRequired(true);
        this.form.add(this.flagIconField);
        this.flagIconFeedback = new TextFeedbackPanel("flagIconFeedback", this.flagIconField);
        this.form.add(this.flagIconFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ShopBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        Long flagIconFileId = null;
        if (this.flagIcon != null && !this.flagIcon.isEmpty()) {
            File file = new File(FileUtils.getTempDirectory(), randomUUIDLong() + this.flagIcon.get(0).getClientFileName());
            try {
                this.flagIcon.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            flagIconFileId = Platform.saveFile(file);
            file.delete();
        }

        Long logoFileId = null;
        if (this.logo != null && !this.logo.isEmpty() && this.logo.get(0).getSize() > 0) {
            File file = new File(FileUtils.getTempDirectory(), randomUUIDLong() + this.logo.get(0).getClientFileName());
            try {
                this.logo.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            logoFileId = Platform.saveFile(file);
            file.delete();
        }

        InsertQuery insertQuery = new InsertQuery("ecommerce_shop");
        insertQuery.addValue("ecommerce_shop_id = ?", randomUUIDLong());
        insertQuery.addValue("name = :name", this.name);
        insertQuery.addValue("description = :description", this.description);
        insertQuery.addValue("language = :language", this.language);
        insertQuery.addValue("url = :url", this.url);
        insertQuery.addValue("google_ua = :google_ua", this.google);
        insertQuery.addValue("logo_file_id = :logo_file_id", logoFileId);
        insertQuery.addValue("flag_icon_file_id = :flag_icon_file_id", flagIconFileId);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
        setResponsePage(ShopBrowsePage.class);
    }

}