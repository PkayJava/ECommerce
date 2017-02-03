//package com.angkorteam.ecommerce.controller;
//
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
//import com.angkorteam.platform.Spring;
//import com.google.gson.Gson;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by socheatkhauv on 28/1/17.
// */
//@Controller
//class UserSingleServicePut {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserSingleServicePut.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(path = "/{shop}/users/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
////        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
////        if (!ECommerce.hasAccess(connection, request, CartDeliveryInfoServiceGet.class)) {
////            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
////        }
////
////        InputStreamReader stream = new InputStreamReader(request.getInputStream());
////        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);
////
////        UpdateQuery updateQuery = null;
////        updateQuery = new UpdateQuery("user");
////        Map<String, Object> params = Maps.newHashMap();
////        updateQuery.addField("street = ?", requestBody.street);
////        updateQuery.addField("house_number = ?", requestBody.houseNumber);
////        updateQuery.addField("zip = ?", requestBody.zip);
////        updateQuery.addField("phone = ?", requestBody.phone);
////        updateQuery.addField("city = ?", requestBody.city);
////        updateQuery.addField("full_name = ?", requestBody.name);
////        updateQuery.addWhere("user_id = ?", id);
////
////        updateQuery.executeUpdate(connection);
////
////        BeanPropertyRowMapper<ResponseBody> mapper = new BeanPropertyRowMapper<>(ResponseBody.class);
////
////        SelectQuery selectQuery = new SelectQuery("user");
////        selectQuery.addField("access_token accessToken");
////        selectQuery.addField("entity_id id");
////        selectQuery.addField("full_name name");
////        selectQuery.addField("street");
////        selectQuery.addField("country");
////        selectQuery.addField("city");
////        selectQuery.addField("house_number");
////        selectQuery.addField("houseNumber");
////        selectQuery.addField("zip");
////        selectQuery.addField("login email");
////        selectQuery.addField("phone");
////        selectQuery.addField("gender");
////        selectQuery.addWhere("user_id = ?", id);
////        ResponseBody responseBody = selectQuery.queryForObject(connection, mapper);
////        return ResponseEntity.ok(responseBody);
//        return null;
//    }
//
//    public static class RequestBody {
//
//        @Expose
//        @SerializedName("name")
//        String name;
//
//        @Expose
//        @SerializedName("street")
//        String street;
//
//        @Expose
//        @SerializedName("house_number")
//        String houseNumber;
//
//        @Expose
//        @SerializedName("city")
//        String city;
//
//        @Expose
//        @SerializedName("zip")
//        String zip;
//
//        @Expose
//        @SerializedName("email")
//        String email;
//
//        @Expose
//        @SerializedName("phone")
//        String phone;
//
//    }
//
//    public static class ResponseBody {
//
//        @Expose
//        @SerializedName("id")
//        long id;
//
//        @Expose
//        @SerializedName("name")
//        String name;
//
//        @Expose
//        @SerializedName("street")
//        String street;
//
//        @Expose
//        @SerializedName("city")
//        String city;
//
//        @Expose
//        @SerializedName("house_number")
//        String houseNumber;
//
//        @Expose
//        @SerializedName("zip")
//        String zip;
//
//        @Expose
//        @SerializedName("email")
//        String email;
//
//        @Expose
//        @SerializedName("phone")
//        String phone;
//
//        @Expose
//        @SerializedName("gender")
//        String gender;
//
//        @Expose
//        @SerializedName("country")
//        String country;
//
//        @Expose
//        @SerializedName("access_token")
//        String accessToken;
//    }
//
//}