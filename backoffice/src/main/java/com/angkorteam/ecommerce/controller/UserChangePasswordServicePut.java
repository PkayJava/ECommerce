package com.angkorteam.ecommerce.controller;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class UserChangePasswordServicePut {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserChangePasswordServicePut.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/users/{id}/password", method = RequestMethod.PUT)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, UserChangePasswordServicePut.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser platformUser = Platform.getCurrentUser(request);
        if (!platformUser.getPlatformUserId().equals(id)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBody = this.gson.fromJson(request.getReader(), type);

        String oldPassword = requestBody.get("old_password");
        String newPassword = requestBody.get("new_password");

        SelectQuery selectQuery = new SelectQuery("platform_user");
        selectQuery.addField("count(*)");
        selectQuery.addWhere("password = MD5(:password)", "password", oldPassword);
        selectQuery.addWhere("platform_user_id = :platform_user_id", id);
        int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
        if (count == 0) {
            Map<String, Object> error = Maps.newHashMap();
            error.put("body", new String[]{"old password is incorrect"});
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        UpdateQuery updateQuery = new UpdateQuery("platform_user");
        updateQuery.addValue("password = MD5(:password)", "password", newPassword);
        updateQuery.addWhere("platform_user_id = :platform_user_id", id);
        named.update(updateQuery.toSQL(), updateQuery.getParam());

        Map<String, Object> data = Maps.newHashMap();
        data.put("body", new String[]{"password is updated"});
        return ResponseEntity.ok(data);
    }

}
