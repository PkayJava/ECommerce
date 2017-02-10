package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.product.Product;
import com.angkorteam.ecommerce.mobile.product.ProductColor;
import com.angkorteam.ecommerce.mobile.product.ProductSize;
import com.angkorteam.ecommerce.mobile.product.ProductVariant;
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
import java.util.List;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class ProductsSingleServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsSingleServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/products/{id}", method = RequestMethod.GET)
    ResponseEntity<?> service(HttpServletRequest request, @PathVariable("id") Long id) throws Throwable {
        LOGGER.info("{}", this.getClass().getName());
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));

        SelectQuery productQuery = new SelectQuery("ecommerce_product");
        productQuery.addField("ecommerce_product.ecommerce_product_id AS id");
        productQuery.addField("ecommerce_product.ecommerce_product_id AS remoteId");
        productQuery.addField("'" + currency + "' AS currency");
        productQuery.addField("ecommerce_product.name AS name");
        productQuery.addField("ecommerce_product.normal_price AS price");
        productQuery.addField("ecommerce_category.ecommerce_category_id AS category");
        productQuery.addField("ecommerce_brand.name AS brand");
        productQuery.addField("ecommerce_product.price AS discountPrice");
        productQuery.addField("ecommerce_product.shipping_price AS shippingPrice");
        productQuery.addField("ecommerce_product.reference AS code");
        productQuery.addField("ecommerce_product.description AS description");
        productQuery.addField("platform_user.phone AS phoneNumber");
        productQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) AS mainImage");
        productQuery.addJoin(JoinType.LeftJoin, "platform_file", "ecommerce_product.main_image_platform_file_id = platform_file.platform_file_id");
        productQuery.addJoin(JoinType.LeftJoin, "ecommerce_category", "ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        productQuery.addJoin(JoinType.LeftJoin, "ecommerce_brand", "ecommerce_product.ecommerce_brand_id = ecommerce_brand.ecommerce_brand_id");
        productQuery.addJoin(JoinType.InnerJoin, "platform_user", "ecommerce_product.platform_user_id = platform_user.platform_user_id");
        productQuery.addWhere("ecommerce_product.ecommerce_product_id = :ecommerce_product_id", id);
        Product product = named.queryForObject(productQuery.toSQL(), productQuery.getParam(), Product.class);
        if (product.getShippingPrice() != null && product.getShippingPrice() > 0) {
            product.setDescription(product.getDescription() + "<br/><br/> <strong>* Note</strong><br/><br/>additional shipping fee : " + priceFormat.format(product.getShippingPrice()) + "/item.");
        }
        product.setPriceFormatted(priceFormat.format(product.getPrice()));
        if (product.getDiscountPrice() != null) {
            product.setDiscountPriceFormatted(priceFormat.format(product.getDiscountPrice()));
        }

        SelectQuery productImageQuery = new SelectQuery("ecommerce_product_image");
        productImageQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name)");
        productImageQuery.addJoin(JoinType.InnerJoin, "platform_file", "ecommerce_product_image.platform_file_id = platform_file.platform_file_id");
        productImageQuery.addWhere("ecommerce_product_image.ecommerce_product_id = :ecommerce_product_id", product.getId());
        List<String> productImages = named.queryForList(productImageQuery.toSQL(), productImageQuery.getParam(), String.class);

        SelectQuery productVariantQuery = new SelectQuery("ecommerce_product_variant");
        productVariantQuery.addField("ecommerce_color_id AS colorId");
        productVariantQuery.addField("ecommerce_size_id AS sizeId");
        productVariantQuery.addField("ecommerce_product_variant_id AS id");
        productVariantQuery.addField("reference AS code");
        productVariantQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", product.getId());
        product.setVariants(named.queryForList(productVariantQuery.toSQL(), productVariantQuery.getParam(), ProductVariant.class));
        if (product.getVariants() != null && !product.getVariants().isEmpty()) {
            for (ProductVariant variant : product.getVariants()) {
                SelectQuery productVariantImageQuery = new SelectQuery("ecommerce_product_variant_image");
                productVariantImageQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name)");
                productVariantImageQuery.addJoin(JoinType.InnerJoin, "platform_file", "ecommerce_product_variant_image.platform_file_id = platform_file.platform_file_id");
                productVariantImageQuery.addWhere("ecommerce_product_variant_image.ecommerce_product_variant_id = :ecommerce_product_variant_id", variant.getId());
                variant.setImages(named.queryForList(productVariantImageQuery.toSQL(), productVariantImageQuery.getParam(), String.class));
                if (variant.getImages() != null) {
                    variant.getImages().add(0, product.getMainImage());
                }

                SelectQuery productVariantColorQuery = new SelectQuery("ecommerce_color");
                productVariantColorQuery.addField("ecommerce_color.ecommerce_color_id AS id");
                productVariantColorQuery.addField("ecommerce_color.ecommerce_color_id AS remoteId");
                productVariantColorQuery.addField("ecommerce_color.value AS value");
                productVariantColorQuery.addField("ecommerce_color.code AS code");
                productVariantColorQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) AS img");
                productVariantColorQuery.addJoin(JoinType.LeftJoin, "platform_file", "ecommerce_color.img_platform_file_id = platform_file.platform_file_id");
                productVariantColorQuery.addWhere("ecommerce_color.ecommerce_color_id = :ecommerce_color_id", variant.getColorId());
                variant.setColor(named.queryForObject(productVariantColorQuery.toSQL(), productVariantColorQuery.getParam(), ProductColor.class));

                SelectQuery productVariantSizeQuery = new SelectQuery("ecommerce_size");
                productVariantSizeQuery.addField("ecommerce_size_id AS id");
                productVariantSizeQuery.addField("ecommerce_size_id AS remoteId");
                productVariantSizeQuery.addField("value");
                productVariantSizeQuery.addWhere("ecommerce_size_id = :size", variant.getSizeId());
                variant.setSize(named.queryForObject(productVariantSizeQuery.toSQL(), productVariantSizeQuery.getParam(), ProductSize.class));
            }
        }
        String include = request.getParameter("include");
        if ("related".equals(include)) {
            SelectQuery productRelatedQuery = new SelectQuery("ecommerce_product_related");
            productRelatedQuery.addField("ecommerce_product.ecommerce_product_id AS id");
            productRelatedQuery.addField("ecommerce_product.ecommerce_product_id AS remoteId");
            productRelatedQuery.addField("ecommerce_product.name AS name");
            productRelatedQuery.addField("ecommerce_product.normal_price AS price");
            productRelatedQuery.addField("ecommerce_product.discount_price AS discountPrice");
            productRelatedQuery.addField("CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) AS mainImage");
            productRelatedQuery.addJoin(JoinType.InnerJoin, "ecommerce_product", "ecommerce_product_related.related_ecommerce_product_id = ecommerce_product.ecommerce_product_id");
            productRelatedQuery.addJoin(JoinType.LeftJoin, "platform_file", "ecommerce_product.main_image_platform_file_id = platform_file.platform_file_id");
            productRelatedQuery.addWhere("ecommerce_product_related.ecommerce_product_id = :ecommerce_product_id", product.getId());
            product.setRelated(named.queryForList(productRelatedQuery.toSQL(), productRelatedQuery.getParam(), Product.class));
            if (product.getRelated() != null && !product.getRelated().isEmpty()) {
                for (Product related : product.getRelated()) {
                    related.setPriceFormatted(priceFormat.format(related.getPrice()));
                    if (related.getDiscountPrice() != null) {
                        related.setDiscountPriceFormatted(priceFormat.format(related.getDiscountPrice()));
                    }
                }
            }
        }

        return ResponseEntity.ok(product);
    }

}
