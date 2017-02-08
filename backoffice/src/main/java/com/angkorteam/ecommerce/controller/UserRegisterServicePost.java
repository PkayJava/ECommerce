package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.User;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformRole;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.validator.routines.EmailValidator;
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
public class UserRegisterServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/users/register", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBody = this.gson.fromJson(request.getReader(), type);

        String email = requestBody.get("email");
        String password = requestBody.get("password");
        String gender = requestBody.get("gender");

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery("platform_user");
        selectQuery.addField("count(*)");
        selectQuery.addWhere("login = :login", email);

        int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
        if (count > 0) {
            Map<String, Object> error = Maps.newHashMap();
            error.put("body", new String[]{"email is not available", "you might want to reset your password"});
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            Map<String, Object> error = Maps.newHashMap();
            error.put("body", new String[]{"email is not valid"});
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        selectQuery = new SelectQuery("platform_role");
        selectQuery.addWhere("name = :name", "ecommerce_user");
        PlatformRole roleRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), PlatformRole.class);

        Long userId = Platform.randomUUIDLong("platform_user");

        InsertQuery insertQuery = null;

        insertQuery = new InsertQuery("platform_user");
        insertQuery.addValue("platform_user_id = :platform_user_id", userId);
        insertQuery.addValue("login = :login", email);
        insertQuery.addValue("password = MD5(:password)", "password", password);
        insertQuery.addValue("platform_role_id = :platform_role_id", roleRecord.getPlatformRoleId());
        insertQuery.addValue("system = :system", false);
        insertQuery.addValue("status = :status", "ACTIVE");
        insertQuery.addValue("full_name = :full_name", "");
        insertQuery.addValue("gender = :gender", gender);
        insertQuery.addValue("access_token = :access_token", Platform.randomUUIDString());
        named.update(insertQuery.toSQL(), insertQuery.getParam());

        insertQuery = new InsertQuery("ecommerce_cart");
        insertQuery.addValue("ecommerce_cart_id = :ecommerce_cart", Platform.randomUUIDLong("ecommerce_cart"));
        insertQuery.addValue("platform_user_id = :platform_user_id", userId);
        named.update(insertQuery.toSQL(), insertQuery.getParam());

        selectQuery = new SelectQuery("platform_user");
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
        selectQuery.addWhere("platform_user_id = :platform_user_id", userId);

        User data = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), User.class);

        return ResponseEntity.ok(data);
    }

}