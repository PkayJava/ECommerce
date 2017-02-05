package com.angkorteam.platform;

import com.angkorteam.ecommerce.page.VendorRegisterPage;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.bean.AuthorizationStrategy;
import com.angkorteam.platform.bean.ConnectionRequestCycleBean;
import com.angkorteam.platform.bean.LocalizationMessage;
import com.angkorteam.platform.model.PlatformPage;
import com.angkorteam.platform.page.DashboardPage;
import com.angkorteam.platform.page.LoginPage;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.resource.DynamicJQueryResourceReference;
import org.apache.wicket.settings.ExceptionSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by socheat on 10/23/16.
 */
public class Application extends AuthenticatedWebApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public Application() {
    }

    @Override
    protected void init() {
        PropertyResolver.setLocator(this, new PropertyResolver.DefaultPropertyLocator());
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(this);
        AuthorizationStrategy authorizationStrategy = new AuthorizationStrategy();
        getExceptionSettings().setAjaxErrorHandlingStrategy(ExceptionSettings.AjaxErrorStrategy.REDIRECT_TO_ERROR_PAGE);
        getExceptionSettings().setThreadDumpStrategy(ExceptionSettings.ThreadDumpStrategy.THREAD_HOLDING_LOCK);
        getExceptionSettings().setUnexpectedExceptionDisplay(ExceptionSettings.SHOW_EXCEPTION_PAGE);
        getSecuritySettings().setAuthorizationStrategy(authorizationStrategy);
        getRequestCycleListeners().add(new ConnectionRequestCycleBean());
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        getRequestCycleSettings().setBufferResponse(true);
        getResourceSettings().setLocalizer(new LocalizationMessage());
        getMarkupSettings().setCompressWhitespace(true);
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
        getMarkupSettings().setStripWicketTags(true);
        getMarkupSettings().setStripComments(true);
        getJavaScriptLibrarySettings().setJQueryReference(new DynamicJQueryResourceReference());
        initPageMount();
        LOGGER.info("application is initialized");
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return Session.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        Session session = (Session) Session.get();
        if (session.isSignedIn()) {
            return DashboardPage.class;
        } else {
            return VendorRegisterPage.class;
        }
    }

    protected void initPageMount() {
        DataSource dataSource = Platform.getBean("delegate", DataSource.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<PlatformPage> pages = jdbcTemplate.queryForList("select * from platform_page", PlatformPage.class);
        for (PlatformPage page : pages) {
            String path = page.getPath();
            String javaClass = page.getJavaClass();
            try {
                mountPage(path, (Class<WebPage>) Class.forName(javaClass));
            } catch (ClassNotFoundException e) {
                LOGGER.debug("page class is not found {}", javaClass);
            }
        }
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        XMLPropertiesConfiguration configuration = Platform.getBean(XMLPropertiesConfiguration.class);
        return RuntimeConfigurationType.valueOf(configuration.getString(Configuration.WICKET));
    }

}
