package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.Banner;
import com.angkorteam.ecommerce.mobile.BannersResponse;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.mobile.Metadata;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BannersServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(BannersServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(path = "/{shop}/banners", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        long limit = 4L;
        long offset = ServletRequestUtils.getLongParameter(request, "offset", 1);
        if (offset < 1) {
            offset = 1;
        }

        String asset = Platform.getSetting("asset");

        BannersResponse data = new BannersResponse();

        Metadata metadata = new Metadata();
        int recordsCount = jdbcTemplate.queryForObject("select count(*) from ecommerce_banner", int.class);
        metadata.setLinks(Platform.buildLinks(request, recordsCount, offset));
        metadata.setRecordsCount(recordsCount);
        data.setMetadata(metadata);

        SelectQuery selectQuery = new SelectQuery("ecommerce_banner");
        selectQuery.addField("ecommerce_banner.ecommerce_banner_id AS id");
        selectQuery.addField("ecommerce_banner.name AS name");
        selectQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) AS imageUrl");
        selectQuery.addField("CONCAT(IF(ecommerce_banner.type = 'Category', 'list', 'detail'), ':', IF(ecommerce_product.ecommerce_product_id IS NULL, ecommerce_category.ecommerce_category_id, ecommerce_product.ecommerce_product_id)) AS target");
        selectQuery.addJoin(JoinType.LeftJoin, "platform_file", "ecommerce_banner.image_url_platform_file_id = platform_file.platform_file_id");
        selectQuery.addJoin(JoinType.LeftJoin, "ecommerce_product", "ecommerce_banner.ecommerce_product_id = ecommerce_product.ecommerce_product_id");
        selectQuery.addJoin(JoinType.LeftJoin, "ecommerce_category", "ecommerce_banner.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        selectQuery.addOrderBy("ecommerce_banner.`order`");
        selectQuery.setLimit((offset - 1) * limit, limit);
        List<Banner> records = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), Banner.class);
        if (records != null && !records.isEmpty()) {
            data.setRecords(records);
        } else {
            data.setRecords(Lists.newArrayList());
        }
        return ResponseEntity.ok(data);
    }

}