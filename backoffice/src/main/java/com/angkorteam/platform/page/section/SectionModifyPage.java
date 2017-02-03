package com.angkorteam.platform.page.section;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.SectionTitleValidator;
import com.angkorteam.platform.model.PlatformSection;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by socheatkhauv on 10/26/16.
 */
public class SectionModifyPage extends MBaaSPage {

    private String sectionId;

    private String title;
    private TextField<String> titleField;
    private TextFeedbackPanel titleFeedback;

    private Integer order;
    private TextField<Integer> orderField;
    private TextFeedbackPanel orderFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        PageParameters parameters = getPageParameters();
        this.sectionId = parameters.get("sectionId").toString("");

        PlatformSection section = getJdbcTemplate().queryForObject("select * from platform_section where platform_section_id = ?", PlatformSection.class, this.sectionId);

        this.order = section.getOrder();
        this.title = section.getTitle();

        this.form = new Form<>("form");
        layout.add(this.form);

        this.titleField = new TextField<>("titleField", new PropertyModel<>(this, "title"));
        this.titleField.setRequired(true);
        this.titleField.add(new SectionTitleValidator(this.sectionId));
        this.form.add(this.titleField);
        this.titleFeedback = new TextFeedbackPanel("titleFeedback", this.titleField);
        this.form.add(this.titleFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.orderField = new TextField<>("orderField", new PropertyModel<>(this, "order"));
        this.orderField.setRequired(true);
        this.form.add(this.orderField);
        this.orderFeedback = new TextFeedbackPanel("orderFeedback", this.orderField);
        this.form.add(this.orderFeedback);

        this.closeButton = new BookmarkablePageLink<>("closeButton", SectionBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("platform_section");
        updateQuery.addValue("title = :title", this.title);
        updateQuery.addValue("`order` = :order", this.order);
        updateQuery.addWhere("platform_section_id = :platform_section_id", this.sectionId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(SectionBrowsePage.class);
    }

}
