package com.angkorteam.ecommerce.page.size;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 24/1/17.
 */
public class SizeCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SizeCreatePage.class);

    private String reference;
    private TextField<String> referenceField;
    private TextFeedbackPanel referenceFeedback;

    private String value;
    private TextField<String> valueField;
    private TextFeedbackPanel valueFeedback;

    private BookmarkablePageLink<Void> closeButton;
    private Button saveButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.reference = StringUtils.upperCase(RandomStringUtils.randomAlphabetic(6));
        this.referenceField = new TextField<>("referenceField", new PropertyModel<>(this, "reference"));
        this.referenceField.add(new UniqueRecordValidator<>("ecommerce_size", "reference"));
        this.referenceField.setRequired(true);
        this.form.add(this.referenceField);
        this.referenceFeedback = new TextFeedbackPanel("referenceFeedback", this.referenceField);
        this.form.add(this.referenceFeedback);

        this.valueField = new TextField<>("valueField", new PropertyModel<>(this, "value"));
        this.valueField.setRequired(true);
        this.form.add(this.valueField);
        this.valueFeedback = new TextFeedbackPanel("valueFeedback", this.valueField);
        this.form.add(this.valueFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", SizeBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        InsertQuery insertQuery = null;
        insertQuery = new InsertQuery("ecommerce_size");
        insertQuery.addValue("ecommerce_size_id = :ecommerce_size_id", Platform.randomUUIDLong("ecommerce_size"));
        insertQuery.addValue("`value` = :value", this.value);
        insertQuery.addValue("reference = :reference", this.reference);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        setResponsePage(SizeBrowsePage.class);
    }

}