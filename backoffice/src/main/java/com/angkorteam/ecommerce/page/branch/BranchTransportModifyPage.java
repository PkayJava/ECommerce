package com.angkorteam.ecommerce.page.branch;

import com.angkorteam.ecommerce.model.EcommerceBranchTransport;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
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
public class BranchTransportModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchTransportModifyPage.class);

    private String ecommerceBranchId;
    private String ecommerceBranchTransportId;

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
        this.ecommerceBranchTransportId = getPageParameters().get("ecommerceBranchTransportId").toString("");

        EcommerceBranchTransport record = getJdbcTemplate().queryForObject("select * from ecommerce_branch_transport where ecommerce_branch_transport_id = ?", EcommerceBranchTransport.class, this.ecommerceBranchTransportId);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.text = record.getText();
        this.textField = new TextField<>("textField", new PropertyModel<>(this, "text"));
        this.textField.setRequired(true);
        this.form.add(this.textField);
        this.textFeedback = new TextFeedbackPanel("textFeedback", this.textField);
        this.form.add(this.textFeedback);

        this.iconField = new FileUploadField("iconField", new PropertyModel<>(this, "icon"));
        this.form.add(this.iconField);
        this.iconFeedback = new TextFeedbackPanel("iconFeedback", this.iconField);
        this.form.add(this.iconFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<Void>("closeButton", BranchBrowsePage.class);
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

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_branch_transport");
        updateQuery.addValue("`text` = :text", this.text);
        if (iconFileId != null) {
            updateQuery.addValue("icon_platform_file_id = :icon_platform_file_id", iconFileId);
        }
        updateQuery.addWhere("ecommerce_branch_transport_id = :ecommerce_branch_transport_id", iconFileId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceBranchId", this.ecommerceBranchId);

        setResponsePage(BranchTransportBrowsePage.class, parameters);
    }

}