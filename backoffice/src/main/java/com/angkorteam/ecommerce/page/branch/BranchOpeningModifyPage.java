package com.angkorteam.ecommerce.page.branch;

import com.angkorteam.ecommerce.model.EcommerceBranchOpening;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
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
public class BranchOpeningModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchOpeningModifyPage.class);

    private String ecommerceBranchId;

    private String ecommerceBranchOpeningId;

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
        this.ecommerceBranchOpeningId = getPageParameters().get("branchOpeningId").toString("");

        EcommerceBranchOpening record = getJdbcTemplate().queryForObject("select * from ecommerce_branch_opening where ecommerce_branch_opening_id = ?", EcommerceBranchOpening.class, this.ecommerceBranchOpeningId);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.day = record.getDay();
        this.dayField = new TextField<>("dayField", new PropertyModel<>(this, "day"));
        this.dayField.setRequired(true);
        this.form.add(this.dayField);
        this.dayFeedback = new TextFeedbackPanel("dayFeedback", this.dayField);
        this.form.add(this.dayFeedback);

        this.opening = record.getOpening();
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
        UpdateQuery updateQuery = new UpdateQuery("ecommerce_branch_opening");
        updateQuery.addValue("day = :day", this.day);
        updateQuery.addValue("opening = :opening", this.opening);
        updateQuery.addWhere("ecommerce_branch_opening_id = :ecommerce_branch_opening_id", this.ecommerceBranchOpeningId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceBranchId", this.ecommerceBranchId);
        setResponsePage(BranchOpeningBrowsePage.class, parameters);
    }

}