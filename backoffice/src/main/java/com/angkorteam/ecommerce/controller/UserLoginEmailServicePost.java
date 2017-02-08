package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.User;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class UserLoginEmailServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginEmailServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/login/email", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBody = this.gson.fromJson(request.getReader(), type);

        SelectQuery selectQuery = new SelectQuery("platform_user");
        selectQuery.addField("access_token accessToken");
        selectQuery.addField("platform_user_id id");
        selectQuery.addField("full_name name");
        selectQuery.addField("street");
        selectQuery.addField("country");
        selectQuery.addField("city");
        selectQuery.addField("house_number houseNumber");
        selectQuery.addField("zip");
        selectQuery.addField("login email");
        selectQuery.addField("phone");
        selectQuery.addField("gender");
        selectQuery.addWhere("login = :login", requestBody.get("email"));
        selectQuery.addWhere("password = MD5(:password)", "password", requestBody.get("password"));

        User data = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), User.class);

        if (data == null) {
            Map<String, Object> error = Maps.newHashMap();
            error.put("body", new String[]{"Invalid password"});
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(data);
    }

}