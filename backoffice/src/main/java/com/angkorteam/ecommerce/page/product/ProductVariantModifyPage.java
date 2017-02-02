package com.angkorteam.ecommerce.page.product;

import com.angkorteam.ecommerce.model.ECommerceProductVariant;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class ProductVariantModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductVariantModifyPage.class);

    String ecommerceProductId;
    String ecommerceProductVariantId;

    String reference;
    Label referenceLabel;

    Option color;
    Select2SingleChoice<Option> colorField;
    TextFeedbackPanel colorFeedback;

    Integer quantity;
    TextField<Integer> quantityField;
    TextFeedbackPanel quantityFeedback;

    Option size;
    Select2SingleChoice<Option> sizeField;
    TextFeedbackPanel sizeFeedback;

    Button saveButton;
    BookmarkablePageLink<Void> closeButton;
    Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceProductId = getPageParameters().get("productId").toString("");
        this.ecommerceProductVariantId = getPageParameters().get("productVariantId").toString("");

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_product_variant");
        selectQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", this.ecommerceProductVariantId);

        ECommerceProductVariant record = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), ECommerceProductVariant.class);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.reference = record.getReference();
        this.referenceLabel = new Label("referenceLabel", new PropertyModel<>(this, "reference"));
        this.form.add(this.referenceLabel);

        selectQuery = new SelectQuery("ecommerce_size");
        selectQuery.addField("ecommerce_size_id AS id", "CONCAT(value, ' -> ', reference) AS text");
        selectQuery.addWhere("ecommerce_size_id = :ecommerce_size_id", record.getECommerceSizeId());
        this.size = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);

        this.colorField = new Select2SingleChoice<>("colorField", new PropertyModel<>(this, "color"), new OptionSingleChoiceProvider("ecommerce_color", "ecommerce_color_id", "CONCAT(value, ' -> ', reference)"));
        this.colorField.setRequired(true);
        this.form.add(this.colorField);
        this.colorFeedback = new TextFeedbackPanel("colorFeedback", this.colorField);
        this.form.add(this.colorFeedback);

        selectQuery = new SelectQuery("ecommerce_color");
        selectQuery.addField("ecommerce_color_id AS id", "CONCAT(value, ' -> ', reference) AS text");
        selectQuery.addWhere("ecommerce_color_id = :ecommerce_color_id", record.getECommerceColorId());
        this.color = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);

        this.sizeField = new Select2SingleChoice<>("sizeField", new PropertyModel<>(this, "size"), new OptionSingleChoiceProvider("ecommerce_size", "ecommerce_size_id", "CONCAT(value, ' -> ', reference)"));
        this.sizeField.setRequired(true);
        this.form.add(this.sizeField);
        this.sizeFeedback = new TextFeedbackPanel("sizeFeedback", this.sizeField);
        this.form.add(this.sizeFeedback);

        this.quantity = record.getQuantity();
        this.quantityField = new TextField<>("quantityField", new PropertyModel<>(this, "quantity"));
        this.quantityField.setRequired(true);
        this.quantityField.add(RangeValidator.<Integer>minimum(1));
        this.form.add(this.quantityField);
        this.quantityFeedback = new TextFeedbackPanel("quantityFeedback", this.quantityField);
        this.form.add(this.quantityFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ProductBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {

        UpdateQuery updateQuery = null;
        updateQuery = new UpdateQuery("ecommerce_product_variant");
        updateQuery.addValue("ecommerce_color_id = :ecommerce_color_id", this.color.getId());
        updateQuery.addValue("ecommerce_size_id = :ecommerce_size_id", this.size.getId());
        updateQuery.addValue("quantity = :quantity", this.quantity);
        updateQuery.addWhere("ecommerce_product_variant_id = :ecommerce_product_variant_id", this.ecommerceProductVariantId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        updateQuery = new UpdateQuery("ecommerce_product");
        updateQuery.addValue("quantity = :quantity", quantity);
        updateQuery.addValue("ready = :ready", false);
        updateQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceProductId", this.ecommerceProductId);
        setResponsePage(ProductVariantBrowsePage.class, parameters);
    }

}