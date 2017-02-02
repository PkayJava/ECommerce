package com.angkorteam.platform.page.menuitem;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.form.OptionDropDownChoice;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.MenuFormValidator;
import com.angkorteam.platform.model.PlatformMenuItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by socheat on 10/24/16.
 */
public class MenuItemModifyPage extends MBaaSPage {

    private String menuItemId;

    private String title;
    private TextField<String> titleField;
    private TextFeedbackPanel titleFeedback;

    private String icon;
    private TextField<String> iconField;
    private TextFeedbackPanel iconFeedback;

    private Integer order;
    private TextField<Integer> orderField;
    private TextFeedbackPanel orderFeedback;

    private Option menu;
    private OptionDropDownChoice menuField;
    private TextFeedbackPanel menuFeedback;

    private Option section;
    private OptionDropDownChoice sectionField;
    private TextFeedbackPanel sectionFeedback;

    private String pageTitle;
    private Label pageTitleLabel;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        PageParameters parameters = getPageParameters();
        this.menuItemId = parameters.get("menuItemId").toString("");

        PlatformMenuItem menuItem = getJdbcTemplate().queryForObject("select * from menu_item where menu_item_id = ?", PlatformMenuItem.class, this.menuItemId);

        this.title = menuItem.getTitle();
        this.icon = menuItem.getIcon();
        this.order = menuItem.getOrder();
        if (menuItem.getMenuId() != null) {
            this.menu = getJdbcTemplate().queryForObject("select menu_id id, title text from menu where menu_id = ?", Option.class, menuItem.getMenuId());
        }
        if (menuItem.getSectionId() != null) {
            this.section = getJdbcTemplate().queryForObject("select section_id id, title text from section where section_id = ?", Option.class, menuItem.getSectionId());
        }

        if (menuItem.getPageId() != null) {
            this.pageTitle = getJdbcTemplate().queryForObject("select page_title from page where page_id = ?", String.class, menuItem.getPageId());
        }

        this.form = new Form<>("form");
        layout.add(this.form);

        this.orderField = new TextField<>("orderField", new PropertyModel<>(this, "order"));
        this.orderField.setRequired(true);
        this.form.add(this.orderField);
        this.orderFeedback = new TextFeedbackPanel("orderFeedback", this.orderField);
        this.form.add(this.orderFeedback);

        this.titleField = new TextField<>("titleField", new PropertyModel<>(this, "title"));
        this.titleField.setRequired(true);
        this.form.add(this.titleField);
        this.titleFeedback = new TextFeedbackPanel("titleFeedback", this.titleField);
        this.form.add(this.titleFeedback);

        this.iconField = new TextField<>("iconField", new PropertyModel<>(this, "icon"));
        this.iconField.setRequired(true);
        this.form.add(this.iconField);
        this.iconFeedback = new TextFeedbackPanel("iconFeedback", this.iconField);
        this.form.add(this.iconFeedback);

        this.pageTitleLabel = new Label("pageTitleLabel", new PropertyModel<>(this, "pageTitle"));
        this.form.add(this.pageTitleLabel);

        this.menuField = new OptionDropDownChoice("menuField", new PropertyModel<>(this, "menu"), getJdbcTemplate(), "menu", "menu_id", "path");
        this.menuField.setNullValid(true);
        this.form.add(this.menuField);
        this.menuFeedback = new TextFeedbackPanel("menuFeedback", this.menuField);
        this.form.add(this.menuFeedback);

        this.sectionField = new OptionDropDownChoice("sectionField", new PropertyModel<>(this, "section"), getJdbcTemplate(), "section", "section_id", "title");
        this.sectionField.setNullValid(true);
        this.form.add(this.sectionField);
        this.sectionFeedback = new TextFeedbackPanel("sectionFeedback", this.sectionField);
        this.form.add(this.sectionFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", MenuItemBrowsePage.class);
        this.form.add(this.closeButton);

        this.form.add(new MenuFormValidator(this.menuField, this.sectionField));
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("menu_item");
        updateQuery.addValue("title = :title", this.title);
        updateQuery.addValue("`order` = :order", this.order);
        updateQuery.addValue("icon = :icon", this.icon);
        if (this.menu != null) {
            updateQuery.addValue("menu_id = :menu_id", this.menu.getId());
        } else {
            updateQuery.addValue("menu_id = NULL");
        }
        if (this.section != null) {
            updateQuery.addValue("section_id = :section_id", this.section.getId());
        } else {
            updateQuery.addValue("section_id = NULL");
        }
        updateQuery.addWhere("menu_item_id = :menu_item_id", this.menuItemId);

        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(MenuItemBrowsePage.class);
    }

}
