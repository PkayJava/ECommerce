package com.angkorteam.ecommerce.page.branch;

import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 24/1/17.
 */
public class BranchOpeningCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchOpeningCreatePage.class);

    private String ecommerceBranchId;

    private String day;
    private TextField<String> dayField;
    private TextFeedbackPanel dayFeedback;

    private String opening;
    private TextArea<String> openingField;
    private TextFeedbackPanel openingFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceBranchId = getPageParameters().get("ecommerceBranchId").toString("");

        this.form = new Form<>("form");
        layout.add(this.form);

        this.dayField = new TextField<>("dayField", new PropertyModel<>(this, "day"));
        this.dayField.setRequired(true);
        this.form.add(this.dayField);
        this.dayFeedback = new TextFeedbackPanel("dayFeedback", this.dayField);
        this.form.add(this.dayFeedback);

        this.openingField = new TextArea<>("openingField", new PropertyModel<>(this, "opening"));
        this.openingField.setRequired(true);
        this.form.add(this.openingField);
        this.openingFeedback = new TextFeedbackPanel("openingFeedback", this.openingField);
        this.form.add(this.openingFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", BranchBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        InsertQuery insertQuery = new InsertQuery("ecommerce_branch_opening");
        insertQuery.addValue("ecommerce_branch_opening_id = :ecommerce_branch_opening_id", Platform.randomUUIDLong("ecommerce_branch_opening"));
        insertQuery.addValue("ecommerce_branch_id = :ecommerce_branch_id", this.ecommerceBranchId);
        insertQuery.addValue("`day` = :day", this.day);
        insertQuery.addValue("opening = :opening", this.opening);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceBranchId", this.ecommerceBranchId);
        setResponsePage(BranchOpeningBrowsePage.class, parameters);
    }

}