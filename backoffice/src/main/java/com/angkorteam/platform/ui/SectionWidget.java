package com.angkorteam.platform.ui;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformMenu;
import com.angkorteam.platform.model.PlatformMenuItem;
import com.angkorteam.platform.model.PlatformSection;
import com.google.common.collect.Lists;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by socheat on 10/23/16.
 */
public class SectionWidget extends Panel {

    private PlatformSection section;

    private boolean access = false;

    public SectionWidget(String id, PlatformSection section) {
        super(id);
        this.section = section;
        this.access = false;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        SelectQuery selectQuery = null;

        Label label = new Label("sectionTitle", this.section.getTitle());
        add(label);

        selectQuery = new SelectQuery("platform_menu");
        selectQuery.addWhere("platform_section_id = :section_id", this.section.getPlatformSectionId());
        selectQuery.addOrderBy("`order`");
        List<PlatformMenu> menus = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), PlatformMenu.class);

        selectQuery = new SelectQuery("platform_menu_item");
        selectQuery.addWhere("platform_section_id = :section_id", this.section.getPlatformSectionId());
        selectQuery.addOrderBy("`order` ");
        List<PlatformMenuItem> menuItems = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), PlatformMenuItem.class);
        List<Object> items = Lists.newArrayList();
        items.addAll(menus);
        items.addAll(menuItems);
        Collections.sort(items, new Comparator());

        RepeatingView itemWidgets = new RepeatingView("itemWidgets", new PropertyModel<Boolean>(this, "access"));
        for (Object item : items) {
            if (item instanceof PlatformMenu) {
                PlatformMenu menu = (PlatformMenu) item;
                MenuWidget itemWidget = new MenuWidget(itemWidgets.newChildId(), menu.getPlatformMenuId());
                itemWidgets.add(itemWidget);
            } else if (item instanceof PlatformMenuItem) {
                PlatformMenuItem menuItem = (PlatformMenuItem) item;
                MenuItemWidget itemWidget = new MenuItemWidget(itemWidgets.newChildId(), menuItem.getPlatformMenuItemId());
                itemWidgets.add(itemWidget);
            }
        }
        add(itemWidgets);
    }

    @Override
    protected void onBeforeRender() {
        setVisible(this.access);
        super.onBeforeRender();
    }

    public static class Comparator implements java.util.Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            Integer io1 = o1 instanceof PlatformMenu ? ((PlatformMenu) o1).getOrder() : ((PlatformMenuItem) o1).getOrder();
            Integer io2 = o2 instanceof PlatformMenu ? ((PlatformMenu) o2).getOrder() : ((PlatformMenuItem) o2).getOrder();
            return io1.compareTo(io2);
        }

    }

}
