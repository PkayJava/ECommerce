package com.angkorteam.ecommerce.page.category;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.OptionSingleChoiceProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class CategoryCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryCreatePage.class);

    private Option parentEntity;
    private Select2SingleChoice<Option> parentField;
    private TextFeedbackPanel parentFeedback;

    private String name;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private String code;
    private TextField<String> codeField;
    private TextFeedbackPanel codeFeedback;

    private Integer order;
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

        this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentEntity"), new OptionSingleChoiceProvider("ecommerce_category", "ecommerce_category_id", "name", "path"));
        this.form.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.form.add(this.parentFeedback);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "name"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.code = StringUtils.upperCase(RandomStringUtils.randomAlphabetic(6));
        this.codeField = new TextField<>("codeField", new PropertyModel<>(this, "code"));
        this.form.add(this.codeField);
        this.codeFeedback = new TextFeedbackPanel("codeFeedback", this.codeField);
        this.form.add(this.codeFeedback);

        this.order = 1;
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
        if (this.code == null) {
            this.code = "";
        }
        String parent_entity_id = null;
        if (this.parentEntity != null) {
            parent_entity_id = this.parentEntity.getId();
        }


        String fullCode = null;
        if (this.parentEntity != null) {
            fullCode = getJdbcTemplate().queryForObject("select code from ecommerce_category where ecommerce_category_id = ?", String.class, this.parentEntity.getId()) + code;
        } else {
            fullCode = code;
        }

        String parentPath = "";
        String path = this.name;
        if (this.parentEntity != null) {
            parentPath = getJdbcTemplate().queryForObject("select path from ecommerce_category where ecommerce_category_id = ?", String.class, this.parentEntity.getId());
            path = parentPath + " > " + this.name;
        }


        InsertQuery insertQuery = new InsertQuery("ecommerce_category");
        insertQuery.addValue("ecommerce_category_id = :ecommerce_category_id", Platform.randomUUIDLong("ecommerce_category"));
        insertQuery.addValue("name = :name", this.name);
        insertQuery.addValue("`order` = :order", this.order);
        insertQuery.addValue("code = :code", this.code);
        insertQuery.addValue("path = :path", path);
        insertQuery.addValue("`enabled` = :enabled", true);
        insertQuery.addValue("parent_path = :parent_path", parentPath);
        insertQuery.addValue("full_code = :full_code", fullCode);
        insertQuery.addValue("type = :type", "category");
        insertQuery.addValue("parent_ecommerce_category_id = :parent_ecommerce_category_id", parent_entity_id);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());

        setResponsePage(CategoryBrowsePage.class);
    }

}
