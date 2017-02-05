package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.User;
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
public class UserSingleServicePut {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSingleServicePut.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, UserSingleServicePut.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser platformUser = Platform.getCurrentUser(request);
        if (!platformUser.getPlatformUserId().equals(id)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBody = this.gson.fromJson(request.getReader(), type);

        String name = requestBody.get("name");
        String street = requestBody.get("street");
        String houseNumber = requestBody.get("house_number");
        String city = requestBody.get("city");
        String zip = requestBody.get("zip");
        String phone = requestBody.get("phone");


        UpdateQuery updateQuery = new UpdateQuery("platform_user");
        updateQuery.addValue("street = :street", street);
        updateQuery.addValue("house_number = :house_number", houseNumber);
        updateQuery.addValue("zip = :zip", zip);
        updateQuery.addValue("phone = :phone", phone);
        updateQuery.addValue("city = :city", city);
        updateQuery.addValue("full_name = :name", name);
        updateQuery.addWhere("platform_user_id = :platform_user_id", id);
        named.update(updateQuery.toSQL(), updateQuery.getParam());

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
        selectQuery.addWhere("platform_user_id = :platform_user_id", id);

        User data = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), User.class);
        return ResponseEntity.ok(data);
    }

}