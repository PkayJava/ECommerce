package com.angkorteam.ecommerce.page.category;

import com.angkorteam.ecommerce.model.ECommerceCategory;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.google.common.base.Strings;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class CategoryModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryModifyPage.class);

    private String ecommerceCategoryId;

    private String parentPath;
    private String parentEntity;
    private Label parentLabel;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String code;
    private Label codeLabel;

    private Integer order = 0;
    private TextField<Integer> orderField;
    private TextFeedbackPanel orderFeedback;

    private BookmarkablePageLink<Void> closeButton;
    private Button saveButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.ecommerceCategoryId = getPageParameters().get("ecommerceCategoryId").toString("");

        ECommerceCategory categoryRecord = getJdbcTemplate().queryForObject("select * from ecommerce_category where ecommerce_category_id = ?", ECommerceCategory.class, this.ecommerceCategoryId);

        ECommerceCategory parentCategory = null;
        if (categoryRecord.getParentECommerceCategoryId() != null) {
            parentCategory = getJdbcTemplate().queryForObject("select * from ecommerce_category where ecommerce_category_id = ?", ECommerceCategory.class, categoryRecord.getParentECommerceCategoryId());
        }
        if (parentCategory != null) {
            this.parentEntity = parentCategory.getPath();
            this.parentPath = parentCategory.getPath();
        }
        this.parentLabel = new Label("parentLabel", new PropertyModel<>(this, "parentEntity"));
        this.form.add(this.parentLabel);

        this.name = categoryRecord.getName();
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.code = categoryRecord.getCode();
        this.codeLabel = new Label("codeLabel", new PropertyModel<>(this, "code"));
        this.form.add(this.codeLabel);

        this.order = categoryRecord.getOrder();
        this.orderField = new TextField<>("orderField", new PropertyModel<>(this, "order"));
        this.orderField.setRequired(true);
        this.form.add(this.orderField);
        this.orderFeedback = new TextFeedbackPanel("orderFeedback", this.orderField);
        this.form.add(this.orderFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", CategoryBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        String path = this.name;
        if (!Strings.isNullOrEmpty(this.parentPath)) {
            path = this.parentPath + " > " + this.name;
        }
        UpdateQuery updateQuery = new UpdateQuery("ecommerce_category");
        updateQuery.addValue("`order` = :order", this.order);
        updateQuery.addValue("name = :name", this.name);
        updateQuery.addValue("path = :path", path);
        updateQuery.addWhere("ecommerce_category_id = :ecommerce_category_id", this.ecommerceCategoryId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(CategoryBrowsePage.class);
    }

}