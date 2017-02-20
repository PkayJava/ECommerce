package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.Facebook;
import com.angkorteam.ecommerce.mobile.User;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformRole;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class UserLoginFacebookServicePost {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginFacebookServicePost.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(value = "/{shop}/login/facebook", method = RequestMethod.POST)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        RequestBody requestBody = this.gson.fromJson(request.getReader(), RequestBody.class);

        LOGGER.info(this.gson.toJson(requestBody));

        try {
            String facebookProfile = "https://graph.facebook.com/me?access_token=" + requestBody.facebookAccessToken;
            LOGGER.info("facebook profile info {}", facebookProfile);
            GetRequest req = Unirest.get(facebookProfile);
            String json = req.asString().getBody();
            LOGGER.info("facebook {}", json);
            Facebook facebook = this.gson.fromJson(json, Facebook.class);

            if (Strings.isNullOrEmpty(facebook.getEmail())) {
                List<String> errors = Lists.newArrayList();
                errors.add("Facebook login will be available soon");
                Map<String, Object> error = Maps.newHashMap();
                error.put("body", errors);
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            SelectQuery selectQuery = null;
            selectQuery = new SelectQuery("platform_user");
            selectQuery.addWhere("login = :login", facebook.getEmail());

            PlatformUser user = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), PlatformUser.class);
            Long userId = null;
            Boolean enabled = true;
            if (user == null) {
                selectQuery = new SelectQuery("platform_role");
                selectQuery.addWhere("name = :name", "ecommerce_user");
                PlatformRole roleRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), PlatformRole.class);
                userId = Platform.randomUUIDLong("platform_user");
                InsertQuery insertQuery = new InsertQuery("platform_user");
                insertQuery.addValue("platform_user_id = :platform_user_id", userId);
                insertQuery.addValue("login = :login", facebook.getEmail());
                insertQuery.addValue("password = MD5(:password)", "password", RandomStringUtils.randomAlphabetic(20));
                insertQuery.addValue("platform_role_id = :platform_role_id", roleRecord.getPlatformRoleId());
                insertQuery.addValue("system = :system", false);
                insertQuery.addValue("status = :status", "ACTIVE");
                insertQuery.addValue("full_name = :full_name", facebook.getName());
                insertQuery.addValue("gender = :gender", facebook.getGender());
                insertQuery.addValue("access_token = :access_token", Platform.randomUUIDString());
                named.update(insertQuery.toSQL(), insertQuery.getParam());
            } else {
                enabled = user.getEnabled();
                if (enabled == null) {
                    enabled = false;
                }
                userId = user.getPlatformUserId();
            }

            if (!enabled) {
                Map<String, Object> error = Maps.newHashMap();
                error.put("body", new String[]{"please try again"});
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            InsertQuery insertQuery = null;

            selectQuery = new SelectQuery("ecommerce_cart");
            selectQuery.addField("count(*)");
            selectQuery.addWhere("platform_user_id = ?", userId);
            int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            if (count == 0) {
                insertQuery = new InsertQuery("ecommerce_cart");
                insertQuery.addValue("ecommerce_cart_id = :ecommerce_cart_id", Platform.randomUUIDLong("ecommerce_cart"));
                insertQuery.addValue("platform_user_id = :platform_user_id", userId);
                named.update(insertQuery.toSQL(), insertQuery.getParam());
            }

            selectQuery = new SelectQuery("platform_user");
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
            selectQuery.addWhere("platform_user_id = :platform_user_id", userId);

            User data = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), User.class);

            return ResponseEntity.ok(data);

        } catch (UnirestException e) {
            Map<String, Object> error = Maps.newHashMap();
            error.put("body", new String[]{"please try again"});
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    public static class RequestBody {

        @Expose
        @SerializedName("fb_id")
        private String facebookId;

        @Expose
        @SerializedName("fb_access_token")
        private String facebookAccessToken;

        public String getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(String facebookId) {
            this.facebookId = facebookId;
        }

        public String getFacebookAccessToken() {
            return facebookAccessToken;
        }

        public void setFacebookAccessToken(String facebookAccessToken) {
            this.facebookAccessToken = facebookAccessToken;
        }
    }

}
