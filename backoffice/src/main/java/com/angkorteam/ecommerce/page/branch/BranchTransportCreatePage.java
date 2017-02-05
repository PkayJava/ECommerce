package com.angkorteam.ecommerce.page.branch;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
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
 * Created by socheatkhauv on 24/1/17.
 */
public class BranchTransportCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchTransportCreatePage.class);

    private String ecommerceBranchId;

    private String text;
    private TextField<String> textField;
    private TextFeedbackPanel textFeedback;

    private List<FileUpload> icon;
    private FileUploadField iconField;
    private TextFeedbackPanel iconFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceBranchId = getPageParameters().get("ecommerceBranchId").toString("");

        this.form = new Form<>("form");
        layout.add(this.form);

        this.textField = new TextField<>("textField", new PropertyModel<>(this, "text"));
        this.textField.setRequired(true);
        this.form.add(this.textField);
        this.textFeedback = new TextFeedbackPanel("textFeedback", this.textField);
        this.form.add(this.textFeedback);

        this.iconField = new FileUploadField("iconField", new PropertyModel<>(this, "icon"));
        this.iconField.setRequired(true);
        this.form.add(this.iconField);
        this.iconFeedback = new TextFeedbackPanel("iconFeedback", this.iconField);
        this.form.add(this.iconFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", BranchBrowsePage.class);
        this.form.add(this.closeButton);
    }


    private void saveButtonOnSubmit(Button button) {
        Long iconFileId = null;
        if (this.icon != null && !this.icon.isEmpty()) {
            File file = new File(FileUtils.getTempDirectory(), Platform.randomUUIDString() + this.icon.get(0).getClientFileName());
            try {
                this.icon.get(0).writeTo(file);
            } catch (Exception e) {
                throw new WicketRuntimeException(e);
            }
            iconFileId = Platform.saveFile(file);
            file.delete();
        }

        InsertQuery insertQuery = new InsertQuery("ecommerce_branch_transport");
        insertQuery.addValue("ecommerce_branch_transport_id = :ecommerce_branch_transport_id", Platform.randomUUIDLong("ecommerce_branch_transport"));
        insertQuery.addValue("`text` = :text", this.text);
        insertQuery.addValue("ecommerce_branch_id = :ecommerce_branch_id", this.ecommerceBranchId);
        insertQuery.addValue("icon_platform_file_id = :icon_platform_file_id", iconFileId);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceBranchId", this.ecommerceBranchId);

        setResponsePage(BranchTransportBrowsePage.class, parameters);
    }

}