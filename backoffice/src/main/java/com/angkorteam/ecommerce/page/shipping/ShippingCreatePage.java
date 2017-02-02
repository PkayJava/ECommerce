package com.angkorteam.ecommerce.page.shipping;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionMultipleChoiceProvider;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class ShippingCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingCreatePage.class);

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Double price;
    private TextField<Double> priceField;
    private TextFeedbackPanel priceFeedback;

    private Integer minCartAmount;
    private TextField<Integer> minCartAmountField;
    private TextFeedbackPanel minCartAmountFeedback;

    private String availabilityTime;
    private TextField<String> availabilityTimeField;
    private TextFeedbackPanel availabilityTimeFeedback;

    private String availabilityDate;
    private TextField<String> availabilityDateField;
    private TextFeedbackPanel availabilityDateFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private List<String> types = Arrays.asList("Pickup", "Delivery");
    private String type;
    private DropDownChoice<String> typeField;
    private TextFeedbackPanel typeFeedback;

    private Option branch;
    private Select2SingleChoice<Option> branchField;
    private TextFeedbackPanel branchFeedback;

    private List<Option> payment;
    private Select2MultipleChoice<Option> paymentField;
    private TextFeedbackPanel paymentFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.priceField = new TextField<>("priceField", new PropertyModel<>(this, "price"));
        this.priceField.setRequired(true);
        this.form.add(this.priceField);
        this.priceFeedback = new TextFeedbackPanel("priceFeedback", this.priceField);
        this.form.add(this.priceFeedback);

        this.minCartAmountField = new TextField<>("minCartAmountField", new PropertyModel<>(this, "minCartAmount"));
        this.minCartAmountField.setRequired(true);
        this.form.add(this.minCartAmountField);
        this.minCartAmountFeedback = new TextFeedbackPanel("minCartAmountFeedback", this.minCartAmountField);
        this.form.add(this.minCartAmountFeedback);

        this.availabilityTimeField = new TextField<>("availabilityTimeField", new PropertyModel<>(this, "availabilityTime"));
        this.form.add(this.availabilityTimeField);
        this.availabilityTimeFeedback = new TextFeedbackPanel("availabilityTimeFeedback", this.availabilityTimeField);
        this.form.add(this.availabilityTimeFeedback);

        this.availabilityDateField = new TextField<>("availabilityDateField", new PropertyModel<>(this, "availabilityDate"));
        this.form.add(this.availabilityDateField);
        this.availabilityDateFeedback = new TextFeedbackPanel("availabilityDateFeedback", this.availabilityDateField);
        this.form.add(this.availabilityDateFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.typeField = new DropDownChoice<>("typeField", new PropertyModel<>(this, "type"), new PropertyModel<>(this, "types"));
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        this.branchField = new Select2SingleChoice<>("branchField", new PropertyModel<>(this, "branch"), new OptionSingleChoiceProvider("ecommerce_branch", "ecommerce_branch_id", "name"));
        this.form.add(this.branchField);
        this.branchFeedback = new TextFeedbackPanel("branchFeedback", this.branchField);
        this.form.add(this.branchFeedback);

        this.paymentField = new Select2MultipleChoice<>("paymentField", new PropertyModel<>(this, "payment"), new OptionMultipleChoiceProvider("ecommerce_payment", "ecommerce_payment_id", "name"));
        this.form.add(this.paymentField);
        this.paymentFeedback = new TextFeedbackPanel("paymentFeedback", this.paymentField);
        this.form.add(this.paymentFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ShippingBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {

        String branchId = null;
        if (this.branch != null) {
            branchId = this.branch.getId();
        }

        Long shippingId = randomUUIDLong();
        InsertQuery insertQuery = null;
        insertQuery = new InsertQuery("ecommerce_shipping");
        insertQuery.addValue("ecommerce_shipping_id = :ecommerce_shipping_id", shippingId);
        insertQuery.addValue("name = :name", this.name);
        insertQuery.addValue("min_cart_amount = :min_cart_amount", this.minCartAmount);
        insertQuery.addValue("ecommerce_branch_id = :ecommerce_branch_id", branchId);
        insertQuery.addValue("description = :description", this.description);
        insertQuery.addValue("availability_time = :availability_time", this.availabilityTime);
        insertQuery.addValue("availability_date = :availability_date", this.availabilityDate);
        insertQuery.addValue("type = :type", this.type);
        insertQuery.addValue("price = :price", this.price);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        if (this.payment != null && !this.payment.isEmpty()) {
            for (Option payment : this.payment) {
                insertQuery = new InsertQuery("ecommerce_shipping_payment");
                insertQuery.addValue("ecommerce_shipping_payment_id = :ecommerce_shipping_payment_id", randomUUIDLong());
                insertQuery.addValue("ecommerce_payment_id = :ecommerce_payment_id", payment.getId());
                insertQuery.addValue("ecommerce_shipping_id = :ecommerce_shipping_id", shippingId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }
        setResponsePage(ShippingBrowsePage.class);
    }

}