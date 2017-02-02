package com.angkorteam.platform.factory;

import com.angkorteam.platform.bean.HttpServletRequestDataSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public class HttpServletRequestDataSourceFactoryBean implements FactoryBean<DataSource>, InitializingBean, DisposableBean {

    private DataSource delegate;

    private HttpServletRequestDataSource dataSource;

    @Override
    public DataSource getObject() throws Exception {
        return this.dataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setDelegate(DataSource delegate) {
        this.delegate = delegate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.dataSource = new HttpServletRequestDataSource(this.delegate);
    }

    @Override
    public void destroy() throws Exception {
        Connection connection = dataSource.getConnection();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
            }
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }
}
