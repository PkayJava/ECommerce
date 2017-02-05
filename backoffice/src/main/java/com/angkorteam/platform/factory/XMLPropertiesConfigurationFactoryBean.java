package com.angkorteam.platform.factory;

import com.angkorteam.platform.Configuration;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public class XMLPropertiesConfigurationFactoryBean implements FactoryBean<XMLPropertiesConfiguration>, InitializingBean, ServletContextAware {

    private XMLPropertiesConfiguration configuration;

    private ServletContext servletContext;

    @Override
    public XMLPropertiesConfiguration getObject() throws Exception {
        return this.configuration;
    }

    @Override
    public Class<?> getObjectType() {
        return XMLPropertiesConfiguration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.servletContext == null) {
            throw new IllegalArgumentException("servlet context is null");
        }
        String configurationFile = this.servletContext.getInitParameter("configuration");
        File file = null;
        if (configurationFile != null) {
            file = new File(configurationFile);
        } else {
            File home = new File(System.getProperty("user.home"));
            file = new File(home, ".xml/" + Configuration.KEY);
        }
        FileInputStream inputStream = FileUtils.openInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, outputStream);
        IOUtils.closeQuietly(inputStream);
        this.configuration = new XMLPropertiesConfiguration();
        this.configuration.load(new ByteArrayInputStream(outputStream.toByteArray()));
    }

}
