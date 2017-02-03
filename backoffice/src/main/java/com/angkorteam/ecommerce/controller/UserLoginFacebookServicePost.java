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
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by socheatkhauv on 28/1/17.
// */
//@Controller
//public class UserLoginFacebookServicePost {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginFacebookServicePost.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(value = "/{shop}/login/facebook", method = RequestMethod.POST)
//    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
////        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
////
////        InputStreamReader stream = new InputStreamReader(request.getInputStream());
////        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);
////
////        LOGGER.info(this.gson.toJson(requestBody));
////
////        try {
////            String facebookProfile = "https://graph.facebook.com/me?access_token=" + requestBody.facebookAccessToken;
////            LOGGER.info("facebook profile info {}", facebookProfile);
////            GetRequest req = Unirest.get(facebookProfile);
////            String json = req.asString().getBody();
////            LOGGER.info("facebook {}", json);
////            Facebook facebook = this.gson.fromJson(json, Facebook.class);
////
////            if (Strings.isNullOrEmpty(facebook.email)) {
////                List<String> errors = Lists.newArrayList();
////                errors.add("Facebook login will be available soon");
////                Map<String, Object> error = Maps.newHashMap();
////                error.put("body", errors);
////                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
////            }
////
////            SelectQuery selectQuery = null;
////            selectQuery = new SelectQuery("user");
////            selectQuery.addWhere("login = ?", facebook.email);
////
////            User user = selectQuery.queryForObject(connection, new UserMapper());
////            if (user == null) {
////                selectQuery = new SelectQuery("role");
////                selectQuery.addWhere("name = ?", "ecommerce_user");
////                Role roleRecord = selectQuery.queryForObject(connection, new RoleMapper());
////
////                InsertQuery insertQuery = new InsertQuery("");
////                insertQuery.addField("login", "?", facebook.email);
////                insertQuery.addField("password", "MD5(?)", RandomStringUtils.randomAlphabetic(20));
////                insertQuery.addField("role_id", "?", roleRecord.getRoleId());
////                insertQuery.addField("system", "?", false);
////                insertQuery.addField("status", "?", "ACTIVE");
////                insertQuery.addField("full_name", "?", facebook.name);
////                insertQuery.addField("gender", "?", facebook.gender);
////                insertQuery.addField("access_token", "?", System.randomUUIDString(connection));
////                insertQuery.executeUpdate(connection);
////            }
////
////            InsertQuery insertQuery = null;
////
////            selectQuery = new SelectQuery("ecommerce_cart");
////            selectQuery.addField("count(*)");
////            selectQuery.addWhere("user_id = ?", user.getUserId());
////            int count = selectQuery.queryForObject(connection, int.class);
////            if (count == 0) {
////                insertQuery = new InsertQuery("ecommerce_cart");
////                insertQuery.addField("user_id", "?", user.getUserId());
////                insertQuery.executeUpdate(connection);
////            }
////
////            selectQuery = new SelectQuery("ecommerce_wishlist");
////            selectQuery.addField("count(*)");
////            selectQuery.addWhere("user_id = ?", user.getUserId());
////            count = selectQuery.queryForObject(connection, int.class);
////            if (count == 0) {
////                insertQuery = new InsertQuery("ecommerce_wishlist");
////                insertQuery.addField("user_id", "?", user.getUserId());
////                insertQuery.executeUpdate(connection);
////            }
////
////            BeanPropertyRowMapper<ResponseBody> mapper = new BeanPropertyRowMapper<>(ResponseBody.class);
////
////            selectQuery = new SelectQuery("user");
////            selectQuery.addField("access_token accessToken");
////            selectQuery.addField("entity_id id");
////            selectQuery.addField("full_name name");
////            selectQuery.addField("street");
////            selectQuery.addField("country");
////            selectQuery.addField("city");
////            selectQuery.addField("house_number");
////            selectQuery.addField("houseNumber");
////            selectQuery.addField("zip");
////            selectQuery.addField("login email");
////            selectQuery.addField("phone");
////            selectQuery.addField("gender");
////            selectQuery.addWhere("user_id = ?", user.getUserId());
////
////            ResponseBody responseBody = selectQuery.queryForObject(connection, mapper);
////            return ResponseEntity.ok(responseBody);
////
////        } catch (UnirestException e) {
////            Map<String, Object> error = Maps.newHashMap();
////            error.put("body", new String[]{"please try again"});
////            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
////        }
//        return null;
//
//    }
//
//    public static class Facebook {
//
//        @Expose
//        @SerializedName("id")
//        String id;
//
//        @Expose
//        @SerializedName("email")
//        String email;
//
//        @Expose
//        @SerializedName("gender")
//        String gender;
//
//        @Expose
//        @SerializedName("name")
//        String name;
//
//    }
//
//    public static class RequestBody {
//
//        @Expose
//        @SerializedName("fb_id")
//        String facebookId;
//
//        @Expose
//        @SerializedName("fb_access_token")
//        String facebookAccessToken;
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
//
//    }
//
//}
