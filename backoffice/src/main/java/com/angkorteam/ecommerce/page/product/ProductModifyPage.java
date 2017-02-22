package com.angkorteam.ecommerce.page.product;

import com.angkorteam.ecommerce.model.EcommerceProduct;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.page.SettingPage;
import com.angkorteam.platform.provider.OptionMultipleChoiceProvider;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class ProductModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductModifyPage.class);

    private String ecommerceProductId;

    private List<Option> relatedProduct;
    private Select2MultipleChoice<Option> relatedProductField;
    private TextFeedbackPanel relatedProductFeedback;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Double normalPrice;
    private TextField<Double> normalPriceField;
    private TextFeedbackPanel normalPriceFeedback;

    private Option category;
    private Select2SingleChoice<Option> categoryField;
    private TextFeedbackPanel categoryFeedback;

    private Option brand;
    private Select2SingleChoice<Option> brandField;
    private TextFeedbackPanel brandFeedback;

    private Double discountPrice;
    private TextField<Double> discountPriceField;
    private TextFeedbackPanel discountPriceFeedback;

    private String reference;
    private Label referenceLabel;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private List<FileUpload> mainImage;
    private FileUploadField mainImageField;
    private TextFeedbackPanel mainImageFeedback;

    private List<FileUpload> mainImageHighRes;
    private FileUploadField mainImageHighResField;
    private TextFeedbackPanel mainImageHighResFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceProductId = getPageParameters().get("ecommerceProductId").toString("");

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_product");
        selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        EcommerceProduct ecommerceProduct = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProduct.class);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.name = ecommerceProduct.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_product", "name", "ecommerce_product_id", this.ecommerceProductId));
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.description = ecommerceProduct.getDescription();
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.normalPrice = ecommerceProduct.getNormalPrice();
        this.normalPriceField = new TextField<>("normalPriceField", new PropertyModel<>(this, "normalPrice"));
        this.normalPriceField.setRequired(true);
        this.form.add(this.normalPriceField);
        this.normalPriceFeedback = new TextFeedbackPanel("normalPriceFeedback", this.normalPriceField);
        this.form.add(this.normalPriceFeedback);

        selectQuery = new SelectQuery("ecommerce_category");
        selectQuery.addField("ecommerce_category_id AS id");
        selectQuery.addField("name AS text");
        selectQuery.addWhere("ecommerce_category_id = :ecommerce_category_id", ecommerceProduct.getEcommerceCategoryId());
        this.category = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
        OptionSingleChoiceProvider categoryOption = new OptionSingleChoiceProvider("ecommerce_category", "ecommerce_category_id", "name", "path");
        categoryOption.addWhere("enabled = true");
        this.categoryField = new Select2SingleChoice<>("categoryField", new PropertyModel<>(this, "category"), categoryOption);
        this.categoryField.setRequired(true);
        this.form.add(this.categoryField);
        this.categoryFeedback = new TextFeedbackPanel("categoryFeedback", this.categoryField);
        this.form.add(this.categoryFeedback);

        selectQuery = new SelectQuery("ecommerce_brand");
        selectQuery.addField("ecommerce_brand_id AS id");
        selectQuery.addField("name AS text");
        selectQuery.addWhere("ecommerce_brand_id = :ecommerce_brand_id", ecommerceProduct.getEcommerceBrandId());
        this.brand = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
        OptionSingleChoiceProvider brandOption = new OptionSingleChoiceProvider("ecommerce_brand", "ecommerce_brand_id", "name");
        brandOption.addWhere("enabled = true");
        this.brandField = new Select2SingleChoice<>("brandField", new PropertyModel<>(this, "brand"), brandOption);
        this.form.add(this.brandField);
        this.brandFeedback = new TextFeedbackPanel("brandFeedback", this.brandField);
        this.form.add(this.brandFeedback);

        this.discountPrice = ecommerceProduct.getDiscountPrice();
        this.discountPriceField = new TextField<>("discountPriceField", new PropertyModel<>(this, "discountPrice"));
        this.form.add(this.discountPriceField);
        this.discountPriceFeedback = new TextFeedbackPanel("discountPriceFeedback", this.discountPriceField);
        this.form.add(this.discountPriceFeedback);

        this.reference = ecommerceProduct.getReference();
        this.referenceLabel = new Label("referenceLabel", new PropertyModel<>(this, "reference"));
        this.form.add(this.referenceLabel);

        selectQuery = new SelectQuery("ecommerce_product_related");
        selectQuery.addField("ecommerce_product.ecommerce_product_id AS id");
        selectQuery.addField("ecommerce_product.name AS text");
        selectQuery.addJoin(JoinType.InnerJoin, "ecommerce_product", "ecommerce_product_related.ecommerce_product_id = ecommerce_product.ecommerce_product_id");
        selectQuery.addWhere("ecommerce_product_related.ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        this.relatedProduct = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);

        OptionMultipleChoiceProvider provider = new OptionMultipleChoiceProvider("ecommerce_product", "ecommerce_product_id", "name");
        provider.addWhere("ecommerce_product_id != '" + this.ecommerceProductId + "'");
        provider.addWhere("enabled = true");
        this.relatedProductField = new Select2MultipleChoice<>("relatedProductField", new PropertyModel<>(this, "relatedProduct"), provider);
        this.form.add(this.relatedProductField);
        this.relatedProductFeedback = new TextFeedbackPanel("relatedProductFeedback", this.relatedProductField);
        this.form.add(this.relatedProductFeedback);

        this.mainImageField = new FileUploadField("mainImageField", new PropertyModel<>(this, "mainImage"));
        this.form.add(this.mainImageField);
        this.mainImageFeedback = new TextFeedbackPanel("mainImageFeedback", this.mainImageField);
        this.form.add(this.mainImageFeedback);

        this.mainImageHighResField = new FileUploadField("mainImageHighResField", new PropertyModel<>(this, "mainImageHighRes"));
        this.form.add(this.mainImageHighResField);
        this.mainImageHighResFeedback = new TextFeedbackPanel("mainImageHighResFeedback", this.mainImageHighResField);
        this.form.add(this.mainImageHighResFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ProductBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        Long mainImageFileId = null;
        if (this.mainImage != null && !this.mainImage.isEmpty()) {
            File file = new File(FileUtils.getTempDirectory(), Platform.randomUUIDString() + this.mainImage.get(0).getClientFileName());
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
            File file = new File(FileUtils.getTempDirectory(), Platform.randomUUIDString() + this.mainImageHighRes.get(0).getClientFileName());
            try {
                this.mainImageHighRes.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            mainImageHighResFileId = Platform.saveFile(file);
            file.delete();
        }


        String brandId = null;
        if (this.brand != null) {
            brandId = this.brand.getId();
        }

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_product");
        updateQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        updateQuery.addValue("name = :name", this.name);

        if (this.discountPrice == null || this.discountPrice > this.normalPrice) {
            updateQuery.addValue("price = :price", this.normalPrice);
        } else {
            updateQuery.addValue("price = :price", this.discountPrice);
        }

        updateQuery.addValue("normal_price = :normal_price", this.normalPrice);
        updateQuery.addValue("ecommerce_category_id = :ecommerce_category_id", this.category.getId());
        updateQuery.addValue("ecommerce_brand_id = :ecommerce_brand_id", brandId);
        updateQuery.addValue("description = :description", this.description);
        updateQuery.addValue("last_modified = :last_modified", new Date());
        updateQuery.addValue("discount_price = :discount_price", this.discountPrice);
        if (mainImageFileId != null) {
            updateQuery.addValue("main_image_platform_file_id = :main_image_platform_file_id", mainImageFileId);
        }
        if (mainImageHighResFileId != null) {
            updateQuery.addValue("main_image_high_res_platform_file_id = :main_image_high_res_platform_file_id", mainImageHighResFileId);
        }
        String demoString = Platform.getSetting(SettingPage.DEMO);
        boolean demo = Strings.isNullOrEmpty(demoString) ? false : Boolean.valueOf(demoString);
        updateQuery.addValue("ready = :ready", demo);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        getJdbcTemplate().update("delete from ecommerce_product_related where ecommerce_product_id = ?", this.ecommerceProductId);

        if (this.relatedProduct != null && !this.relatedProduct.isEmpty()) {
            for (Option item : this.relatedProduct) {
                InsertQuery insertQuery = new InsertQuery("ecommerce_product_related");
                insertQuery.addValue("ecommerce_product_related_id = :ecommerce_product_related_id", Platform.randomUUIDLong("ecommerce_product_related"));
                insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
                insertQuery.addValue("related_ecommerce_product_id = :related_ecommerce_product_id", item.getId());
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }
        setResponsePage(ProductBrowsePage.class);
    }

}