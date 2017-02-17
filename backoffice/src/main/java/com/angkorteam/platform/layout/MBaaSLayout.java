package com.angkorteam.platform.layout;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Session;
import com.angkorteam.platform.model.PlatformPage;
import com.angkorteam.platform.model.PlatformSection;
import com.angkorteam.platform.model.PlatformUser;
import com.angkorteam.platform.page.LogoutPage;
import com.angkorteam.platform.page.SettingPage;
import com.angkorteam.platform.page.user.UserProfilePage;
import com.angkorteam.platform.ui.SectionWidget;
import com.google.common.base.Strings;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by socheat on 11/3/16.
 */
public class MBaaSLayout extends Border {

    private static final Logger LOGGER = LoggerFactory.getLogger(MBaaSLayout.class);

    private String welcomeName;

    private PlatformPage page;

    public MBaaSLayout(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Label shortcutText = new Label("shortcutText", Platform.getSetting(SettingPage.SHORTCUT_TEXT));
        add(shortcutText);

        Label descriptionText = new Label("descriptionText", Platform.getSetting(SettingPage.DESCRIPTION_TEXT));
        add(descriptionText);

        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);

        String javaClass = getPage().getClass().getName();
        this.page = jdbcTemplate.queryForObject("select * from platform_page where  java_class = ?", PlatformPage.class, javaClass);

        WebMarkupContainer headerContainer = new WebMarkupContainer("headerContainer");
        addToBorder(headerContainer);
        Label headerTitle = new Label("headerTitle", new PropertyModel<>(this.page, "pageTitle"));
        headerContainer.add(headerTitle);
        Label headerDescription = new Label("headerDescription", new PropertyModel<>(this.page, "pageDescription"));
        headerContainer.add(headerDescription);
        headerContainer.setVisible(!(Strings.isNullOrEmpty(this.page.getPageTitle()) && Strings.isNullOrEmpty(this.page.getPageDescription())));

        List<PlatformSection> sections = jdbcTemplate.queryForList("select * from platform_section order by `order` asc", PlatformSection.class);

        RepeatingView sectionWidgets = new RepeatingView("sectionWidgets");

        for (PlatformSection section : sections) {
            SectionWidget sectionWidget = new SectionWidget(sectionWidgets.newChildId(), section);
            sectionWidgets.add(sectionWidget);
        }

        addToBorder(sectionWidgets);

        BookmarkablePageLink<Void> logoutPage = new BookmarkablePageLink<>("logoutPage", LogoutPage.class);
        addToBorder(logoutPage);

        PlatformUser user = jdbcTemplate.queryForObject("select * from platform_user where platform_user_id = ?", PlatformUser.class, getSession().getPlatformUserId());
        if (user == null) {
            LOGGER.debug("user id {} is deleted", getSession().getPlatformUserId());
        }
        if (user != null) {
            this.welcomeName = Strings.isNullOrEmpty(user.getFullName()) ? user.getLogin() : user.getFullName();
        }

        BookmarkablePageLink<Void> profileLink = new BookmarkablePageLink<>("profileLink", UserProfilePage.class);
        addToBorder(profileLink);

        Label welcomeNameLabel = new Label("welcomeNameLabel", new PropertyModel<>(this, "welcomeName"));
        profileLink.add(welcomeNameLabel);
    }

    @Override
    public final Session getSession() {
        return (Session) super.getSession();
    }

}
