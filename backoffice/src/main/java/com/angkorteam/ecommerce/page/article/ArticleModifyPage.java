package com.angkorteam.ecommerce.page.article;

import com.angkorteam.ecommerce.model.ECommercePage;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.UniqueRecordValidator;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 23/1/17.
 */
public class ArticleModifyPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleModifyPage.class);

    private String ecommercePageId;

    private String articleTitle;
    private TextField<String> titleField;
    private TextFeedbackPanel titleFeedback;

    private String articleText;
    private TextArea<String> textField;
    private TextFeedbackPanel textFeedback;

    private Integer order = 0;
    private TextField<Integer> orderField;
    private TextFeedbackPanel orderFeedback;

    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommercePageId = getPageParameters().get("ecommercePageId").toString("");

        ECommercePage record = getJdbcTemplate().queryForObject("select * from ecommerce_page where ecommerce_page_id = ?", ECommercePage.class, this.ecommercePageId);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.articleTitle = record.getTitle();
        this.titleField = new TextField<>("titleField", new PropertyModel<>(this, "articleTitle"));
        this.titleField.add(new UniqueRecordValidator<>("ecommerce_page", "title", "ecommerce_page_id", ecommercePageId));
        this.titleField.setRequired(true);
        this.form.add(this.titleField);
        this.titleFeedback = new TextFeedbackPanel("titleFeedback", this.titleField);
        this.form.add(this.titleFeedback);

        this.articleText = record.getText();
        this.textField = new TextArea<>("textField", new PropertyModel<>(this, "articleText"));
        this.textField.setRequired(true);
        this.form.add(this.textField);
        this.textFeedback = new TextFeedbackPanel("textFeedback", this.textField);
        this.form.add(this.textFeedback);

        this.order = record.getOrder();
        this.orderField = new TextField<>("orderField", new PropertyModel<>(this, "order"));
        this.orderField.setRequired(true);
        this.form.add(this.orderField);
        this.orderFeedback = new TextFeedbackPanel("orderFeedback", this.orderField);
        this.form.add(this.orderFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ArticleBrowsePage.class);
        this.form.add(this.closeButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("ecommerce_page");
        updateQuery.addValue("title = :title", this.articleTitle);
        updateQuery.addValue("`order` = :order", this.order);
        updateQuery.addValue("`text` = :text", this.articleText);
        updateQuery.addWhere("ecommerce_page_id = :ecommerce_page_id", this.ecommercePageId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(ArticleBrowsePage.class);
    }

}