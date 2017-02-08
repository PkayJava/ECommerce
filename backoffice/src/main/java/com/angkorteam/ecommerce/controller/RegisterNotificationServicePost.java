package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.model.EcommerceDevice;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class RegisterNotificationServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNotificationServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/devices", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBody = this.gson.fromJson(request.getReader(), type);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_device");
        selectQuery.addWhere("device_token = :device_token", requestBody.get("device_token"));
        selectQuery.addWhere("platform = :platform", requestBody.get("platform"));

        EcommerceDevice deviceRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceDevice.class);
        Long deviceId = 0L;
        if (deviceRecord == null) {
            deviceId = Platform.randomUUIDLong("ecommerce_device");
            InsertQuery insertQuery = new InsertQuery("ecommerce_device");
            insertQuery.addValue("ecommerce_device_id = :ecommerce_device_id", deviceId);
            insertQuery.addValue("device_token = :device_token", requestBody.get("device_token"));
            insertQuery.addValue("platform = :platform", requestBody.get("platform"));
            named.update(insertQuery.toSQL(), insertQuery.getParam());
        } else {
            deviceId = deviceRecord.getEcommerceDeviceId();
        }

        PlatformUser currentUser = Platform.getCurrentUser(request);
        if (currentUser != null) {
            UpdateQuery updateQuery = new UpdateQuery("ecommerce_device");
            updateQuery.addValue("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
            updateQuery.addWhere("ecommerce_device_id = :ecommerce_device_id", deviceId);
            named.update(updateQuery.toSQL(), updateQuery.getParam());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("id", deviceId);
        data.put("device_token", requestBody.get("device_token"));
        data.put("platform", requestBody.get("platform"));
        return ResponseEntity.ok(data);
    }

}