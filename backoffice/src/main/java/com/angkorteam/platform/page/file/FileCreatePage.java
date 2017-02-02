package com.angkorteam.platform.page.file;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.platform.Configuration;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by socheat on 3/11/16.
 */
public class FileCreatePage extends MBaaSPage {

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private List<FileUpload> file;
    private FileUploadField fileField;
    private TextFeedbackPanel fileFeedback;

    private Button saveButton;
    private Form<Void> form;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.nameField.setLabel(Model.of("name"));
        this.form.add(nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(nameFeedback);

        this.fileField = new FileUploadField("fileField", new PropertyModel<>(this, "file"));
        this.fileField.setRequired(true);
        this.form.add(this.fileField);
        this.fileFeedback = new TextFeedbackPanel("fileFeedback", this.fileField);
        this.form.add(fileFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", FileBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        FileUpload file = this.file.get(0);

        XMLPropertiesConfiguration configuration = Spring.getBean(XMLPropertiesConfiguration.class);

        String patternFolder = configuration.getString(Configuration.PATTERN_FOLDER);

        String repo = configuration.getString(Configuration.RESOURCE_REPO);

        String fileRepo = DateFormatUtils.format(new Date(), patternFolder);
        File container = new File(repo, fileRepo);
        String extension = StringUtils.lowerCase(FilenameUtils.getExtension(file.getClientFileName()));


        Long fileId = Platform.randomUUIDLong();
        String name = fileId + "_" + this.name + "." + extension;
        container.mkdirs();
        File fn = new File(container, name);
        try {
            file.writeTo(new File(container, name));
        } catch (Exception e) {
        }

        Platform.saveFile(fn);

        setResponsePage(FileBrowsePage.class);
    }
}
