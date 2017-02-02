package com.angkorteam.ecommerce.page.payment;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
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
public class PaymentCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentCreatePage.class);

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

        InsertQuery insertQuery = new InsertQuery("ecommerce_payment");
        insertQuery.addValue("ecommerce_payment_id = :ecommerce_payment_id", randomUUIDLong());
        insertQuery.addValue("name = :name", this.name);
        insertQuery.addValue("description = :description", this.description);
        insertQuery.addValue("price = :price", this.price);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        setResponsePage(PaymentBrowsePage.class);
    }

}