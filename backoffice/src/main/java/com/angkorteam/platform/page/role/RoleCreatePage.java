package com.angkorteam.platform.page.role;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionMultipleChoiceProvider;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

/**
 * Created by socheat on 10/24/16.
 */
public class RoleCreatePage extends MBaaSPage {

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String description;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private List<Option> pageValue;
    private Select2MultipleChoice<Option> pageField;
    private TextFeedbackPanel pageFeedback;

    private List<Option> rest;
    private Select2MultipleChoice<Option> restField;
    private TextFeedbackPanel restFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.pageField = new Select2MultipleChoice<>("pageField", new PropertyModel<>(this, "pageValue"), new OptionMultipleChoiceProvider("page", "page_id", "page_title"));
        this.form.add(this.pageField);
        this.pageFeedback = new TextFeedbackPanel("pageFeedback", this.pageField);
        this.form.add(this.pageFeedback);

        this.restField = new Select2MultipleChoice<>("restField", new PropertyModel<>(this, "rest"), new OptionMultipleChoiceProvider("rest", "rest_id", "name"));
        this.form.add(this.restField);
        this.restFeedback = new TextFeedbackPanel("restFeedback", this.restField);
        this.form.add(this.restFeedback);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "description"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", RoleBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        Long roleId = randomUUIDLong();
        InsertQuery insertQuery = null;
        insertQuery = new InsertQuery("platform_role");
        insertQuery.addValue("platform_role_id = :role_id", roleId);
        insertQuery.addValue("name = :name", this.name);
        insertQuery.addValue("description = :description", this.description);
        insertQuery.addValue("version = :version", 1);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        if (this.pageValue != null && !this.pageValue.isEmpty()) {
            for (Option page : this.pageValue) {
                insertQuery = new InsertQuery("platform_page_role");
                insertQuery.addValue("platform_page_role_id = :page_role_id", randomUUIDLong());
                insertQuery.addValue("platform_page_id = :page_id", page.getId());
                insertQuery.addValue("platform_role_id = :role_id", roleId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        if (this.rest != null && !this.rest.isEmpty()) {
            for (Option rest : this.rest) {
                insertQuery = new InsertQuery("platform_rest_role");
                insertQuery.addValue("platform_rest_role_id = :rest_role_id", randomUUIDLong());
                insertQuery.addValue("platform_rest_id = :rest_id", rest.getId());
                insertQuery.addValue("platform_role_id = :role_id", roleId);
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            }
        }

        setResponsePage(RoleBrowsePage.class);
    }

}
