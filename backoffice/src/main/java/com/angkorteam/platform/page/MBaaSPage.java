package com.angkorteam.platform.page;

import com.angkorteam.framework.HttpFunction;
import com.angkorteam.framework.wicket.AdminLTEPage;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Session;
import com.angkorteam.platform.model.PlatformMenu;
import com.angkorteam.platform.model.PlatformMenuItem;
import com.angkorteam.platform.model.PlatformPage;
import com.angkorteam.platform.model.PlayformLayout;
import com.google.common.collect.Lists;
import org.apache.wicket.markup.html.border.Border;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by socheat on 10/23/16.
 */
public abstract class MBaaSPage extends AdminLTEPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MBaaSPage.class);

    private List<String> breadcrumb;

    private Border layout;

    @Override
    protected final void onInitialize() {
        super.onInitialize();
        this.breadcrumb = initBreadcrumb();

        PlatformPage page = getJdbcTemplate().queryForObject("select * from platform_page where java_class = ?", PlatformPage.class, getClass().getName());
        if (page == null) {
            LOGGER.debug("{} is not registered in {}", getClass().getName(), "page");
        }

        PlayformLayout layout = getJdbcTemplate().queryForObject("select * from platform_layout where platform_layout_id = ?", PlayformLayout.class, page.getPlatformLayoutId());
        if (layout == null) {
            LOGGER.debug("layout id {} is not registered", page.getPlatformLayoutId());
        }

        Class<? extends Border> layoutClass = null;
        try {
            layoutClass = (Class<? extends Border>) Class.forName(layout.getJavaClass());
        } catch (ClassNotFoundException e) {
            LOGGER.debug("layout class {} is not found", layout.getJavaClass());
        }
        Constructor<? extends Border> constructor = null;
        try {
            constructor = layoutClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            LOGGER.debug("layout class {} need to have constructor (String id)", layout.getJavaClass());
        }
        Border cmsLayout = null;
        try {
            cmsLayout = constructor.newInstance("layout");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(e.getMessage());
        }
        this.layout = cmsLayout;

        doInitialize(this.layout);
    }

    protected abstract void doInitialize(Border layout);

    protected final Border getLayout() {
        return this.layout;
    }

    public PlatformMenuItem getMenuItem() {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        PlatformPage page = jdbcTemplate.queryForObject("select * from platform_page where java_class = ?", PlatformPage.class, getClass().getName());
        PlatformMenuItem menuItem = jdbcTemplate.queryForObject("select * from platform_menu_item where platform_page_id = ?", PlatformMenuItem.class, page.getPlatformPageId());
        return menuItem;
    }

    protected List<String> initBreadcrumb() {
        List<String> breadcrumb = Lists.newArrayList();
        PlatformMenuItem menuItem = getMenuItem();
        if (menuItem == null) {
            return breadcrumb;
        }
        if (menuItem.getPlatformMenuItemId() != null) {
            breadcrumb.add("MenuItemId:" + menuItem.getPlatformMenuItemId());
        }
        if (menuItem.getPlatformSectionId() != null) {
            breadcrumb.add("SectionId:" + menuItem.getPlatformSectionId());
        }
        if (menuItem.getPlatformMenuId() != null) {
            breadcrumb.add("MenuId:" + menuItem.getPlatformMenuId());
        }

        Long menuId = menuItem.getPlatformMenuId();

        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);

        while (true) {
            if (menuId == null) {
                break;
            }
            PlatformMenu menu = jdbcTemplate.queryForObject("select * from platform_menu where platform_menu_id = ?", PlatformMenu.class, menuId);
            if (menu == null) {
                break;
            }
            menuId = menu.getParentPlatformMenuId();
            if (menu.getPlatformSectionId() != null) {
                breadcrumb.add("SectionId:" + menu.getPlatformSectionId());
            }
            if (menu.getParentPlatformMenuId() != null) {
                breadcrumb.add("MenuId:" + menu.getParentPlatformMenuId());
            }

        }
        return breadcrumb;
    }

    @Override
    public Session getSession() {
        return (Session) super.getSession();
    }

    protected NamedParameterJdbcTemplate getNamed() {
        return Platform.getBean(NamedParameterJdbcTemplate.class);
    }

    protected JdbcTemplate getJdbcTemplate() {
        return Platform.getBean(JdbcTemplate.class);
    }

    public boolean isMenuWidgetSelected(Long menuId) {
        if (this.breadcrumb == null) {
            this.breadcrumb = initBreadcrumb();
        }
        return this.breadcrumb.contains("MenuId:" + menuId);
    }

    public boolean isMenuItemWidgetSelected(Long menuItemId) {
        if (this.breadcrumb == null) {
            this.breadcrumb = initBreadcrumb();
        }
        return this.breadcrumb.contains("MenuItemId:" + menuItemId);
    }

    public String getHttpAddress() {
        HttpServletRequest request = (HttpServletRequest) getRequest().getContainerRequest();
        return HttpFunction.getHttpAddress(request);
    }

}
