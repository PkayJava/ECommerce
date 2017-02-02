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
public class UserLoginEmailServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginEmailServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/login/email", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
//        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
//
//        InputStreamReader stream = new InputStreamReader(request.getInputStream());
//        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);
//        BeanPropertyRowMapper<ResponseBody> mapper = new BeanPropertyRowMapper<>(ResponseBody.class);
//
//        SelectQuery selectQuery = new SelectQuery("user");
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
//        selectQuery.addWhere("login = ?", requestBody.email);
//        selectQuery.addWhere("password = MD5(?)", requestBody.password);
//
//        ResponseBody responseBody = selectQuery.queryForObject(connection, mapper);
//
//        if (responseBody == null) {
//            Map<String, Object> error = Maps.newHashMap();
//            error.put("body", new String[]{"Invalid password"});
//            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
//        }
//        return ResponseEntity.ok(responseBody);
        return null;
    }

    public static class RequestBody {

        @Expose
        @SerializedName("email")
        String email;

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