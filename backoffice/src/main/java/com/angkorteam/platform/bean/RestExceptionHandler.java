package com.angkorteam.platform.bean;

import com.angkorteam.platform.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by socheatkhauv on 3/2/17.
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public void handler(HttpServletRequest request, HttpServletResponse response, Exception error) throws Exception {
        LOGGER.info("error {}", error.getMessage());
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
        }
        error.printStackTrace(response.getWriter());
        error.printStackTrace();
    }

}
