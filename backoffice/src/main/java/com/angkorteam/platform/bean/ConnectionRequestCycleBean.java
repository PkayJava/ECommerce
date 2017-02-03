package com.angkorteam.platform.bean;

import com.angkorteam.platform.Platform;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by socheatkhauv on 19/1/17.
 */
public class ConnectionRequestCycleBean implements IRequestCycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionRequestCycleBean.class);

    @Override
    public IRequestHandler onException(RequestCycle cycle, Exception ex) {
        DataSource dataSource = Platform.getBean("dataSource", DataSource.class);
        try {
            Connection connection = dataSource.getConnection();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                }
            }
        } catch (SQLException e) {
            throw new WicketRuntimeException(e);
        }
        return null;
    }

}
