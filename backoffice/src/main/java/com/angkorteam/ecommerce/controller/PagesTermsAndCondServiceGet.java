package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.Page;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class PagesTermsAndCondServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PagesTermsAndCondServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/pages/terms", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        SelectQuery selectQuery = new SelectQuery("ecommerce_page");
        selectQuery.addField("ecommerce_page.ecommerce_page_id AS id");
        selectQuery.addField("ecommerce_page.title AS title");
        selectQuery.addField("ecommerce_page.text AS text");
        selectQuery.addWhere("lower(ecommerce_page.title) = lower(:title)", "title", "Terms");
        Page data = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Page.class);

        return ResponseEntity.ok(data);
    }

}
