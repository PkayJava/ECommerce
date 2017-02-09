package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.Page;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.gson.Gson;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class ShopsSingleServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopsSingleServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{organization}/shops/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        SelectQuery selectQuery = null;

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));

        selectQuery = new SelectQuery("ecommerce_shop");
        selectQuery.addField("ecommerce_shop.ecommerce_shop_id id");
        selectQuery.addField("ecommerce_shop.description description");
        selectQuery.addField("ecommerce_shop.name name");
        selectQuery.addField("ecommerce_shop.url url");
        selectQuery.addField("'" + currency + "' currency");
        selectQuery.addField("CONCAT('" + asset + "', '/api/resource', logo_file.path, '/', logo_file.name) logo");
        selectQuery.addField("ecommerce_shop.google_ua google");
        selectQuery.addField("ecommerce_shop.language language");
        selectQuery.addField("CONCAT('" + asset + "', '/api/resource', flag_icon_file.path, '/', flag_icon_file.name) flagIcon");
        selectQuery.addJoin(JoinType.LeftJoin, "LEFT JOIN platform_file logo_file", "ecommerce_shop.logo_platform_file_id = logo_file.platform_file_id");
        selectQuery.addJoin(JoinType.LeftJoin, "LEFT JOIN platform_file flag_icon_file", "ecommerce_shop.flag_icon_platform_file_id = flag_icon_file.platform_file_id");
        selectQuery.addWhere("ecommerce_shop.ecommerce_shop_id = :ecommerce_shop_id", id);
        Page data = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Page.class);
        return ResponseEntity.ok(data);
    }

}