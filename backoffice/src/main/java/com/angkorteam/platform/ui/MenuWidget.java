package com.angkorteam.platform.ui;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformMenu;
import com.angkorteam.platform.model.PlatformMenuItem;
import com.angkorteam.platform.page.MBaaSPage;
import com.google.common.collect.Lists;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by socheat on 10/23/16.
 */
public class MenuWidget extends Panel {

    private Long menuId;

    private List<Object> items;

    private String cssClass;

    private WebMarkupContainer menuContainer;

    private boolean access = false;

    public MenuWidget(String id, Long menuId) {
        super(id);
        this.menuId = menuId;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.menuContainer = new WebMarkupContainer("menuContainer");
        this.menuContainer.add(AttributeModifier.replace("class", new PropertyModel<>(this, "cssClass")));
        add(this.menuContainer);

        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("platform_menu");
        selectQuery.addWhere("platform_menu_id = :menu_id", this.menuId);
        PlatformMenu menu = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), PlatformMenu.class);
        WebMarkupContainer menuIcon = new WebMarkupContainer("menuIcon");
        menuIcon.add(AttributeModifier.replace("class", "fa " + menu.getIcon()));
        this.menuContainer.add(menuIcon);
        Label menuTitle = new Label("menuTitle", menu.getTitle());
        this.menuContainer.add(menuTitle);

        selectQuery = new SelectQuery("platform_menu");
        selectQuery.addWhere("parent_platform_menu_id = :parent_menu_id", this.menuId);
        selectQuery.addOrderBy("`order`");
        List<PlatformMenu> menus = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), PlatformMenu.class);

        selectQuery = new SelectQuery("platform_menu_item");
        selectQuery.addWhere("platform_menu_id = :menu_id", this.menuId);
        selectQuery.addOrderBy("`order`");
        List<PlatformMenuItem> menuItems = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), PlatformMenuItem.class);

        this.items = Lists.newArrayList();
        this.items.addAll(menus);
        this.items.addAll(menuItems);
        Collections.sort(items, new SectionWidget.Comparator());

        RepeatingView itemWidgets = new RepeatingView("itemWidgets", new PropertyModel<Boolean>(this, "access"));
        for (Object item : items) {
            if (item instanceof PlatformMenu) {
                MenuWidget itemWidget = new MenuWidget(itemWidgets.newChildId(), ((PlatformMenu) item).getPlatformMenuId());
                itemWidgets.add(itemWidget);
            } else if (item instanceof PlatformMenuItem) {
                MenuItemWidget itemWidget = new MenuItemWidget(itemWidgets.newChildId(), ((PlatformMenuItem) item).getPlatformMenuItemId());
                itemWidgets.add(itemWidget);
            }
        }
        this.menuContainer.add(itemWidgets);

        setVisible(!this.items.isEmpty());
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = this.access || access;
        PropertyModel<Boolean> model = (PropertyModel<Boolean>) getParent().getDefaultModel();
        model.setObject(access);
    }

    @Override
    protected void onBeforeRender() {
        setVisible(this.access);
        MBaaSPage mBaaSPage = (MBaaSPage) getPage();
        this.cssClass = mBaaSPage.isMenuWidgetSelected(this.menuId) ? "treeview active" : "treeview";
        super.onBeforeRender();
    }

}
