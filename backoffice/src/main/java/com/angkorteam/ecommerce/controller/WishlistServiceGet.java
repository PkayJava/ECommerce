package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.wishlist.WishlistItem;
import com.angkorteam.ecommerce.mobile.wishlist.WishlistProductVariant;
import com.angkorteam.ecommerce.mobile.wishlist.WishlistResponse;
import com.angkorteam.ecommerce.model.EcommerceCategory;
import com.angkorteam.ecommerce.model.EcommerceProduct;
import com.angkorteam.ecommerce.model.EcommerceProductVariant;
import com.angkorteam.ecommerce.model.EcommerceWishlist;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by socheatkhauv on 28/1/17.
 */
@Controller
public class WishlistServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/wishlist", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        if (!Platform.hasAccess(request, WishlistServiceGet.class)) {
            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
        }

        String asset = Platform.getSetting("asset");
        String currency = Platform.getSetting("currency");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));
        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));

        PlatformUser currentUser = Platform.getCurrentUser(request);

        SelectQuery selectQuery = null;

        WishlistResponse data = new WishlistResponse();
        data.setId(currentUser.getPlatformUserId());

        selectQuery = new SelectQuery("ecommerce_wishlist");
        selectQuery.addWhere("platform_user_id = :platform_user_id", currentUser.getPlatformUserId());
        List<EcommerceWishlist> wishlistItemRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceWishlist.class);
        data.setProductCount(wishlistItemRecords.size());

        List<WishlistItem> items = Lists.newArrayList();
        data.setItems(items);
        if (!wishlistItemRecords.isEmpty()) {
            for (EcommerceWishlist wishlistItemRecord : wishlistItemRecords) {
                WishlistItem item = new WishlistItem();
                item.setId(wishlistItemRecord.getEcommerceWishlistId());
                WishlistProductVariant variant = new WishlistProductVariant();
                item.setVariant(variant);

                EcommerceProduct productRecord = jdbcTemplate.queryForObject("SELECT * FROM ecommerce_product WHERE ecommerce_product_id = ?", EcommerceProduct.class, wishlistItemRecord.getEcommerceProductId());
                EcommerceProductVariant variantRecord = jdbcTemplate.queryForObject("SELECT * FROM ecommerce_product_variant WHERE ecommerce_product_id = ? LIMIT 1", EcommerceProductVariant.class, wishlistItemRecord.getEcommerceProductId());
                EcommerceCategory categoryRecord = jdbcTemplate.queryForObject("SELECT * FROM ecommerce_category WHERE ecommerce_category_id = ?", EcommerceCategory.class, productRecord.getEcommerceCategoryId());

                variant.setId(variantRecord.getEcommerceProductVariantId());
                variant.setProductId(productRecord.getEcommerceProductId());
                variant.setName(productRecord.getName());
                variant.setCategory(categoryRecord.getEcommerceCategoryId());
                variant.setPrice(productRecord.getPrice());
                variant.setPriceFormatted(priceFormat.format(variant.getPrice()));
                if (productRecord.getDiscountPrice() != null) {
                    variant.setDiscountPrice(productRecord.getDiscountPrice());
                    variant.setDiscountPriceFormatted(priceFormat.format(variant.getDiscountPrice()));
                }
                variant.setCurrency(currency);
                variant.setCode(productRecord.getReference());
                variant.setDescription(productRecord.getDescription());
                variant.setMainImage(jdbcTemplate.queryForObject("SELECT CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) FROM platform_file WHERE platform_file.platform_file_id = ?", String.class, productRecord.getMainImagePlatformFileId()));
                variant.setMainImageHighRes(jdbcTemplate.queryForObject("SELECT CONCAT('" + asset + "', '/api/resource', platform_file.path, '/', platform_file.name) FROM platform_file WHERE platform_file.platform_file_id = ?", String.class, productRecord.getMainImageHighResPlatformFileId()));
                items.add(item);
            }
        }

        return ResponseEntity.ok(data);
    }

}