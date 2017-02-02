package com.angkorteam.platform;

/**
 * Created by socheatkhauv on 20/1/17.
 */
public interface Configuration {

    String KEY = "platform.properties.xml";

    String MAINTENANCE = "maintenance";

    String WICKET = "wicket";

    String APP_JDBC_TYPE = "app.jdbc.type";
    String APP_JDBC_JNDI = "app.jdbc.jndi";
    String APP_JDBC_DRIVER = "app.jdbc.driver";
    String APP_JDBC_HOSTNAME = "app.jdbc.hostname";
    String APP_JDBC_EXTRA = "app.jdbc.extra";
    String APP_JDBC_PORT = "app.jdbc.port";
    String APP_JDBC_USERNAME = "app.jdbc.username";
    String APP_JDBC_PASSWORD = "app.jdbc.password";
    String APP_JDBC_DATABASE = "app.jdbc.database";

    String ROLE_ADMINISTRATOR = "role.administrator";
    String ROLE_ADMINISTRATOR_DESCRIPTION = "role.administrator.description";
    String ROLE_SYSTEM = "role.system";
    String ROLE_SYSTEM_DESCRIPTION = "role.system.description";
    String ROLE_SERVICE = "role.service";
    String ROLE_SERVICE_DESCRIPTION = "role.service.description";

    String USER_ADMIN = "user.admin";
    String USER_ADMIN_ROLE = "user.admin.role";
    String USER_ADMIN_PASSWORD = "user.admin.password";

    String USER_SYSTEM = "user.system";
    String USER_SYSTEM_ROLE = "user.system.role";
    String USER_SYSTEM_PASSWORD = "user.system.password";

    String USER_SERVICE = "user.service";
    String USER_SERVICE_ROLE = "user.service.role";
    String USER_SERVICE_PASSWORD = "user.service.password";

    String PATTERN_DATETIME = "pattern.datetime";
    String PATTERN_TIME = "pattern.time";
    String PATTERN_DATE = "pattern.date";
    String PATTERN_FOLDER = "pattern.folder";

    String ENCRYPTION_PASSWORD = "encryption.password";
    String ENCRYPTION_OUTPUT = "encryption.output";

    String RESOURCE_REPO = "resource.repo";

    String EXECUTOR_POOL_SIZE = "executor.pool_size";
    String EXECUTOR_QUEUE_CAPACITY = "executor.queue_capacity";

}
