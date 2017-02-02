package com.angkorteam.platform.factory;

import com.angkorteam.platform.Configuration;
import com.google.common.base.Strings;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.File;

/**
 * Created by Khauv Socheat on 2/4/2016.
 */
public class DataSourceFactoryBean implements FactoryBean<DataSource>, InitializingBean, ServletContextAware {

    private DataSource dataSource;

    private ServletContext servletContext;

    private XMLPropertiesConfiguration configuration;

    @Override
    public DataSource getObject() throws Exception {
        return this.dataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return DataSourceFactoryBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String configurationFile = this.servletContext.getInitParameter("configuration");
        File file;
        if (!Strings.isNullOrEmpty(configurationFile)) {
            file = new File(configurationFile);
        } else {
            File home = new File(java.lang.System.getProperty("user.home"));
            file = new File(home, ".xml/" + Configuration.KEY);
        }
        try {
            this.configuration = new XMLPropertiesConfiguration(file);
        } catch (ConfigurationException e) {
        }

        String jdbcType = this.configuration.getString(Configuration.APP_JDBC_TYPE);
        if (StringUtils.equalsIgnoreCase(jdbcType, "Local")) {
            String jdbcDriver = configuration.getString(Configuration.APP_JDBC_DRIVER);
            String jdbcUrl = "jdbc:mysql://" + configuration.getString(Configuration.APP_JDBC_HOSTNAME) + ":" + configuration.getString(Configuration.APP_JDBC_PORT) + "/" + configuration.getString(Configuration.APP_JDBC_DATABASE) + "?" + configuration.getString(Configuration.APP_JDBC_EXTRA);
            String username = configuration.getString(Configuration.APP_JDBC_USERNAME);
            String password = configuration.getString(Configuration.APP_JDBC_PASSWORD);
            this.dataSource = getDataSource(jdbcDriver, jdbcUrl, username, password);
        } else if (StringUtils.equalsIgnoreCase(jdbcType, "Container")) {
            String jdbcJndi = this.configuration.getString(Configuration.APP_JDBC_JNDI);
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            this.dataSource = (DataSource) envContext.lookup(jdbcJndi);
        }
    }

    protected BasicDataSource getDataSource(String jdbcDriver, String jdbcUrl, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxIdle(100);
        dataSource.setMinIdle(50);
        dataSource.setMaxWaitMillis(5000);
        dataSource.setMaxTotal(200);
        dataSource.setInitialSize(30);
        dataSource.setUrl(jdbcUrl);
        return dataSource;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}

