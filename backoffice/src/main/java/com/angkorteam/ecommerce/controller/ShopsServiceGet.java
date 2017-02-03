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
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by socheatkhauv on 28/1/17.
// */
//@Controller
//public class ShopsServiceGet {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ShopsServiceGet.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(path = "/{organization}/shops", method = RequestMethod.GET)
//    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
////        Connection connection = (Connection) request.getAttribute(ConnectionRequestListener.CONNECTION);
////        if (!ECommerce.hasAccess(connection, request, CartDeliveryInfoServiceGet.class)) {
////            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
////        }
////
////        SelectQuery selectQuery = null;
////        selectQuery = new SelectQuery("setting");
////        selectQuery.addField("value");
////        selectQuery.addWhere("`key` = ?", "asset");
////        String asset = selectQuery.queryForObject(connection, String.class);
////
////        selectQuery = new SelectQuery("setting");
////        selectQuery.addField("value");
////        selectQuery.addWhere("`key` = ?", "currency");
////        String currency = selectQuery.queryForObject(connection, String.class);
////
////        selectQuery = new SelectQuery("setting");
////        selectQuery.addField("value");
////        selectQuery.addWhere("`key` = ?", "price_format");
////        DecimalFormat priceFormat = new DecimalFormat(selectQuery.queryForObject(connection, String.class));
////
////        selectQuery = new SelectQuery("setting");
////        selectQuery.addField("value");
////        selectQuery.addWhere("`key` = ?", "datetime_format");
////        DateFormat datetimeFormat = new SimpleDateFormat(selectQuery.queryForObject(connection, String.class));
////
////        ResponseBody data = new ResponseBody();
////
////        BeanPropertyRowMapper<Shop> shopMapper = new BeanPropertyRowMapper<>(Shop.class);
////
////        selectQuery = new SelectQuery("ecommerce_shop");
////        selectQuery.addField("ecommerce_shop.entity_id id");
////        selectQuery.addField("ecommerce_shop.description description");
////        selectQuery.addField("ecommerce_shop.name name");
////        selectQuery.addField("ecommerce_shop.url url");
////        selectQuery.addField("CONCAT('${asset}', '/api/resource', logo_file.path, '/', logo_file.name) logo");
////        selectQuery.addField("ecommerce_shop.google_ua google");
////        selectQuery.addField("ecommerce_shop.language language");
////        selectQuery.addField("CONCAT('" + asset + "', '/api/resource', flag_icon_file.path, '/', flag_icon_file.name) flagIcon");
////        selectQuery.addJoin("LEFT JOIN file logo_file on ecommerce_shop.logo_file_id = logo_file.file_id");
////        selectQuery.addJoin("LEFT JOIN file flag_icon_file on ecommerce_shop.flag_icon_file_id = flag_icon_file.file_id");
////
////        List<Shop> records = selectQuery.queryForList(connection, shopMapper);
////        data.records = records;
////
////        return ResponseEntity.ok(data);
//        return null;
//    }
//
//    public static class Shop {
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
//        @SerializedName("description")
//        String description;
//
//        @Expose
//        @SerializedName("url")
//        String url;
//
//        @Expose
//        @SerializedName("logo")
//        String logo;
//
//        @Expose
//        @SerializedName("google_ua")
//        String google;
//
//        @Expose
//        @SerializedName("language")
//        String language;
//
//        @Expose
//        @SerializedName("currency")
//        String currency = "USD";
//
//        @Expose
//        @SerializedName("flag_icon")
//        String flagIcon;
//
//    }
//
//    public static class ResponseBody {
//
//        @Expose
//        @SerializedName("records")
//        List<Shop> records = new ArrayList<>();
//
//    }
//
//}