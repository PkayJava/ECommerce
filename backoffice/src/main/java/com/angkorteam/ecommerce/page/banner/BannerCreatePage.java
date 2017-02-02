package com.angkorteam.ecommerce.page.banner;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
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
public class BannerCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BannerCreatePage.class);

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private List<String> types = Arrays.asList("Category", "Product");
    private String type;
    private DropDownChoice<String> typeField;
    private TextFeedbackPanel typeFeedback;

    private Option product;
    private Select2SingleChoice<Option> productField;
    private TextFeedbackPanel productFeedback;

    private Option category;
    private Select2SingleChoice<Option> categoryField;
    private TextFeedbackPanel categoryFeedback;

    private List<FileUpload> image;
    private FileUploadField imageField;
    private TextFeedbackPanel imageFeedback;

    private Integer order;
    private TextField<Integer> orderField;
    private TextFeedbackPanel orderFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.nameField = new TextField<>("nameField", new PropertyModel<String>(this, "name"));
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_banner", "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.typeField = new DropDownChoice<>("typeField", new PropertyModel<>(this, "type"), new PropertyModel<>(this, "types"));
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        this.productField = new Select2SingleChoice<>("productField", new PropertyModel<>(this, "product"), new OptionSingleChoiceProvider("ecommerce_product", "ecommerce_product_id", "name"));
        this.form.add(this.productField);
        this.productFeedback = new TextFeedbackPanel("productFeedback", this.productField);
        this.form.add(this.productFeedback);

        this.categoryField = new Select2SingleChoice<>("categoryField", new PropertyModel<>(this, "category"), new OptionSingleChoiceProvider("ecommerce_category", "ecommerce_category_id", "CONCAT(name, ( IF (parent_path = '', '', CONCAT(' : ', parent_path) )))"));
        this.form.add(this.categoryField);
        this.categoryFeedback = new TextFeedbackPanel("categoryFeedback", this.categoryField);
        this.form.add(this.categoryFeedback);

        this.imageField = new FileUploadField("imageField", new PropertyModel<>(this, "image"));
        this.imageField.setRequired(true);
        this.form.add(this.imageField);
        this.imageFeedback = new TextFeedbackPanel("imageFeedback", this.imageField);
        this.form.add(this.imageFeedback);

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
        if (this.image != null && !this.image.isEmpty())

        {
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

        InsertQuery insertQuery = new InsertQuery("ecommerce_banner");
        insertQuery.addValue("ecommerce_banner_id = :ecommerce_banner_id", randomUUIDInteger("ecommerce_banner"));
        insertQuery.addValue("name = :name", this.name);
        insertQuery.addValue("`order` = :order", this.order);
        insertQuery.addValue("type = :type", this.type);
        insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", productId);
        insertQuery.addValue("ecommerce_category_id = :ecommerce_category_id", categoryId);
        insertQuery.addValue("image_url_file_id = :image_url_file_id", imageUrlFileId);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
        setResponsePage(BannerBrowsePage.class);
    }

}