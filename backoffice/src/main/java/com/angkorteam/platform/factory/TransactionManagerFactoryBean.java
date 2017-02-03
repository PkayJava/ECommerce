package com.angkorteam.platform.factory;

import com.angkorteam.platform.bean.TransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;

/**
 * Created by socheatkhauv on 3/2/17.
 */
public class TransactionManagerFactoryBean implements FactoryBean<TransactionManager>, InitializingBean {

    private TransactionManager object;

    private DataSource delegate;

    @Override
    public TransactionManager getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return TransactionManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.object = new TransactionManager(this.delegate);
    }

    public void setDelegate(DataSource delegate) {
        this.delegate = delegate;
    }
}
