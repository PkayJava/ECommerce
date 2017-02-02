package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.vo.BannerVO;
import com.angkorteam.ecommerce.vo.MetadataVO;
import com.angkorteam.ecommerce.vo.PaginationVO;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Spring;
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
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
        long limit = 4L;
        long page = ServletRequestUtils.getLongParameter(request, "page", 1);
        if (page < 1) {
            page = 1;
        }

        String asset = Platform.getSetting("asset");

        PaginationVO<BannerVO> paginationVO = new PaginationVO<>();

        paginationVO.setMetadata(new MetadataVO());

        int recordsCount = jdbcTemplate.queryForObject("select count(*) from ecommerce_banner", int.class);
        paginationVO.getMetadata().setLinks(Platform.buildLinks(request, recordsCount, limit));
        paginationVO.getMetadata().setRecordsCount(recordsCount);

        SelectQuery selectQuery = new SelectQuery("ecommerce_banner");
        selectQuery.addField("ecommerce_banner.ecommerce_banner_id AS id");
        selectQuery.addField("ecommerce_banner.name AS name");
        selectQuery.addField("CONCAT('" + asset + "', '/api/resource', file.path, '/', file.name) AS imageUrl");
        selectQuery.addField("CONCAT(IF(ecommerce_banner.type = 'Category', 'list', 'detail'), ':', IF(ecommerce_product.ecommerce_product_id IS NULL, ecommerce_category.ecommerce_category_id, ecommerce_product.ecommerce_product_id)) AS target");
        selectQuery.addJoin(JoinType.LeftJoin, "file", "ecommerce_banner.image_url_file_id = file.file_id");
        selectQuery.addJoin(JoinType.LeftJoin, "ecommerce_product", "ecommerce_banner.ecommerce_product_id = ecommerce_product.ecommerce_product_id");
        selectQuery.addJoin(JoinType.LeftJoin, "ecommerce_category", "ecommerce_banner.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        selectQuery.addOrderBy("ecommerce_banner.`order`");
        selectQuery.setLimit((page - 1) * limit, limit);
        List<BannerVO> records = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), BannerVO.class);
        if (records != null && !records.isEmpty()) {
            paginationVO.setRecords(records);
        }
        return ResponseEntity.ok(paginationVO);
    }

}