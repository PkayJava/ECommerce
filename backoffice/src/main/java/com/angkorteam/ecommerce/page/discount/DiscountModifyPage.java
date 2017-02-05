package com.angkorteam.ecommerce.page.discount;

import com.angkorteam.ecommerce.model.EcommerceDiscount;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class DiscountModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountModifyPage.class);

    private String ecommerceDiscountId;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String type;
    private TextField<String> typeField;
    private TextFeedbackPanel typeFeedback;

    private String value;
    private TextField<String> valueField;
    private TextFeedbackPanel valueFeedback;

    private String valueFormatted;
    private TextField<String> valueFormattedField;
    private TextFeedbackPanel valueFormattedFeedback;

    private String minCartAmount;
    private TextField<String> minCartAmountField;
    private TextFeedbackPanel minCartAmountFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceDiscountId = getPageParameters().get("ecommerceDiscountId").toString("");

        EcommerceDiscount record = getJdbcTemplate().queryForObject("select * from ecommerce_discount where ecommerce_discount_id = ?", EcommerceDiscount.class, this.ecommerceDiscountId);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.name = record.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<String>(this, "name"));
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_discount", "name", "ecommerce_discount_id", this.ecommerceDiscountId));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.type = record.getType();
        this.typeField = new TextField<>("typeField", new PropertyModel<String>(this, "type"));
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        this.value = record.getValue();
        this.valueField = new TextField<>("valueField", new PropertyModel<String>(this, "value"));
        this.valueField.setRequired(true);
        this.form.add(this.valueField);
        this.valueFeedback = new TextFeedbackPanel("valueFeedback", this.valueField);
        this.form.add(this.valueFeedback);

        this.valueFormatted = record.getValueFormatted();
        this.valueFormattedField = new TextField<>("valueFormattedField", new PropertyModel<String>(this, "valueFormatted"));
        this.valueFormattedField.setRequired(true);
        this.form.add(this.valueFormattedField);
        this.valueFormattedFeedback = new TextFeedbackPanel("valueFormattedFeedback", this.valueFormattedField);
        this.form.add(this.valueFormattedFeedback);

        this.minCartAmount = record.getMinCartAmount();
        this.minCartAmountField = new TextField<>("minCartAmountField", new PropertyModel<String>(this, "minCartAmount"));
        this.minCartAmountField.setRequired(true);
        this.form.add(this.minCartAmountField);
        this.minCartAmountFeedback = new TextFeedbackPanel("minCartAmountFeedback", this.minCartAmountField);
        this.form.add(this.minCartAmountFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", DiscountBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("ecommerce_discount");
        updateQuery.addValue("name = :name", name);
        updateQuery.addValue("type = :type", this.type);
        updateQuery.addValue("`value` = :value", this.value);
        updateQuery.addValue("value_formatted = :value_formatted", this.valueFormatted);
        updateQuery.addValue("min_cart_amount = :min_cart_amount", this.minCartAmount);
        updateQuery.addWhere("ecommerce_discount_id = :ecommerce_discount_id", this.ecommerceDiscountId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(DiscountBrowsePage.class);
    }

}