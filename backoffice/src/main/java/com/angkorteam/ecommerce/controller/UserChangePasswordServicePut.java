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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
//        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
//        if (!ECommerce.hasAccess(connection, request, CartDeliveryInfoServiceGet.class)) {
//            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
//        }
//
//        InputStreamReader stream = new InputStreamReader(request.getInputStream());
//        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);
//
//        SelectQuery selectQuery = new SelectQuery("user");
//        selectQuery.addField("count(*)");
//        selectQuery.addWhere("password = MD5(?)", requestBody.oldPassword);
//        selectQuery.addWhere("user_id = ?", id);
//        int count = selectQuery.queryForObject(connection, int.class);
//        if (count == 0) {
//            Map<String, Object> error = Maps.newHashMap();
//            error.put("body", new String[]{"old password is incorrect"});
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        }
//        UpdateQuery updateQuery = new UpdateQuery("user");
//        updateQuery.addField("password = MD5(?)", requestBody.newPassword);
//        updateQuery.addWhere("user_id = ?", id);
//        updateQuery.executeUpdate(connection);
//
//        return ResponseEntity.ok(null);
        return null;
    }

    public static class RequestBody {

        @Expose
        @SerializedName("old_password")
        String oldPassword;

        @Expose
        @SerializedName("new_password")
        String newPassword;

    }

}
