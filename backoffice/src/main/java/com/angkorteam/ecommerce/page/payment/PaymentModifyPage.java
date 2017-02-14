package com.angkorteam.ecommerce.page.payment;

import com.angkorteam.ecommerce.mobile.delivery.Payment;
import com.angkorteam.ecommerce.model.EcommercePayment;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
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

    private String type;
    private DropDownChoice<String> typeField;
    private TextFeedbackPanel typeFeedback;

    private String clientParam1;
    private TextField<String> clientParam1Field;
    private TextFeedbackPanel clientParam1Feedback;

    private String clientParam2;
    private TextField<String> clientParam2Field;
    private TextFeedbackPanel clientParam2Feedback;

    private String clientParam3;
    private TextField<String> clientParam3Field;
    private TextFeedbackPanel clientParam3Feedback;

    private String clientParam4;
    private TextField<String> clientParam4Field;
    private TextFeedbackPanel clientParam4Feedback;

    private String clientParam5;
    private TextField<String> clientParam5Field;
    private TextFeedbackPanel clientParam5Feedback;

    private String serverParam1;
    private TextField<String> serverParam1Field;
    private TextFeedbackPanel serverParam1Feedback;

    private String serverParam2;
    private TextField<String> serverParam2Field;
    private TextFeedbackPanel serverParam2Feedback;

    private String serverParam3;
    private TextField<String> serverParam3Field;
    private TextFeedbackPanel serverParam3Feedback;

    private String serverParam4;
    private TextField<String> serverParam4Field;
    private TextFeedbackPanel serverParam4Feedback;

    private String serverParam5;
    private TextField<String> serverParam5Field;
    private TextFeedbackPanel serverParam5Feedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommercePaymentId = getPageParameters().get("ecommercePaymentId").toString("");

        SelectQuery selectQuery = new SelectQuery("ecommerce_payment");
        selectQuery.addWhere("ecommerce_payment_id = :ecommerce_payment_id", this.ecommercePaymentId);

        EcommercePayment record = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommercePayment.class);

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

        this.type = record.getType();
        List<String> types = Arrays.asList(Payment.TYPE_CASH, Payment.TYPE_PAYPAL, Payment.TYPE_BRAIN_TREE);
        this.typeField = new DropDownChoice<>("typeField", new PropertyModel<>(this, "type"), types);
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        this.serverParam1 = record.getServerParam1();
        this.serverParam1Field = new TextField<>("serverParam1Field", new PropertyModel<>(this, "serverParam1"));
        this.form.add(this.serverParam1Field);
        this.serverParam1Feedback = new TextFeedbackPanel("serverParam1Feedback", this.serverParam1Field);
        this.form.add(this.serverParam1Feedback);

        this.serverParam2 = record.getServerParam2();
        this.serverParam2Field = new TextField<>("serverParam2Field", new PropertyModel<>(this, "serverParam2"));
        this.form.add(this.serverParam2Field);
        this.serverParam2Feedback = new TextFeedbackPanel("serverParam2Feedback", this.serverParam2Field);
        this.form.add(this.serverParam2Feedback);

        this.serverParam3 = record.getServerParam3();
        this.serverParam3Field = new TextField<>("serverParam3Field", new PropertyModel<>(this, "serverParam3"));
        this.form.add(this.serverParam3Field);
        this.serverParam3Feedback = new TextFeedbackPanel("serverParam3Feedback", this.serverParam3Field);
        this.form.add(this.serverParam3Feedback);

        this.serverParam4 = record.getServerParam4();
        this.serverParam4Field = new TextField<>("serverParam4Field", new PropertyModel<>(this, "serverParam4"));
        this.form.add(this.serverParam4Field);
        this.serverParam4Feedback = new TextFeedbackPanel("serverParam4Feedback", this.serverParam4Field);
        this.form.add(this.serverParam4Feedback);

        this.serverParam5 = record.getServerParam5();
        this.serverParam5Field = new TextField<>("serverParam5Field", new PropertyModel<>(this, "serverParam5"));
        this.form.add(this.serverParam5Field);
        this.serverParam5Feedback = new TextFeedbackPanel("serverParam5Feedback", this.serverParam5Field);
        this.form.add(this.serverParam5Feedback);

        this.clientParam1 = record.getClientParam1();
        this.clientParam1Field = new TextField<>("clientParam1Field", new PropertyModel<>(this, "clientParam1"));
        this.form.add(this.clientParam1Field);
        this.clientParam1Feedback = new TextFeedbackPanel("clientParam1Feedback", this.clientParam1Field);
        this.form.add(this.clientParam1Feedback);

        this.clientParam2 = record.getClientParam2();
        this.clientParam2Field = new TextField<>("clientParam2Field", new PropertyModel<>(this, "clientParam2"));
        this.form.add(this.clientParam2Field);
        this.clientParam2Feedback = new TextFeedbackPanel("clientParam2Feedback", this.clientParam2Field);
        this.form.add(this.clientParam2Feedback);

        this.clientParam3 = record.getClientParam3();
        this.clientParam3Field = new TextField<>("clientParam3Field", new PropertyModel<>(this, "clientParam3"));
        this.form.add(this.clientParam3Field);
        this.clientParam3Feedback = new TextFeedbackPanel("clientParam3Feedback", this.clientParam3Field);
        this.form.add(this.clientParam3Feedback);

        this.clientParam4 = record.getClientParam4();
        this.clientParam4Field = new TextField<>("clientParam4Field", new PropertyModel<>(this, "clientParam4"));
        this.form.add(this.clientParam4Field);
        this.clientParam4Feedback = new TextFeedbackPanel("clientParam4Feedback", this.clientParam4Field);
        this.form.add(this.clientParam4Feedback);

        this.clientParam5 = record.getClientParam5();
        this.clientParam5Field = new TextField<>("clientParam5Field", new PropertyModel<>(this, "clientParam5"));
        this.form.add(this.clientParam5Field);
        this.clientParam5Feedback = new TextFeedbackPanel("clientParam5Feedback", this.clientParam5Field);
        this.form.add(this.clientParam5Feedback);

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
        updateQuery.addValue("type = :type", this.type);
        updateQuery.addValue("server_param1 = :server_param1", this.serverParam1);
        updateQuery.addValue("server_param2 = :server_param2", this.serverParam2);
        updateQuery.addValue("server_param3 = :server_param3", this.serverParam3);
        updateQuery.addValue("server_param4 = :server_param4", this.serverParam4);
        updateQuery.addValue("server_param5 = :server_param5", this.serverParam5);
        updateQuery.addValue("client_param1 = :client_param1", this.clientParam1);
        updateQuery.addValue("client_param2 = :client_param2", this.clientParam2);
        updateQuery.addValue("client_param3 = :client_param3", this.clientParam3);
        updateQuery.addValue("client_param4 = :client_param4", this.clientParam4);
        updateQuery.addValue("client_param5 = :client_param5", this.clientParam5);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(PaymentBrowsePage.class);
    }

}