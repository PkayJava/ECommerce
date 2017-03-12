package com.angkorteam.ecommerce.page.article;

import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.platform.Platform;
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
public class ArticleCreatePage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleCreatePage.class);

    private String articleTitle;
    private TextField<String> titleField;
    private TextFeedbackPanel titleFeedback;

    private String articleText;
    private TextArea textField;
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

        this.form = new Form<>("form");
        layout.add(this.form);

        this.titleField = new TextField<>("titleField", new PropertyModel<String>(this, "articleTitle"));
        this.titleField.add(new UniqueRecordValidator<>("ecommerce_page", "title"));
        this.titleField.setRequired(true);
        this.form.add(this.titleField);
        this.titleFeedback = new TextFeedbackPanel("titleFeedback", this.titleField);
        this.form.add(this.titleFeedback);

        this.textField = new TextArea<>("textField", new PropertyModel<String>(this, "articleText"));
        this.textField.setRequired(true);
        this.form.add(this.textField);
        this.textFeedback = new TextFeedbackPanel("textFeedback", this.textField);
        this.form.add(this.textFeedback);

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
        InsertQuery insertQuery = new InsertQuery("ecommerce_page");
        insertQuery.addValue("`ecommerce_page_id` = :ecommerce_page_id", Platform.randomUUIDLong("ecommerce_page"));
        insertQuery.addValue("`order` = :order", this.order);
        insertQuery.addValue("`enabled` = :enabled", true);
        insertQuery.addValue("`title` = :title", this.articleTitle);
        insertQuery.addValue("`text` = :text", this.articleText);
        getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
        setResponsePage(ArticleBrowsePage.class);
    }

}
