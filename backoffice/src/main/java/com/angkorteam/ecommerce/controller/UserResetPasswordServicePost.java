package com.angkorteam.ecommerce.controller;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
//        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
//        InputStreamReader stream = new InputStreamReader(request.getInputStream());
//        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);
//        SelectQuery selectQuery = null;
//        selectQuery = new SelectQuery("user");
//        selectQuery.addField("count(*)");
//        selectQuery.addWhere("login = ?", requestBody.email);
//        int count = selectQuery.queryForObject(connection, int.class);
//        if (count == 0) {
//            Map<String, Object> error = Maps.newHashMap();
//            error.put("body", new String[]{"email is incorrect"});
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        } else {
//            Email email = new SimpleEmail();
////            email.setHostName("smtp.googlemail.com")
////            email.setSmtpPort(465)
////            email.setAuthenticator(new DefaultAuthenticator("", ""))
////            email.setSSLOnConnect(true)
////            email.setFrom("")
////            email.setSubject("")
////            email.setMsg("")
////            email.addTo(requestBody.email)
////            email.send()
////            this.jdbcTemplate.update("UPDATE user SET password = MD5('123123a') WHERE login = ?", requestBody.email)
//            Map<String, Object> error = Maps.newHashMap();
//            error.put("body", new String[]{"please contact admin for reset your password"});
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        }
        return null;
    }

    public static class RequestBody {

        @Expose
        @SerializedName("email")
        String email;

    }

}