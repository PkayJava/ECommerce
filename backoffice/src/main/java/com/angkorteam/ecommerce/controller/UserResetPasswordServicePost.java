package com.angkorteam.ecommerce.controller;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
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
public class UserResetPasswordServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResetPasswordServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/users/reset-password", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBody = this.gson.fromJson(request.getReader(), type);


        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("user");
        selectQuery.addField("count(*)");
        selectQuery.addWhere("login = :login", requestBody.get("email"));
        int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
        if (count == 0) {
            Map<String, Object> error = Maps.newHashMap();
            error.put("body", new String[]{"email is incorrect"});
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else {
            String smtpUser = Platform.getSetting("smtp_user");
            String smtpPassword = Platform.getSetting("smtp_password");
            if (Strings.isNullOrEmpty(smtpUser) || Strings.isNullOrEmpty(smtpPassword)) {
                Map<String, Object> data = Maps.newHashMap();
                data.put("body", new String[]{"please contact admin for reset your password"});
                return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
            } else {
                try {
                    String newPassword = String.valueOf(RandomStringUtils.randomNumeric(4));
                    Email email = new SimpleEmail();
                    email.setHostName("smtp.googlemail.com");
                    email.setSmtpPort(465);
                    email.setAuthenticator(new DefaultAuthenticator(smtpUser, smtpPassword));
                    email.setSSLOnConnect(true);
                    email.setFrom(smtpUser);
                    email.setSubject("New Password");
                    email.setMsg("Your New Password : " + newPassword);
                    email.addTo(requestBody.get("email"));
                    email.send();
                    jdbcTemplate.update("UPDATE user SET password = MD5(?) WHERE login = ?", newPassword, requestBody.get("email"));
                    Map<String, Object> data = Maps.newHashMap();
                    data.put("body", new String[]{"please check your email for new password"});
                    return new ResponseEntity<>(data, HttpStatus.OK);
                } catch (EmailException e) {
                    Map<String, Object> data = Maps.newHashMap();
                    data.put("body", new String[]{"please contact admin for reset your password"});
                    return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
                }
            }
        }
    }

}