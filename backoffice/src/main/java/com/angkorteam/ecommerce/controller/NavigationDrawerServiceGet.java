package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.vo.MenuVO;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.SortType;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class NavigationDrawerServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationDrawerServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/navigation/menu", method = RequestMethod.GET)
    public ResponseEntity<?> menu(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);

        List<MenuVO> menus = Lists.newArrayList();
        int order = 0;
        {
            MenuVO menu = new MenuVO();
            menu.setTitle("Just Arrived");
            menu.setMenuId(1);
            menu.setOrder(1);
            menu.setType("banner");
            menu.setGroupId(1);
            menus.add(menu);
        }

        {
            SelectQuery selectQuery = new SelectQuery("ecommerce_category menu");
            selectQuery.addField("menu.ecommerce_category_id AS menuId");
            selectQuery.addField("IF(max(item.ecommerce_category_id) IS NULL , 'category','menu') AS type");
            selectQuery.addField("menu.order");
            selectQuery.addField("menu.name AS title");
            selectQuery.addField("2 AS groupId");
            selectQuery.addJoin(JoinType.LeftJoin, "ecommerce_category item", "menu.ecommerce_category_id = item.parent_ecommerce_category_id");
            selectQuery.addWhere("menu.parent_ecommerce_category_id IS NULL");
            selectQuery.addOrderBy("`order`", SortType.Asc);
            List<MenuVO> categoryMenuVOs = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), MenuVO.class);
            if (categoryMenuVOs != null && !categoryMenuVOs.isEmpty()) {
                MenuVO lastMenu = categoryMenuVOs.get(categoryMenuVOs.size() - 1);
                order = lastMenu.getOrder();
                menus.addAll(categoryMenuVOs);
            }
        }

        {
            order++;
            MenuVO menu = new MenuVO();
            menu.setTitle("Brands");
            menu.setMenuId(2);
            menu.setType("brand");
            menu.setOrder(order);
            menu.setGroupId(3);
            menus.add(menu);
        }

        {
            order++;
            MenuVO menu = new MenuVO();
            menu.setTitle("Terms");
            menu.setMenuId(3);
            menu.setType("page");
            menu.setOrder(order);
            menu.setGroupId(4);
            menus.add(menu);
        }


        return ResponseEntity.ok(menus);
    }

    @RequestMapping(path = "/navigation/menu/item", method = RequestMethod.GET)
    public ResponseEntity<?> menuItem(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);

        String type = ServletRequestUtils.getStringParameter(request, "type", "menu");
        Integer id = ServletRequestUtils.getIntParameter(request, "id", 0);

        List<MenuVO> menus = Lists.newArrayList();

        if ("menu".equals(type)) {
            SelectQuery selectQuery = new SelectQuery("ecommerce_category");
            selectQuery.addField("ecommerce_category.ecommerce_category_id AS menuId");
            selectQuery.addField("'category' AS type");
            selectQuery.addField("ecommerce_category.order AS 'order'");
            selectQuery.addField("ecommerce_category.name AS title");
            selectQuery.addWhere("ecommerce_category.parent_ecommerce_category_id = :id", id);
            selectQuery.addOrderBy("`order`", SortType.Asc);
            List<MenuVO> categoryMenuVOs = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), MenuVO.class);
            if (categoryMenuVOs != null && !categoryMenuVOs.isEmpty()) {
                menus.addAll(categoryMenuVOs);
            }
        } else if ("brand".equals(type)) {
            SelectQuery selectQuery = new SelectQuery("ecommerce_brand");
            selectQuery.addField("ecommerce_brand_id AS menuId");
            selectQuery.addField("'category' AS type");
            selectQuery.addField("`order` AS 'order'");
            selectQuery.addField("`name` AS title");
            selectQuery.addOrderBy("`order`", SortType.Asc);
            List<MenuVO> brandMenuVOs = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), MenuVO.class);
            if (brandMenuVOs != null && !brandMenuVOs.isEmpty()) {
                menus.addAll(brandMenuVOs);
            }
        }

        return ResponseEntity.ok(menus);
    }

}
