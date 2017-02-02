package com.angkorteam.platform.factory;

import com.angkorteam.platform.Configuration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public class ResourceFactoryBean implements FactoryBean<Resource>, InitializingBean, ServletContextAware {

    private Resource resource;

    private ServletContext servletContext;

    @Override
    public Resource getObject() throws Exception {
        return this.resource;
    }

    @Override
    public Class<?> getObjectType() {
        return Resource.class;
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
        this.resource = new FileSystemResource(file);
    }
}
