package com.angkorteam.platform.layout;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.Session;
import com.angkorteam.platform.page.LogoutPage;
import com.angkorteam.platform.ui.SectionWidget;
import com.angkorteam.platform.Spring;
import com.angkorteam.platform.model.PlatformPage;
import com.angkorteam.platform.model.PlatformSection;
import com.angkorteam.platform.model.PlatformUser;
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

        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);

        String javaClass = getPage().getClass().getName();
        this.page = jdbcTemplate.queryForObject("select * from page where  java_class = ?", PlatformPage.class, javaClass);

        WebMarkupContainer headerContainer = new WebMarkupContainer("headerContainer");
        addToBorder(headerContainer);
        Label headerTitle = new Label("headerTitle", new PropertyModel<>(this.page, "pageTitle"));
        headerContainer.add(headerTitle);
        Label headerDescription = new Label("headerDescription", new PropertyModel<>(this.page, "pageDescription"));
        headerContainer.add(headerDescription);
        headerContainer.setVisible(!(Strings.isNullOrEmpty(this.page.getPageTitle()) && Strings.isNullOrEmpty(this.page.getPageDescription())));

        List<PlatformSection> sections = jdbcTemplate.queryForList("select * from section order by `order` asc", PlatformSection.class);

        RepeatingView sectionWidgets = new RepeatingView("sectionWidgets");

        for (PlatformSection section : sections) {
            SectionWidget sectionWidget = new SectionWidget(sectionWidgets.newChildId(), section);
            sectionWidgets.add(sectionWidget);
        }

        addToBorder(sectionWidgets);

        BookmarkablePageLink<Void> logoutPage = new BookmarkablePageLink<>("logoutPage", LogoutPage.class);
        addToBorder(logoutPage);

        PlatformUser user = jdbcTemplate.queryForObject("select * from user where user_id = ?", PlatformUser.class, getSession().getUserId());
        if (user == null) {
            LOGGER.debug("user id {} is deleted", getSession().getUserId());
        }
        if (user != null) {
            this.welcomeName = Strings.isNullOrEmpty(user.getFullName()) ? user.getLogin() : user.getFullName();
        }
        Label welcomeNameLabel = new Label("welcomeNameLabel", new PropertyModel<>(this, "welcomeName"));
        addToBorder(welcomeNameLabel);
    }

    @Override
    public final Session getSession() {
        return (Session) super.getSession();
    }

}
