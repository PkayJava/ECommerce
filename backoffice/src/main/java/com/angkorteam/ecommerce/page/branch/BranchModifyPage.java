package com.angkorteam.ecommerce.page.branch;

import com.angkorteam.ecommerce.model.ECommerceBranch;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 24/1/17.
 */
public class BranchModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchModifyPage.class);

    private String ecommerceBranchId;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String address;
    private TextField<String> addressField;
    private TextFeedbackPanel addressFeedback;

    private String note;
    private TextField<String> noteField;
    private TextFeedbackPanel noteFeedback;

    private Double longitude;
    private TextField<Double> longitudeField;
    private TextFeedbackPanel longitudeFeedback;

    private Double latitude;
    private TextField<Double> latitudeField;
    private TextFeedbackPanel latitudeFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);
        this.ecommerceBranchId = getPageParameters().get("ecommerceBranchId").toString("");

        ECommerceBranch ecommerceBranch = getJdbcTemplate().queryForObject("select * from ecommerce_branch where ecommerce_branch_id = ?", ECommerceBranch.class, this.ecommerceBranchId);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.name = ecommerceBranch.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.add(new UniqueRecordValidator<>("ecommerce_branch", "name", "ecommerce_branch_id", this.ecommerceBranchId));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.address = ecommerceBranch.getAddress();
        this.addressField = new TextField<>("addressField", new PropertyModel<>(this, "address"));
        this.addressField.setRequired(true);
        this.form.add(this.addressField);
        this.addressFeedback = new TextFeedbackPanel("addressFeedback", this.addressField);
        this.form.add(this.addressFeedback);

        this.note = ecommerceBranch.getNote();
        this.noteField = new TextField<>("noteField", new PropertyModel<>(this, "note"));
        this.noteField.setRequired(true);
        this.form.add(this.noteField);
        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
        this.form.add(this.noteFeedback);

        this.latitude = ecommerceBranch.getLatitude();
        this.latitudeField = new TextField<>("latitudeField", new PropertyModel<>(this, "latitude"));
        this.latitudeField.setRequired(true);
        this.form.add(this.latitudeField);
        this.latitudeFeedback = new TextFeedbackPanel("latitudeFeedback", this.latitudeField);
        this.form.add(this.latitudeFeedback);

        this.longitude = ecommerceBranch.getLongitude();
        this.longitudeField = new TextField<>("longitudeField", new PropertyModel<>(this, "longitude"));
        this.longitudeField.setRequired(true);
        this.form.add(this.longitudeField);
        this.longitudeFeedback = new TextFeedbackPanel("longitudeFeedback", this.longitudeField);
        this.form.add(this.longitudeFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", BranchBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("ecommerce_branch");
        updateQuery.addValue("name = :name", this.name);
        updateQuery.addValue("address = :address", this.address);
        updateQuery.addValue("note = :note", this.note);
        updateQuery.addValue("longitude = :longitude", this.longitude);
        updateQuery.addValue("latitude = :latitude", this.latitude);
        updateQuery.addWhere("ecommerce_branch_id = :ecommerce_branch_id", this.ecommerceBranchId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(BranchBrowsePage.class);
    }

}