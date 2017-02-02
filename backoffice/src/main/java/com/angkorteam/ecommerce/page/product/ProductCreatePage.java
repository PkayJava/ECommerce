package com.angkorteam.ecommerce.page.product;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionMultipleChoiceProvider;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class ProductCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCreatePage.class);

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private List<Option> relatedProduct;
    private Select2MultipleChoice<Option> relatedProductField;
    private TextFeedbackPanel relatedProductFeedback;

    private Double normalPrice;
    private TextField<Double> normalPriceField;
    private TextFeedbackPanel normalPriceFeedback;

    private Option category;
    private Select2SingleChoice<Option> categoryField;
    private TextFeedbackPanel categoryFeedback;

    private Option brand;
    private Select2SingleChoice<Option> brandField;
    private TextFeedbackPanel brandFeedback;

    private Option color;
    private Select2SingleChoice<Option> colorField;
    private TextFeedbackPanel colorFeedback;

    private Option size;
    private Select2SingleChoice<Option> sizeField;
    private TextFeedbackPanel sizeFeedback;

    private Double discountPrice;
    private TextField<Double> discountPriceField;
    private TextFeedbackPanel discountPriceFeedback;

    private String reference;
    private TextField<String> referenceField;
    private TextFeedbackPanel referenceFeedback;

    private Integer quantity = 1;
    private TextField<Integer> quantityField;
    private TextFeedbackPanel quantityFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private List<FileUpload> mainImage;
    private FileUploadField mainImageField;
    private TextFeedbackPanel mainImageFeedback;

    private List<FileUpload> mainImageHighRes;
    private FileUploadField mainImageHighResField;
    private TextFeedbackPanel mainImageHighResFeedback;

    private List<FileUpload> variantImage;
    private MultiFileUploadField variantImageField;
    private TextFeedbackPanel variantImageFeedback;

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
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_product", "name"));
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.normalPriceField = new TextField<>("normalPriceField", new PropertyModel<>(this, "normalPrice"));
        this.normalPriceField.setRequired(true);
        this.form.add(this.normalPriceField);
        this.normalPriceFeedback = new TextFeedbackPanel("normalPriceFeedback", this.normalPriceField);
        this.form.add(this.normalPriceFeedback);

        this.categoryField = new Select2SingleChoice<>("categoryField", new PropertyModel<>(this, "category"), new OptionSingleChoiceProvider("ecommerce_category", "ecommerce_category_id", "name", "path"));
        this.categoryField.setRequired(true);
        this.form.add(this.categoryField);
        this.categoryFeedback = new TextFeedbackPanel("categoryFeedback", this.categoryField);
        this.form.add(this.categoryFeedback);

        this.brandField = new Select2SingleChoice<>("brandField", new PropertyModel<>(this, "brand"), new OptionSingleChoiceProvider("ecommerce_brand", "ecommerce_brand_id", "name"));
        this.form.add(this.brandField);
        this.brandFeedback = new TextFeedbackPanel("brandFeedback", this.brandField);
        this.form.add(this.brandFeedback);

        this.discountPriceField = new TextField<>("discountPriceField", new PropertyModel<>(this, "discountPrice"));
        this.form.add(this.discountPriceField);
        this.discountPriceFeedback = new TextFeedbackPanel("discountPriceFeedback", this.discountPriceField);
        this.form.add(this.discountPriceFeedback);

        this.reference = randomUUIDLong() + "";
        this.referenceField = new TextField<>("referenceField", new PropertyModel<>(this, "reference"));
        this.referenceField.add(new UniqueRecordValidator<>("ecommerce_product", "reference"));
        this.referenceField.setRequired(true);
        this.form.add(this.referenceField);
        this.referenceFeedback = new TextFeedbackPanel("referenceFeedback", this.referenceField);
        this.form.add(this.referenceFeedback);

        this.mainImageField = new FileUploadField("mainImageField", new PropertyModel<>(this, "mainImage"));
        this.mainImageField.setRequired(true);
        this.form.add(this.mainImageField);
        this.mainImageFeedback = new TextFeedbackPanel("mainImageFeedback", this.mainImageField);
        this.form.add(this.mainImageFeedback);

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

        this.quantityField = new TextField<>("quantityField", new PropertyModel<>(this, "quantity"));
        this.quantityField.setRequired(true);
        this.quantityField.add(RangeValidator.<Integer>minimum(1));
        this.form.add(this.quantityField);
        this.quantityFeedback = new TextFeedbackPanel("quantityFeedback", this.quantityField);
        this.form.add(this.quantityFeedback);

        this.mainImageHighResField = new FileUploadField("mainImageHighResField", new PropertyModel<>(this, "mainImageHighRes"));
        this.mainImageHighResField.setRequired(true);
        this.form.add(this.mainImageHighResField);
        this.mainImageHighResFeedback = new TextFeedbackPanel("mainImageHighResFeedback", this.mainImageHighResField);
        this.form.add(this.mainImageHighResFeedback);

        this.relatedProductField = new Select2MultipleChoice<>("relatedProductField", new PropertyModel<>(this, "relatedProduct"), new OptionMultipleChoiceProvider("ecommerce_product", "ecommerce_product_id", "name"));
        this.form.add(this.relatedProductField);
        this.relatedProductFeedback = new TextFeedbackPanel("relatedProductFeedback", this.relatedProductField);
        this.form.add(this.relatedProductFeedback);

        this.quantityField = new TextField<>("quantityField", new PropertyModel<>(this, "quantity"));
        this.quantityField.setRequired(true);
        this.quantityField.add(RangeValidator.<Integer>minimum(1));
        this.form.add(this.quantityField);
        this.quantityFeedback = new TextFeedbackPanel("quantityFeedback", this.quantityField);
        this.form.add(this.quantityFeedback);

        this.variantImageField = new MultiFileUploadField("variantImageField", new PropertyModel<>(this, "variantImage"));
        this.variantImageField.setRequired(true);
        this.form.add(this.variantImageField);
        this.variantImageFeedback = new TextFeedbackPanel("variantImageFeedback", this.variantImageField);
        this.form.add(this.variantImageFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ProductBrowsePage.class);
        this.form.add(this.closeButton);
    }


    private void saveButtonOnSubmit(Button button) {

        Long mainImageFileId = null;
        if (this.mainImage != null && !this.mainImage.isEmpty()) {
            File file = new File(FileUtils.getTempDirectory(), randomUUIDLong() + this.mainImage.get(0).getClientFileName());
            try {
                this.mainImage.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            mainImageFileId = Platform.saveFile(file);
            file.delete();
        }

        Long mainImageHighResFileId = null;
        if (this.mainImageHighRes != null && !this.mainImageHighRes.isEmpty()) {
            File file = new File(FileUtils.getTempDirectory(), randomUUIDLong() + this.mainImageHighRes.get(0).getClientFileName());
            try {
                this.mainImageHighRes.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            mainImageHighResFileId = Platform.saveFile(file);
            file.delete();
        }

        Long productId = randomUUIDLong();
        InsertQuery insertQuery = null;
        insertQuery = new InsertQuery("ecommerce_product");
        insertQuery.addValue("ecommerce_product_id = :ecommerce_product", productId);
        insertQuery.addValue("name = :name", this.name);

        if (this.discountPrice == null || this.discountPrice > this.normalPrice) {
            insertQuery.addValue("price = :price", this.normalPrice);
        } else {
            insertQuery.addValue("price = :price", this.discountPrice);
        }
        insertQuery.addValue("normal_price = :normal_price", this.normalPrice);
        insertQuery.addValue("ecommerce_category_id = :ecommerce_category_id", this.category.getId());
        insertQuery.addValue("user_id = :user_id", getSession().getUserId());

        if (this.brand != null) {
            insertQuery.addValue("ecommerce_brand_id = :ecommerce_brand_id", this.brand.getId());
        }
        insertQuery.addValue("reference = :reference", this.reference);
        insertQuery.addValue("description = :description", this.description);

        if (this.discountPrice != null) {
            insertQuery.addValue("discount_price = :discount_price", this.discountPrice);
        }
        insertQuery.addValue("main_image_file_id = :main_image_file_id", mainImageFileId);

        insertQuery.addValue("last_modified = :last_modified", new Date());
        insertQuery.addValue("popularity = :popularity", 0);
        insertQuery.addValue("main_image_high_res_file_id = :main_image_high_res_file_id", mainImageHighResFileId);
        insertQuery.addValue("quantity = :quantity", this.quantity);
        insertQuery.addValue("shipping_price = :shipping_price", 0);
        insertQuery.addValue("ready = :ready", false);

        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        if (this.relatedProduct != null && !this.relatedProduct.isEmpty()) {
            for (Option option : this.relatedProduct) {
                insertQuery = new InsertQuery("ecommerce_product_related");
                insertQuery.addValue("ecommerce_product_related_id = :ecommerce_product_related_id", randomUUIDLong());
                insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productId);
                insertQuery.addValue("related_ecommerce_product_id = :related_ecommerce_product_id", option.getId());
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }


        // insert variant

        Long variantId = randomUUIDLong();
        insertQuery = new InsertQuery("ecommerce_product_variant");
        insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant", variantId);
        insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productId);
        insertQuery.addValue("reference = :reference", randomUUIDLong());
        insertQuery.addValue("ecommerce_color_id = :ecommerce_color_id", this.color.getId());
        insertQuery.addValue("ecommerce_size_id = :ecommerce_size_id", this.size.getId());
        insertQuery.addValue("quantity = :quantity", this.quantity);

        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        SelectQuery selectQuery = new SelectQuery("ecommerce_product_variant");
        selectQuery.addField("sum(quantity)");
        selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", productId);
        Integer quantity = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);

        if (this.variantImage != null && !this.variantImage.isEmpty()) {
            for (FileUpload tmp : this.variantImage) {
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
                insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }


        setResponsePage(ProductBrowsePage.class);
    }

}