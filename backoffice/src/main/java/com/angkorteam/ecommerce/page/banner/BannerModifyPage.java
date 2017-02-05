package com.angkorteam.ecommerce.page.banner;

import com.angkorteam.ecommerce.model.EcommerceBanner;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by socheatkhauv on 22/1/17.
 */
public class BannerModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BannerModifyPage.class);

    String ecommerceBannerId;

    String name;
    TextField<String> nameField;
    TextFeedbackPanel nameFeedback;

    List<String> types = Arrays.asList("Category", "Product");
    String type;
    DropDownChoice<String> typeField;
    TextFeedbackPanel typeFeedback;

    Option product;
    Select2SingleChoice<Option> productField;
    TextFeedbackPanel productFeedback;

    Option category;
    Select2SingleChoice<Option> categoryField;
    TextFeedbackPanel categoryFeedback;

    List<FileUpload> image;
    FileUploadField imageField;
    TextFeedbackPanel imageFeedback;

    Integer order = 0;
    TextField<Integer> orderField;
    TextFeedbackPanel orderFeedback;

    Button saveButton;
    BookmarkablePageLink<Void> closeButton;
    Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceBannerId = getPageParameters().get("ecommerceBannerId").toString("");

        EcommerceBanner ecommerceBanner = getJdbcTemplate().queryForObject("select * from ecommerce_banner where ecommerce_banner_id = ?", EcommerceBanner.class, ecommerceBannerId);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.name = ecommerceBanner.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<String>(this, "name"));
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_banner", "name", "ecommerce_banner_id", this.ecommerceBannerId));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.type = ecommerceBanner.getType();
        this.typeField = new DropDownChoice<>("typeField", new PropertyModel<>(this, "type"), new PropertyModel<>(this, "types"));
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        this.product = getJdbcTemplate().queryForObject("select ecommerce_product_id id, name text from ecommerce_product where ecommerce_product_id = ?", Option.class, ecommerceBanner.getEcommerceProductId());
        this.productField = new Select2SingleChoice<>("productField", new PropertyModel<>(this, "product"), new OptionSingleChoiceProvider("ecommerce_product", "ecommerce_product_id", "name"));
        this.form.add(this.productField);
        this.productFeedback = new TextFeedbackPanel("productFeedback", this.productField);
        this.form.add(this.productFeedback);

        this.category = getJdbcTemplate().queryForObject("select ecommerce_category_id id, name text from ecommerce_category where ecommerce_category_id = ?", Option.class, ecommerceBanner.getEcommerceCategoryId());
        this.categoryField = new Select2SingleChoice<>("categoryField", new PropertyModel<>(this, "category"), new OptionSingleChoiceProvider("ecommerce_category", "ecommerce_category_id", "CONCAT(name, ( IF (parent_path = '', '', CONCAT(' : ', parent_path) )))"));
        this.form.add(this.categoryField);
        this.categoryFeedback = new TextFeedbackPanel("categoryFeedback", this.categoryField);
        this.form.add(this.categoryFeedback);

        this.imageField = new FileUploadField("imageField", new PropertyModel<>(this, "image"));
        this.imageField.setRequired(true);
        this.form.add(this.imageField);
        this.imageFeedback = new TextFeedbackPanel("imageFeedback", this.imageField);
        this.form.add(this.imageFeedback);

        this.order = ecommerceBanner.getOrder();
        this.orderField = new TextField<>("orderField", new PropertyModel<>(this, "order"));
        this.orderField.setRequired(true);
        this.form.add(this.orderField);
        this.orderFeedback = new TextFeedbackPanel("orderFeedback", this.orderField);
        this.form.add(this.orderFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", BannerBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        Long imageUrlFileId = null;
        if (this.image != null && !this.image.isEmpty()) {
            File file = new File(FileUtils.getTempDirectory(), randomUUIDLong() + this.image.get(0).getClientFileName());
            try {
                this.image.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            imageUrlFileId = Platform.saveFile(file);
            file.delete();
        }

        String productId = null;
        if (this.product != null) {
            productId = this.product.getId();
        }

        String categoryId = null;
        if (this.category != null) {
            categoryId = this.category.getId();
        }

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_banner");
        updateQuery.addValue("`order` = :order", this.order);
        updateQuery.addValue("name = :name", this.name);
        updateQuery.addValue("type = :type", this.type);
        updateQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productId);
        updateQuery.addValue("ecommerce_category_id = :ecommerce_category_id", categoryId);
        updateQuery.addValue("image_url_platform_file_id = :image_url_platform_file_id", imageUrlFileId);
        updateQuery.addWhere("ecommerce_banner_id = :ecommerce_banner_id", this.ecommerceBannerId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(BannerBrowsePage.class);
    }

}