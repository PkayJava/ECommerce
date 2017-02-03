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
//public class RegisterNotificationServicePost {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNotificationServicePost.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(path = "/{shop}/devices", method = RequestMethod.POST)
//    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
////        InputStreamReader stream = new InputStreamReader(request.getInputStream());
////        RequestBody requestBody = this.gson.fromJson(stream, RequestBody.class);
////        User userRecord = null;
////
////        SelectQuery selectQuery = null;
////        String authorization = request.getHeader("Authorization");
////        if (!Strings.isNullOrEmpty(authorization)) {
////            byte[] base64Token = authorization.substring(6).getBytes("UTF-8");
////            byte[] decoded = Base64.decodeBase64(base64Token);
////            String token = new String(decoded, "UTF-8");
////            Integer delim = token.indexOf(":");
////            String accessToken = token.substring(0, delim);
////            selectQuery = new SelectQuery("user");
////            selectQuery.addWhere("access_token = ?", accessToken);
////            userRecord = selectQuery.queryForObject(connection, new UserMapper());
////        }
////
////        selectQuery = new SelectQuery("ecommerce_device");
////        selectQuery.addWhere("device_token = ?", requestBody.deviceToken);
////        selectQuery.addWhere("platform = ?", requestBody.platform);
////
////        ECommerceDevice deviceRecord = selectQuery.queryForObject(connection, new ECommerceDeviceMapper());
////        if (deviceRecord == null) {
////            InsertQuery insertQuery = new InsertQuery("ecommerce_device");
////            insertQuery.addField("device_token", "?", requestBody.deviceToken);
////            insertQuery.addField("platform", "?", requestBody.platform);
////            deviceRecord = insertQuery.executeUpdate(connection, new ECommerceDeviceMapper());
////        }
////
////        if (userRecord != null) {
////            UpdateQuery updateQuery = new UpdateQuery("ecommerce_device");
////            updateQuery.addField("user_id = ?", userRecord.getUserId());
////            updateQuery.addWhere("ecommerce_device_id = ?", deviceRecord.getECommerceDeviceId());
////            updateQuery.executeUpdate(connection);
////        }
////
////        ResponseBody responseBody = new ResponseBody();
////        responseBody.id = deviceRecord.getECommerceDeviceId();
////        responseBody.deviceToken = requestBody.deviceToken;
////        responseBody.platform = requestBody.platform;
////
////        return ResponseEntity.ok(responseBody);
//        return null;
//    }
//
//    public static class RequestBody {
//
//        @Expose
//        @SerializedName("device_token")
//        String deviceToken;
//
//        @Expose
//        @SerializedName("platform")
//        String platform;
//
//    }
//
//    public static class ResponseBody {
//
//        @Expose
//        @SerializedName("id")
//        Long id;
//
//        @Expose
//        @SerializedName("device_token")
//        String deviceToken;
//
//        @Expose
//        @SerializedName("platform")
//        String platform;
//
//    }
//
//}