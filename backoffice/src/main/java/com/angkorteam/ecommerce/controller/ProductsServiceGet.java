package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.product.Product;
import com.angkorteam.ecommerce.mobile.product.ProductListResponse;
import com.angkorteam.ecommerce.model.EcommerceCategory;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.SortType;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.mobile.Metadata;
import com.angkorteam.platform.mobile.filtr.*;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class ProductsServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/products", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request, @PathVariable("shop") String shop) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));

        String color = request.getParameter("color");
        String size = request.getParameter("size");
        String brand = request.getParameter("brand");
        String price = request.getParameter("price");
        String sort = request.getParameter("sort");
        Long offset = ServletRequestUtils.getLongParameter(request, "offset", 0l);
        String category = request.getParameter("category");
        String search = request.getParameter("search");

        LOGGER.info("color {}", request.getParameter("color"));
        LOGGER.info("size {}", request.getParameter("size"));
        LOGGER.info("price {}", request.getParameter("price"));
        LOGGER.info("sort {}", request.getParameter("sort"));
        LOGGER.info("search {}", request.getParameter("search"));
        LOGGER.info("category {}", request.getParameter("category"));
        LOGGER.info("brand {}", request.getParameter("brand"));

        SelectQuery productQuery = new SelectQuery("ecommerce_product");
        productQuery.addJoin(JoinType.LeftJoin, "ecommerce_category", "ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        productQuery.addJoin(JoinType.LeftJoin, "ecommerce_brand", "ecommerce_product.ecommerce_brand_id = ecommerce_brand.ecommerce_brand_id");
        productQuery.addJoin(JoinType.LeftJoin, "platform_file AS main_image", "ecommerce_product.main_image_platform_file_id = main_image.platform_file_id");
        productQuery.addJoin(JoinType.LeftJoin, "platform_file AS main_image_high_res", "ecommerce_product.main_image_high_res_platform_file_id = main_image_high_res.platform_file_id");

        productQuery.addField("'" + currency + "' AS currency");
        productQuery.addField("ecommerce_product.ecommerce_product_id AS id");
        productQuery.addField("ecommerce_product.url AS url");
        productQuery.addField("ecommerce_product.name AS name");
        productQuery.addField("ecommerce_product.normal_price AS price");
        productQuery.addField("ecommerce_category.ecommerce_category_id AS category");
        productQuery.addField("ecommerce_product.price AS discountPrice");
        productQuery.addField("ecommerce_brand.name AS brand");
        productQuery.addField("ecommerce_product.reference AS code");
        productQuery.addField("ecommerce_product.description AS description");
        productQuery.addField("CONCAT('" + asset + "', '/api/resource', main_image.path, '/', main_image.name) AS mainImage");
        productQuery.addField("CONCAT('" + asset + "', '/api/resource', main_image_high_res.path, '/', main_image_high_res.name) AS mainImageHighRes");

        SelectQuery priceQuery = new SelectQuery("ecommerce_product");
        priceQuery.addField("min(price) AS min");
        priceQuery.addField("max(price) AS max");
        if (!Strings.isNullOrEmpty(category)) {
            priceQuery.addJoin(JoinType.InnerJoin, "ecommerce_category", "ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        }

        SelectQuery brandQuery = new SelectQuery("ecommerce_product");
        brandQuery.addField("max(ecommerce_brand.ecommerce_brand_id) AS id");
        brandQuery.addField("max(ecommerce_brand.name) AS value");
        if (!Strings.isNullOrEmpty(category)) {
            brandQuery.addJoin(JoinType.InnerJoin, "ecommerce_category", "ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        }
        brandQuery.addJoin(JoinType.InnerJoin, "ecommerce_brand", "ecommerce_product.ecommerce_brand_id = ecommerce_brand.ecommerce_brand_id");
        brandQuery.addGroupBy("ecommerce_brand.name");
        brandQuery.addOrderBy("ecommerce_brand.name");

        SelectQuery colorQuery = new SelectQuery("ecommerce_product");
        colorQuery.addField("max(ecommerce_color.ecommerce_color_id) AS id");
        colorQuery.addField("max(ecommerce_color.value) AS `value`");
        colorQuery.addField("max(ecommerce_color.code) AS `code`");
        colorQuery.addField("max(CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name)) AS img");
        if (!Strings.isNullOrEmpty(category)) {
            colorQuery.addJoin(JoinType.InnerJoin, "ecommerce_category", "ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        }
        colorQuery.addJoin(JoinType.InnerJoin, "ecommerce_product_variant", "ecommerce_product.ecommerce_product_id = ecommerce_product_variant.ecommerce_product_id");
        colorQuery.addJoin(JoinType.InnerJoin, "ecommerce_color", "ecommerce_product_variant.ecommerce_color_id = ecommerce_color.ecommerce_color_id");
        colorQuery.addJoin(JoinType.LeftJoin, "platform_file", "ecommerce_color.img_platform_file_id = platform_file.platform_file_id");
        colorQuery.addGroupBy("ecommerce_color.value");
        colorQuery.addOrderBy("ecommerce_color.value");

        SelectQuery sizeQuery = new SelectQuery("ecommerce_product");
        sizeQuery.addField("max(ecommerce_size.ecommerce_size_id) AS id");
        sizeQuery.addField("max(ecommerce_size.value) AS `value`");
        if (!Strings.isNullOrEmpty(category)) {
            sizeQuery.addJoin(JoinType.InnerJoin, "ecommerce_category", "ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        }
        sizeQuery.addJoin(JoinType.InnerJoin, "ecommerce_product_variant", "ecommerce_product.ecommerce_product_id = ecommerce_product_variant.ecommerce_product_id");
        sizeQuery.addJoin(JoinType.InnerJoin, "ecommerce_size", "ecommerce_product_variant.ecommerce_size_id = ecommerce_size.ecommerce_size_id");
        sizeQuery.addGroupBy("ecommerce_size.value");
        sizeQuery.addOrderBy("ecommerce_size.value");

        SelectQuery selectQuery = null;
        if (category != null) {
            selectQuery = new SelectQuery("ecommerce_category");
            selectQuery.addWhere("ecommerce_category_id = :ecommerce_category_id", category);
            EcommerceCategory categoryRecord = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceCategory.class);
            productQuery.addWhere("ecommerce_category.full_code like :full_code1", categoryRecord.getFullCode() + "%");
            priceQuery.addWhere("ecommerce_category.full_code like :full_code2", categoryRecord.getFullCode() + "%");
            brandQuery.addWhere("ecommerce_category.full_code like :full_code2", categoryRecord.getFullCode() + "%");
            colorQuery.addWhere("ecommerce_category.full_code like :full_code3", categoryRecord.getFullCode() + "%");
            sizeQuery.addWhere("ecommerce_category.full_code like :full_code4", categoryRecord.getFullCode() + "%");
        }

        if (!Strings.isNullOrEmpty(search)) {
            productQuery.addWhere("ecommerce_product.name like :name1", "%" + search + "%");
            priceQuery.addWhere("ecommerce_product.name like :name2", "%" + search + "%");
            brandQuery.addWhere("ecommerce_product.name like :name3", "%" + search + "%");
            colorQuery.addWhere("ecommerce_product.name like :name4", "%" + search + "%");
            sizeQuery.addWhere("ecommerce_product.name like :name5", "%" + search + "%");
        }

        ProductListResponse data = new ProductListResponse();

        Metadata metadata = new Metadata();
        data.setMetadata(metadata);

        List<Object> filters = new ArrayList<>();
        metadata.setFilters(filters);

        PriceFilter priceFilter = new PriceFilter();
        filters.add(priceFilter);
        BrandFilter brandFilter = new BrandFilter();
        filters.add(brandFilter);
        ColorFilter colorFilter = new ColorFilter();
        filters.add(colorFilter);
        SizeFilter sizeFilter = new SizeFilter();
        filters.add(sizeFilter);

        Map<String, Object> priceRecord = named.queryForMap(priceQuery.toSQL(), priceQuery.getParam());
        priceFilter.getValues()[0] = 0;
        if (priceRecord.get("max") != null) {
            priceFilter.getValues()[1] = priceRecord.get("max");
        } else {
            priceFilter.getValues()[1] = 0d;
        }
        priceFilter.getValues()[2] = "$";

        List<BrandFilterValue> brands = named.queryForList(brandQuery.toSQL(), brandQuery.getParam(), BrandFilterValue.class);
        if (brands != null && !brands.isEmpty()) {
            brandFilter.getValues().addAll(brands);
        }

        List<ColorFilterValue> colors = named.queryForList(colorQuery.toSQL(), colorQuery.getParam(), ColorFilterValue.class);
        if (colors != null && !colors.isEmpty()) {
            colorFilter.getValues().addAll(colors);
        }

        List<SizeFilterValue> sizes = named.queryForList(sizeQuery.toSQL(), sizeQuery.getParam(), SizeFilterValue.class);
        if (sizes != null && !sizes.isEmpty()) {
            sizeFilter.getValues().addAll(sizes);
        }

        if (!Strings.isNullOrEmpty(color) || !Strings.isNullOrEmpty(size)) {
            productQuery.addJoin(JoinType.InnerJoin, "ecommerce_product_variant", "ecommerce_product.ecommerce_product_id = ecommerce_product_variant.ecommerce_product_id");
            if (!Strings.isNullOrEmpty(color)) {
                selectQuery = new SelectQuery("ecommerce_color");
                selectQuery.addWhere("ecommerce_color_id = :ecommerce_color_id", color);
                Map<String, Object> colorRecord = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
                productQuery.addJoin(JoinType.InnerJoin, "ecommerce_color", "ecommerce_product_variant.ecommerce_color_id = ecommerce_color.ecommerce_color_id");
                productQuery.addWhere("ecommerce_color.value = :value", colorRecord.get("value"));
            }
            if (!Strings.isNullOrEmpty(size)) {
                selectQuery = new SelectQuery("ecommerce_size");
                selectQuery.addWhere("ecommerce_size_id = :ecommerce_size_id", size);
                Map<String, Object> sizeRecord = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
                productQuery.addJoin(JoinType.InnerJoin, "ecommerce_size", "ecommerce_product_variant.ecommerce_size_id = ecommerce_size.ecommerce_size_id");
                productQuery.addWhere("ecommerce_size.value = :value", sizeRecord.get("value"));
            }
        }

        if (!Strings.isNullOrEmpty(brand)) {
            selectQuery = new SelectQuery("ecommerce_brand");
            selectQuery.addWhere("ecommerce_brand_id = :ecommerce_brand_id", brand);
            Map<String, Object> brandRecord = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            productQuery.addWhere("ecommerce_brand.name = :name", brandRecord.get("name"));
        }

        if (!Strings.isNullOrEmpty(price)) {
            int index = price.indexOf("|");
            Integer min = Integer.valueOf(price.substring(0, index));
            Integer max = Integer.valueOf(price.substring(index + 1));
            productQuery.addWhere("ecommerce_product.price BETWEEN :min AND :max", int.class, min, max);
        }

        productQuery.addWhere("ecommerce_product.ready = true");

        int total = named.queryForObject("select count(*) from (" + productQuery.toSQL() + ") pp", productQuery.getParam(), int.class);
        metadata.setRecordsCount(total);

        if (!Strings.isNullOrEmpty(sort)) {
            if ("price_ASC".equals(sort)) {
                productQuery.addOrderBy("ecommerce_product.price");
            } else if ("price_DESC".equals(sort)) {
                productQuery.addOrderBy("ecommerce_product.price", SortType.Desc);
            } else if ("popularity".equals(sort)) {
                productQuery.addOrderBy("ecommerce_product.popularity", SortType.Desc);
            } else if ("newest".equals(sort)) {
                productQuery.addOrderBy("ecommerce_product.last_modified", SortType.Desc);
            }
        }

        long limit = 4L;
        if (offset < 1L) {
            offset = 1L;
        }

        metadata.setLinks(Platform.buildLinks(request, total, limit));

        productQuery.setLimit((offset - 1) * limit, limit);

        List<Product> products = named.queryForList(productQuery.toSQL(), productQuery.getParam(), Product.class);
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
                product.setPriceFormatted(priceFormat.format(product.getPrice()));
                if (product.getDiscountPrice() != null) {
                    product.setDiscountPriceFormatted(priceFormat.format(product.getDiscountPrice()));
                }
            }
            data.setRecords(products);
        } else {
            data.setRecords(Lists.newArrayList());
        }

        return ResponseEntity.ok(data);
    }

}
