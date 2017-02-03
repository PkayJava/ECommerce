package com.angkorteam.ecommerce.page.color;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.ColorTextField;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by socheatkhauv on 22/1/17.
 */
public class ColorCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorCreatePage.class);

    private String code;
    private ColorTextField codeField;
    private TextFeedbackPanel codeFeedback;

    private String reference;
    private TextField<String> referenceField;
    private TextFeedbackPanel referenceFeedback;

    private String value;
    private TextField<String> valueField;
    private TextFeedbackPanel valueFeedback;

    private List<FileUpload> img;
    private FileUploadField imgField;
    private TextFeedbackPanel imgFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.reference = String.valueOf(randomUUIDLong());
        this.referenceField = new TextField<>("referenceField", new PropertyModel<>(this, "reference"));
        this.referenceField.add(new UniqueRecordValidator<>("ecommerce_color", "reference"));
        this.referenceField.setRequired(true);
        this.form.add(this.referenceField);
        this.referenceFeedback = new TextFeedbackPanel("referenceFeedback", this.referenceField);
        this.form.add(this.referenceFeedback);

        this.codeField = new ColorTextField("codeField", new PropertyModel<>(this, "code"));
        this.form.add(this.codeField);
        this.codeFeedback = new TextFeedbackPanel("codeFeedback", this.codeField);
        this.form.add(this.codeFeedback);

        this.valueField = new TextField<>("valueField", new PropertyModel<>(this, "value"));
        this.form.add(this.valueField);
        this.valueFeedback = new TextFeedbackPanel("valueFeedback", this.valueField);
        this.form.add(this.valueFeedback);

        this.imgField = new FileUploadField("imgField", new PropertyModel<>(this, "img"));
        this.form.add(this.imgField);
        this.imgFeedback = new TextFeedbackPanel("imgFeedback", this.imgField);
        this.form.add(this.imgFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ColorBrowsePage.class);
        this.form.add(this.closeButton);
    }


    private void saveButtonOnSubmit(Button button) {
        Long imgFileId = null;
        if (this.img != null && !this.img.isEmpty() && this.img.get(0).getSize() > 0) {
            File file = new File(FileUtils.getTempDirectory(), randomUUIDLong() + this.img.get(0).getClientFileName());
            try {
                this.img.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            imgFileId = Platform.saveFile(file);
            file.delete();
        }

        InsertQuery insertQuery = new InsertQuery("ecommerce_color");
        insertQuery.addValue("ecommerce_color_id = :ecommerce_color_id", Platform.randomUUIDLong());
        insertQuery.addValue("code = :code", this.code);
        insertQuery.addValue("reference = :reference", this.reference);
        insertQuery.addValue("value = :value", this.value);
        insertQuery.addValue("img_platform_file_id = :img_platform_file_id", imgFileId);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        setResponsePage(ColorBrowsePage.class);
    }

}