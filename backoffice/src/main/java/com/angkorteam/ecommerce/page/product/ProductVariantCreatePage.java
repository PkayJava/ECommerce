package com.angkorteam.ecommerce.page.product;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class ProductVariantCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductVariantCreatePage.class);

    private String ecommerceProductId;

    private String reference;
    private TextField<String> referenceField;
    private TextFeedbackPanel referenceFeedback;

    private Integer quantity = 1;
    private TextField<Integer> quantityField;
    private TextFeedbackPanel quantityFeedback;

    private Option color;
    private Select2SingleChoice<Option> colorField;
    private TextFeedbackPanel colorFeedback;

    private Option size;
    private Select2SingleChoice<Option> sizeField;
    private TextFeedbackPanel sizeFeedback;

    private List<FileUpload> image;
    private MultiFileUploadField imageField;
    private TextFeedbackPanel imageFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceProductId = getPageParameters().get("ecommerceProductId").toString("");

        this.form = new Form<>("form");
        layout.add(this.form);

        this.reference = randomUUIDLong() + "";
        this.referenceField = new TextField<>("referenceField", new PropertyModel<>(this, "reference"));
        this.referenceField.setRequired(true);
        this.referenceField.add(new UniqueRecordValidator<>("ecommerce_product_variant", "reference"));
        this.form.add(this.referenceField);
        this.referenceFeedback = new TextFeedbackPanel("referenceFeedback", this.referenceField);
        this.form.add(this.referenceFeedback);

        this.quantityField = new TextField<>("quantityField", new PropertyModel<>(this, "quantity"));
        this.quantityField.setRequired(true);
        this.quantityField.add(RangeValidator.<Integer>minimum(1));
        this.form.add(this.quantityField);
        this.quantityFeedback = new TextFeedbackPanel("quantityFeedback", this.quantityField);
        this.form.add(this.quantityFeedback);

        this.colorField = new Select2SingleChoice<>("colorField", new PropertyModel<>(this, "color"), new OptionSingleChoiceProvider("ecommerce_color", "ecommerce_color_id", "CONCAT(value, ' -> ', reference)"));
        this.colorField.setRequired(true);
        this.form.add(this.colorField);
        this.colorFeedback = new TextFeedbackPanel("colorFeedback", this.colorField);
        this.form.add(this.colorFeedback);

        this.sizeField = new Select2SingleChoice<>("sizeField", new PropertyModel<>(this, "size"), new OptionSingleChoiceProvider("ecommerce_size", "ecommerce_size_id", "CONCAT(value, ' -> ', reference)"));
        this.sizeField.setRequired(true);
        this.form.add(this.sizeField);
        this.sizeFeedback = new TextFeedbackPanel("sizeFeedback", this.sizeField);
        this.form.add(this.sizeFeedback);

        this.imageField = new MultiFileUploadField("imageField", new PropertyModel<>(this, "image"));
        this.imageField.setRequired(true);
        this.form.add(this.imageField);
        this.imageFeedback = new TextFeedbackPanel("imageFeedback", this.imageField);
        this.form.add(this.imageFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ProductBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {

        Long variantId = randomUUIDLong();
        InsertQuery insertQuery = new InsertQuery("ecommerce_product_variant");
        insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant", variantId);
        insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        insertQuery.addValue("reference = :reference", this.reference);
        insertQuery.addValue("ecommerce_color_id = :ecommerce_color_id", this.color.getId());
        insertQuery.addValue("ecommerce_size_id = :ecommerce_size_id", this.size.getId());
        insertQuery.addValue("quantity = :quantity", this.quantity);

        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        SelectQuery selectQuery = new SelectQuery("ecommerce_product_variant");
        selectQuery.addField("sum(quantity)");
        selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        Integer quantity = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);

        if (this.image != null && !this.image.isEmpty()) {
            for (FileUpload tmp : this.image) {
                Long imageFileId = null;

                File file = new File(FileUtils.getTempDirectory(), randomUUIDLong() + tmp.getClientFileName());
                try {
                    tmp.writeTo(file);
                } catch (Exception e) {
                    throw new WicketRuntimeException(e);
                }
                imageFileId = Platform.saveFile(file);
                file.delete();

                insertQuery = new InsertQuery("ecommerce_product_variant_image");
                insertQuery.addValue("ecommerce_product_variant_image_id = :ecommerce_product_variant_image", randomUUIDLong());
                insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant_id", variantId);
                insertQuery.addValue("name = :name", randomUUIDLong());
                insertQuery.addValue("file_id = :file_id", imageFileId);
                insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_product");
        updateQuery.addValue("quantity = :quantity", quantity);
        updateQuery.addValue("ready = :ready", false);
        updateQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceProductId", this.ecommerceProductId);

        setResponsePage(ProductVariantBrowsePage.class, parameters);
    }

}