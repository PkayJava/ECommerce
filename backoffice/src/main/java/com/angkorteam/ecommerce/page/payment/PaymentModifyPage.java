package com.angkorteam.ecommerce.page.payment;

import com.angkorteam.ecommerce.model.ECommercePayment;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 24/1/17.
 */
public class PaymentModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentModifyPage.class);

    private String ecommercePaymentId;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Double price;
    private TextField<Double> priceField;
    private TextFeedbackPanel priceFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommercePaymentId = getPageParameters().get("ecommercePaymentId").toString("");

        SelectQuery selectQuery = new SelectQuery("ecommerce_payment");
        selectQuery.addWhere("ecommerce_payment_id = :ecommerce_payment_id", this.ecommercePaymentId);

        ECommercePayment record = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommercePayment.class);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.name = record.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.price = record.getPrice();
        this.priceField = new TextField<>("priceField", new PropertyModel<>(this, "price"));
        this.priceField.setRequired(true);
        this.form.add(this.priceField);
        this.priceFeedback = new TextFeedbackPanel("priceFeedback", this.priceField);
        this.form.add(this.priceFeedback);

        this.description = record.getDescription();
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", PaymentBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_payment");
        updateQuery.addWhere("ecommerce_payment_id = :ecommerce_payment_id", this.ecommercePaymentId);
        updateQuery.addValue("name = :name", this.name);
        updateQuery.addValue("description = :description", this.description);
        updateQuery.addValue("price = :price", this.price);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(PaymentBrowsePage.class);
    }

}