package com.angkorteam.platform.ui;

import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Session;
import com.angkorteam.platform.model.PlatformMenuItem;
import com.angkorteam.platform.model.PlatformPage;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by socheat on 10/23/16.
 */
public class MenuItemWidget extends Panel {

    private final static Logger LOGGER = LoggerFactory.getLogger(MenuItemWidget.class);

    private Long menuItemId;

    private String cssClass;

    private WebMarkupContainer menuItemContainer;

    private Label menuItemLabel;

    private boolean access = false;

    public MenuItemWidget(String id, Long menuItemId) {
        super(id);
        this.menuItemId = menuItemId;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);

        this.menuItemContainer = new WebMarkupContainer("menuItemContainer");
        this.menuItemContainer.add(AttributeModifier.replace("class", new PropertyModel<>(this, "cssClass")));
        add(this.menuItemContainer);

        SelectQuery selectQuery = null;

        PlatformMenuItem menuItem = jdbcTemplate.queryForObject("select * from platform_menu_item where platform_menu_item_id = ?", PlatformMenuItem.class, this.menuItemId);

        PlatformPage page = jdbcTemplate.queryForObject("select * from platform_page where platform_page_id = ?", PlatformPage.class, menuItem.getPlatformPageId());
        if (page == null) {
            LOGGER.debug("page id {} is not found", menuItem.getPlatformPageId());
        }
        Roles sessionRoles = getSession().getRoles();

        selectQuery = new SelectQuery("platform_role");
        selectQuery.addField("platform_role.name");
        selectQuery.addJoin(JoinType.InnerJoin, "platform_page_role", "platform_page_role.platform_role_id = platform_role.platform_role_id");
        selectQuery.addWhere("platform_page_role.platform_page_id = :page_id", page.getPlatformPageId());
        List<String> pageRoles = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);

        if (pageRoles != null && !pageRoles.isEmpty()) {
            for (String role : pageRoles) {
                if (sessionRoles.hasRole(role)) {
                    this.access = true;
                    PropertyModel<Boolean> model = (PropertyModel<Boolean>) getParent().getDefaultModel();
                    model.setObject(true);
                    break;
                }
            }
        }

        Class<? extends WebPage> pageClass = null;
        try {
            pageClass = (Class<? extends WebPage>) Class.forName(page.getJavaClass());
        } catch (ClassNotFoundException e) {
            LOGGER.debug("page class {} is not found {}", page.getJavaClass());
        }

        BookmarkablePageLink<Void> menuItemLink = new BookmarkablePageLink<>("menuItemLink", pageClass);
        this.menuItemContainer.add(menuItemLink);

        WebMarkupContainer menuItemIcon = new WebMarkupContainer("menuItemIcon");
        menuItemLink.add(menuItemIcon);
        menuItemIcon.add(AttributeModifier.replace("class", "fa " + menuItem.getIcon()));

        this.menuItemLabel = new Label("menuItemLabel", menuItem.getTitle());
        menuItemLink.add(this.menuItemLabel);
        this.menuItemLabel.setRenderBodyOnly(menuItem.getPlatformSectionId() == null);
    }

    @Override
    protected void onBeforeRender() {
        setVisible(this.access);
        MBaaSPage mBaaSPage = (MBaaSPage) getPage();
        this.cssClass = mBaaSPage.isMenuItemWidgetSelected(this.menuItemId) ? "active" : "";
        super.onBeforeRender();
    }

    @Override
    public Session getSession() {
        return (Session) super.getSession();
    }
}
