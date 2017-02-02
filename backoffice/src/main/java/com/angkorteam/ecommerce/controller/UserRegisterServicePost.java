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
public class UserRegisterServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/users/register", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
//        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
//
//        InputStreamReader stream = new InputStreamReader(request.getInputStream());
//        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery("user");
//        selectQuery.addField("count(*)");
//        selectQuery.addWhere("login = ?", requestBody.email);
//
//        int count = selectQuery.queryForObject(connection, int.class);
//        if (count > 0) {
//            Map<String, Object> error = Maps.newHashMap();
//            error.put("body", new String[]{"email is not available", "you might want to reset your password"});
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        }
//
//        if (!EmailValidator.getInstance().isValid(requestBody.email)) {
//            Map<String, Object> error = Maps.newHashMap();
//            error.put("body", new String[]{"email is not valid"});
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        }
//
//        selectQuery = new SelectQuery("role");
//        selectQuery.addWhere("name = ?", "ecommerce_user");
//        Role roleRecord = selectQuery.queryForObject(connection, new RoleMapper());
//
//        InsertQuery insertQuery = null;
//
//        insertQuery = new InsertQuery("user");
//        insertQuery.addField("login", "?", requestBody.email);
//        insertQuery.addField("password", "MD5(?)", requestBody.password);
//        insertQuery.addField("role_id", "?", roleRecord.getRoleId());
//        insertQuery.addField("system", "?", false);
//        insertQuery.addField("status", "?", "ACTIVE");
//        insertQuery.addField("full_name", "?", "");
//        insertQuery.addField("gender", "?", requestBody.gender);
//        insertQuery.addField("access_token", "?", System.randomUUIDString(connection));
//        User user = insertQuery.executeUpdate(connection, new UserMapper());
//
//        insertQuery = new InsertQuery("ecommerce_cart");
//        insertQuery.addField("user_id", "?", user.getUserId());
//        insertQuery.executeUpdate(connection);
//
//        insertQuery = new InsertQuery("ecommerce_wishlist");
//        insertQuery.addField("user_id", "?", user.getUserId());
//        insertQuery.executeUpdate(connection);
//
//        BeanPropertyRowMapper<ResponseBody> mapper = new BeanPropertyRowMapper<>(ResponseBody.class);
//        selectQuery = new SelectQuery("user");
//        selectQuery.addField("access_token accessToken");
//        selectQuery.addField("entity_id id");
//        selectQuery.addField("full_name name");
//        selectQuery.addField("street");
//        selectQuery.addField("country");
//        selectQuery.addField("city");
//        selectQuery.addField("house_number");
//        selectQuery.addField("houseNumber");
//        selectQuery.addField("zip");
//        selectQuery.addField("login email");
//        selectQuery.addField("phone");
//        selectQuery.addField("gender");
//        selectQuery.addWhere("user_id = ?", user.getUserId());
//
//        ResponseBody responseBody = selectQuery.queryForObject(connection, mapper);
//
//        return ResponseEntity.ok(responseBody);
        return null;
    }

    public static class RequestBody {

        @Expose
        @SerializedName("email")
        String email;

        @Expose
        @SerializedName("gender")
        String gender;

        @Expose
        @SerializedName("password")
        String password;

    }

    public static class ResponseBody {

        @Expose
        @SerializedName("id")
        long id;

        @Expose
        @SerializedName("name")
        String name;

        @Expose
        @SerializedName("street")
        String street;

        @Expose
        @SerializedName("city")
        String city;

        @Expose
        @SerializedName("house_number")
        String houseNumber;

        @Expose
        @SerializedName("zip")
        String zip;

        @Expose
        @SerializedName("email")
        String email;

        @Expose
        @SerializedName("phone")
        String phone;

        @Expose
        @SerializedName("gender")
        String gender;

        @Expose
        @SerializedName("country")
        String country;

        @Expose
        @SerializedName("access_token")
        String accessToken;

    }

}