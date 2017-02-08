package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.User;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.gson.Gson;
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

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class UserSingleServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSingleServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, UserSingleServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        PlatformUser platformUser = Platform.getCurrentUser(request);
        if (!platformUser.getPlatformUserId().equals(id)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

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