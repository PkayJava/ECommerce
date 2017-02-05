package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.drawerMenu.DrawerItemCategory;
import com.angkorteam.ecommerce.mobile.drawerMenu.DrawerItemPage;
import com.angkorteam.ecommerce.mobile.drawerMenu.DrawerResponse;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.SortType;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.common.collect.Lists;
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
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class NavigationDrawerServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationDrawerServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/navigation_drawer", method = RequestMethod.GET)
    public ResponseEntity<?> menu(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        DrawerResponse data = new DrawerResponse();

        SelectQuery select = new SelectQuery("ecommerce_category");
        select.addField("ecommerce_category.ecommerce_category_id id");
        select.addField("ecommerce_category.type type");
        select.addField("ecommerce_category.ecommerce_category_id originalId");
        select.addField("ecommerce_category.name name");
        select.addWhere("ecommerce_category.parent_ecommerce_category_id IS NULL");
        select.addOrderBy("`order`", SortType.Asc);

        List<DrawerItemCategory> categoryNavigations = named.queryForList(select.toSQL(), select.getParam(), DrawerItemCategory.class);

        List<DrawerItemCategory> navigations = Lists.newArrayList();

        select = new SelectQuery("ecommerce_category");
        select.addField("ecommerce_category.ecommerce_category_id id");
        select.addField("'category' type");
        select.addField("ecommerce_category.ecommerce_category_id originalId");
        select.addField("ecommerce_category.name name");
        select.addWhere("ecommerce_category.parent_ecommerce_category_id = :id", "");
        select.addOrderBy("`order`", SortType.Asc);
        for (DrawerItemCategory categoryNavigation : categoryNavigations) {
            Map<String, Object> param = select.getParam();
            param.put("id", categoryNavigation.getId());
            List<DrawerItemCategory> children = named.queryForList(select.toSQL(), param, DrawerItemCategory.class);
            if (children != null && !children.isEmpty()) {
                DrawerItemCategory all = new DrawerItemCategory();
                all.setType("category");
                all.setName("All");
                all.setId(categoryNavigation.getId());
                all.setOriginalId(categoryNavigation.getOriginalId());
                children.add(0, all);
                categoryNavigation.setChildren(children);
            }
        }

        navigations.addAll(categoryNavigations);

        select = new SelectQuery("ecommerce_brand");
        select.addField("name");
        select.addField("ecommerce_brand_id id");
        select.addField("ecommerce_brand_id originalId");
        select.addField("'brand' type");
        select.addOrderBy("`order`", SortType.Asc);
        List<DrawerItemCategory> brandNavigations = named.queryForList(select.toSQL(), select.getParam(), DrawerItemCategory.class);
        if (brandNavigations != null && !brandNavigations.isEmpty()) {
            DrawerItemCategory navigation = new DrawerItemCategory();
            navigation.setName("Brand");
            navigation.setType("brand");
            navigation.setChildren(brandNavigations);
            navigations.add(navigation);
        }

        data.setNavigation(navigations);

        select = new SelectQuery("ecommerce_page");
        select.addField("ecommerce_page_id id");
        select.addField("title name");
        select.addOrderBy("`order`", SortType.Asc);
        List<DrawerItemPage> pages = named.queryForList(select.toSQL(), select.getParam(), DrawerItemPage.class);

        if (pages != null && !pages.isEmpty()) {
            data.setPages(pages);
        }

        return ResponseEntity.ok(data);

    }

}
