package com.angkorteam.ecommerce.page.product;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.page.SettingPage;
import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class ProductVariantGalleryCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductVariantGalleryCreatePage.class);

    String ecommerceProductId;
    String ecommerceProductVariantId;

    String name;
    TextField<String> nameField;
    TextFeedbackPanel nameFeedback;

    List<FileUpload> image;
    FileUploadField imageField;
    TextFeedbackPanel imageFeedback;

    Button saveButton;
    BookmarkablePageLink<Void> closeButton;
    Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceProductId = getPageParameters().get("ecommerceProductId").toString("");
        this.ecommerceProductVariantId = getPageParameters().get("ecommerceProductVariantId").toString("");

        this.form = new Form<>("form");
        layout.add(this.form);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.imageField = new FileUploadField("imageField", new PropertyModel<>(this, "image"));
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

        Long imageFileId = null;
        if (this.image != null && !this.image.isEmpty()) {
            File file = new File(FileUtils.getTempDirectory(), Platform.randomUUIDString() + this.image.get(0).getClientFileName());
            try {
                this.image.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            imageFileId = Platform.saveFile(file);
            file.delete();
        }

        InsertQuery insertQuery = new InsertQuery("ecommerce_product_variant_image");
        insertQuery.addValue("ecommerce_product_variant_image_id = :ecommerce_product_variant_image_id", Platform.randomUUIDLong("ecommerce_product_variant_image"));
        insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant_id", this.ecommerceProductVariantId);
        insertQuery.addValue("name = :name", name);
        insertQuery.addValue("platform_file_id = :platform_file_id", imageFileId);
        insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_product");
        String demoString = Platform.getSetting(SettingPage.DEMO);
        boolean demo = Strings.isNullOrEmpty(demoString) ? false : Boolean.valueOf(demoString);
        updateQuery.addValue("ready = :ready", demo);
        updateQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceProductId", this.ecommerceProductId);
        parameters.add("ecommerceProductVariantId", this.ecommerceProductVariantId);

        setResponsePage(ProductVariantGalleryBrowsePage.class, parameters);
    }

}