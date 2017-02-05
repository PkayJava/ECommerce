package com.angkorteam.ecommerce.page.brand;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
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
public class BrandCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandCreatePage.class);

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Integer order;
    private TextField<Integer> orderField;
    private TextFeedbackPanel orderFeedback;

    private BookmarkablePageLink<Void> closeButton;
    private Button saveButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_brand", "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.order = 1;
        this.orderField = new TextField<>("orderField", new PropertyModel<>(this, "order"));
        this.orderField.setRequired(true);
        this.form.add(this.orderField);
        this.orderFeedback = new TextFeedbackPanel("orderFeedback", this.orderField);
        this.form.add(this.orderFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", BrandBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        InsertQuery insertQuery = new InsertQuery("ecommerce_brand");
        insertQuery.addValue("ecommerce_brand_id = :ecommerce_brand_id", Platform.randomUUIDLong("ecommerce_brand"));
        insertQuery.addValue("`order` = :order", this.order);
        insertQuery.addValue("name = :name", this.name);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
        setResponsePage(BrandBrowsePage.class);
    }

}