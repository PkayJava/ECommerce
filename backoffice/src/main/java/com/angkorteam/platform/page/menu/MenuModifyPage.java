package com.angkorteam.platform.page.menu;

import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.OptionDropDownChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformMenu;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.validator.MenuFormValidator;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by socheat on 10/24/16.
 */
public class MenuModifyPage extends MBaaSPage {

    private String menuId;

    private String title;
    private TextField<String> titleField;
    private TextFeedbackPanel titleFeedback;

    private String icon;
    private TextField<String> iconField;
    private TextFeedbackPanel iconFeedback;

    private Integer order;
    private TextField<Integer> orderField;
    private TextFeedbackPanel orderFeedback;

    private Option menuParent;
    private OptionDropDownChoice parentField;
    private TextFeedbackPanel parentFeedback;

    private Option section;
    private OptionDropDownChoice sectionField;
    private TextFeedbackPanel sectionFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);

        PageParameters parameters = getPageParameters();
        this.menuId = parameters.get("menuId").toString("");

        PlatformMenu menu = jdbcTemplate.queryForObject("select * from platform_menu where platform_menu_id = ?", PlatformMenu.class, this.menuId);
        this.title = menu.getTitle();
        this.icon = menu.getIcon();
        this.order = menu.getOrder();
        if (menu.getParentPlatformMenuId() != null) {
            this.menuParent = jdbcTemplate.queryForObject("select platform_menu_id id, title text from platform_menu where platform_menu_id = ?", Option.class, menu.getParentPlatformMenuId());
        }
        if (menu.getPlatformSectionId() != null) {
            this.section = jdbcTemplate.queryForObject("select platform_section_id id, title text from platform_section where platform_section_id = ?", Option.class, menu.getPlatformSectionId());
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

        this.parentField = new OptionDropDownChoice("parentField", new PropertyModel<>(this, "menuParent"), jdbcTemplate, "platform_menu", "platform_menu_id", "path");
        this.parentField.setNullValid(true);
        this.form.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.form.add(this.parentFeedback);

        this.sectionField = new OptionDropDownChoice("sectionField", new PropertyModel<>(this, "section"), jdbcTemplate, "platform_section", "platform_section_id", "title");
        this.sectionField.setNullValid(true);
        this.form.add(this.sectionField);
        this.sectionFeedback = new TextFeedbackPanel("sectionFeedback", this.sectionField);
        this.form.add(this.sectionFeedback);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", MenuBrowsePage.class);
        this.form.add(this.closeButton);

        this.form.add(new MenuFormValidator(this.parentField, this.sectionField));
    }

    private void saveButtonOnSubmit(Button button) {
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        UpdateQuery updateQuery = new UpdateQuery("platform_menu");
        updateQuery.addValue("title = :title", this.title);
        updateQuery.addValue("icon = :icon", this.icon);
        updateQuery.addValue("`order` = :order", this.order);
        updateQuery.addWhere("platform_menu_id = :platform_menu_id", this.menuId);

        SelectQuery selectQuery = null;

        if (this.menuParent != null) {
            selectQuery = new SelectQuery("platform_menu");
            selectQuery.addField("path");
            selectQuery.addWhere("platform_menu_id = :platform_menu_id", this.menuParent.getId());
            String path = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
            updateQuery.addValue("parent_platform_menu_id = :parent_platform_menu_id", this.menuParent.getId());
            updateQuery.addValue("path = :path", path + " > " + this.title);
        } else {
            updateQuery.addValue("parent_platform_menu_id = NULL");
        }

        if (this.section != null) {
            selectQuery = new SelectQuery("platform_section");
            selectQuery.addField("title");
            selectQuery.addWhere("platform_section_id = :platform_section_id", this.section.getId());
            String path = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
            updateQuery.addValue("platform_section_id = :platform_section_id", this.section.getId());
            updateQuery.addValue("path = :path", path + " > " + this.title);

        } else {
            updateQuery.addValue("platform_section_id = NULL");
        }
        named.update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(MenuBrowsePage.class);
    }

}
