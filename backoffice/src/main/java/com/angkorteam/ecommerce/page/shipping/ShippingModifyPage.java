package com.angkorteam.ecommerce.page.shipping;

import com.angkorteam.ecommerce.model.EcommerceShipping;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
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
public class ShippingModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingModifyPage.class);

    private String ecommerceShippingId;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Double price;
    private TextField<Double> priceField;
    private TextFeedbackPanel priceFeedback;

    private Double minCartAmount;
    private TextField<Double> minCartAmountField;
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

        this.ecommerceShippingId = getPageParameters().get("ecommerceShippingId").toString("");

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_shipping");
        selectQuery.addWhere("ecommerce_shipping_id = :ecommerce_shipping_id", this.ecommerceShippingId);

        EcommerceShipping ecommerceShipping = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceShipping.class);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.name = ecommerceShipping.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.price = ecommerceShipping.getPrice();
        this.priceField = new TextField<>("priceField", new PropertyModel<>(this, "price"));
        this.priceField.setRequired(true);
        this.form.add(this.priceField);
        this.priceFeedback = new TextFeedbackPanel("priceFeedback", this.priceField);
        this.form.add(this.priceFeedback);

        this.minCartAmount = ecommerceShipping.getMinCartAmount();
        this.minCartAmountField = new TextField<>("minCartAmountField", new PropertyModel<>(this, "minCartAmount"));
        this.minCartAmountField.setRequired(true);
        this.form.add(this.minCartAmountField);
        this.minCartAmountFeedback = new TextFeedbackPanel("minCartAmountFeedback", this.minCartAmountField);
        this.form.add(this.minCartAmountFeedback);

        this.availabilityTime = ecommerceShipping.getAvailabilityTime();
        this.availabilityTimeField = new TextField<>("availabilityTimeField", new PropertyModel<>(this, "availabilityTime"));
        this.form.add(this.availabilityTimeField);
        this.availabilityTimeFeedback = new TextFeedbackPanel("availabilityTimeFeedback", this.availabilityTimeField);
        this.form.add(this.availabilityTimeFeedback);

        this.availabilityDate = ecommerceShipping.getAvailabilityDate();
        this.availabilityDateField = new TextField<>("availabilityDateField", new PropertyModel<>(this, "availabilityDate"));
        this.form.add(this.availabilityDateField);
        this.availabilityDateFeedback = new TextFeedbackPanel("availabilityDateFeedback", this.availabilityDateField);
        this.form.add(this.availabilityDateFeedback);

        this.description = ecommerceShipping.getDescription();
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.type = ecommerceShipping.getType();
        this.typeField = new DropDownChoice<>("typeField", new PropertyModel<>(this, "type"), new PropertyModel<>(this, "types"));
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        selectQuery = new SelectQuery("ecommerce_branch");
        selectQuery.addField("ecommerce_branch_id AS id", "name AS text");
        selectQuery.addWhere("ecommerce_branch_id = :ecommerce_branch_id", ecommerceShipping.getEcommerceBranchId());
        this.branch = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
        OptionSingleChoiceProvider branchOption = new OptionSingleChoiceProvider("ecommerce_branch", "ecommerce_branch_id", "name");
        branchOption.addWhere("enabled = true");
        this.branchField = new Select2SingleChoice<>("branchField", new PropertyModel<>(this, "branch"), branchOption);
        this.form.add(this.branchField);
        this.branchFeedback = new TextFeedbackPanel("branchFeedback", this.branchField);
        this.form.add(this.branchFeedback);

        selectQuery = new SelectQuery("ecommerce_payment");
        selectQuery.addField("ecommerce_payment.ecommerce_payment_id AS id", "ecommerce_payment.name AS text");
        selectQuery.addJoin(JoinType.InnerJoin, "ecommerce_shipping_payment", "ecommerce_shipping_payment.ecommerce_payment_id = ecommerce_payment.ecommerce_payment_id");
        selectQuery.addWhere("ecommerce_shipping_payment.ecommerce_shipping_id = :ecommerce_shipping_id", this.ecommerceShippingId);
        this.payment = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
        OptionMultipleChoiceProvider paymentOption = new OptionMultipleChoiceProvider("ecommerce_payment", "ecommerce_payment_id", "name");
        paymentOption.addWhere("enabled = true");
        this.paymentField = new Select2MultipleChoice<>("paymentField", new PropertyModel<>(this, "payment"), paymentOption);
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

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_shipping");
        updateQuery.addValue("name = :name", this.name);
        updateQuery.addValue("min_cart_amount = :min_cart_amount", this.minCartAmount);
        updateQuery.addValue("ecommerce_branch_id = :ecommerce_branch_id", branchId);
        updateQuery.addValue("description = :description", this.description);
        updateQuery.addValue("availability_time = :availability_time", this.availabilityTime);
        updateQuery.addValue("availability_date = :availability_date", this.availabilityDate);
        updateQuery.addValue("type = :type", this.type);
        updateQuery.addValue("price = :price", this.price);
        updateQuery.addWhere("ecommerce_shipping_id = :ecommerce_shipping_id", this.ecommerceShippingId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        getJdbcTemplate().update("delete from ecommerce_shipping_payment where ecommerce_shipping_id = ?", this.ecommerceShippingId);


        if (this.payment != null && !this.payment.isEmpty()) {
            for (Option payment : this.payment) {
                InsertQuery insertQuery = new InsertQuery("ecommerce_shipping_payment");
                insertQuery.addValue("ecommerce_shipping_payment_id = :ecommerce_shipping_payment_id", Platform.randomUUIDLong("ecommerce_shipping_payment"));
                insertQuery.addValue("ecommerce_payment_id = :ecommerce_payment_id", payment.getId());
                insertQuery.addValue("ecommerce_shipping_id = :ecommerce_shipping_id", this.ecommerceShippingId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        setResponsePage(ShippingBrowsePage.class);
    }

}