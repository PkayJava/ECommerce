package com.angkorteam.platform.bean;

import com.angkorteam.platform.Platform;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by socheatkhauv on 3/2/17.
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
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
        Map<String, Object> error = Maps.newHashMap();
        error.put("body", new String[]{exception.getMessage()});
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
