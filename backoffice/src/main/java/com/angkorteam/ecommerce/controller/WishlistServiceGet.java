//package com.angkorteam.ecommerce.controller;
//
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
//import com.angkorteam.platform.Spring;
//import com.google.gson.Gson;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// * Created by socheatkhauv on 28/1/17.
// */
//@Controller
//public class WishlistServiceGet {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistServiceGet.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(path = "/{shop}/wishlist", method = RequestMethod.GET)
//    public ResponseEntity<?> service(HttpServletRequest request) throws Throwable {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
////        if (!ECommerce.hasAccess(request, CartDeliveryInfoServiceGet.class)) {
////            throw new ServletException(String.valueOf(HttpStatus.FORBIDDEN.getReasonPhrase()));
////        }
////
////        SelectQuery selectQuery = null;
////        String asset = ECommerce.getSetting("asset");
////        String currency = ECommerce.getSetting("currency");
////        DecimalFormat priceFormat = new DecimalFormat(ECommerce.getSetting("price_format"));
////        DateFormat datetimeFormat = new SimpleDateFormat(ECommerce.getSetting("datetime_format"));
////
////        User currentUser = ECommerce.getCurrentUser(request);
////
////        ECommerceWishlist wishlistRecord = selectQuery.queryForObject(connection, new ECommerceWishlistMapper());
////
////        WishlistResponse responseBody = new WishlistResponse();
////        responseBody.id = wishlistRecord.getECommerceWishlistId();
//
////        selectQuery = new SelectQuery("ecommerce_wishlist");
////        selectQuery.addWhere("ecommerce_wishlist_id = ?",wishlistRecord.getECommerceWishlistId());
////        List<ECommerceWishlistIem> wishlistItemRecords = this.jdbcTemplate.queryForList("SELECT * FROM ecommerce_wishlist_item WHERE ecommerce_wishlist_id = ?", wishlistRecord.get("ecommerce_wishlist_id"))
////        responseBody.productCount = wishlistItemRecords.size()
////        responseBody.items = Lists.newArrayList()
////        if (wishlistItemRecords != null && !wishlistItemRecords.isEmpty()) {
////            for (Map<String, Object> wishlistItemRecord : wishlistItemRecords) {
////                WishlistItem item = new WishlistItem()
////                item.id = wishlistItemRecord.get("entity_id") as Long
////                item.variant = new WishlistProductVariant()
////
////                Map<String, Object> productRecord = jdbcTemplate.queryForMap("SELECT * FROM ecommerce_product WHERE ecommerce_product_id = ?", wishlistItemRecord.get("ecommerce_product_id"))
////                Map<String, Object> variantRecord = jdbcTemplate.queryForMap("SELECT * FROM ecommerce_product_variant WHERE ecommerce_product_id = ? LIMIT 1", wishlistItemRecord.get("ecommerce_product_id"))
////                Map<String, Object> categoryRecord = jdbcTemplate.queryForMap("SELECT * FROM ecommerce_category WHERE ecommerce_category_id = ?", productRecord.get("ecommerce_category_id"))
////
////                item.variant.id = variantRecord.get("entity_id") as Long
////                item.variant.productId = productRecord.get("entity_id") as Long
////                item.variant.name = productRecord.get("name")
////                item.variant.category = categoryRecord.get("entity_id") as Long
////                item.variant.price = productRecord.get("price") as Double
////                item.variant.priceFormatted = '$' + item.variant.price
////                if (productRecord.get("discount_price") != null) {
////                    item.variant.discountPrice = productRecord.get("discount_price") as Double
////                    item.variant.discountPriceFormatted = '$' + item.variant.discountPrice
////                }
////                item.variant.currency = "USD"
////                item.variant.code = productRecord.get("code")
////                item.variant.description = productRecord.get("description")
////                item.variant.mainImage = this.jdbcTemplate.queryForObject("SELECT CONCAT('${asset}', '/api/resource', file.path, '/', file.name) FROM file WHERE file.file_id = ?", String.class, productRecord.get("main_image_file_id"))
////                item.variant.mainImageHighRes = this.jdbcTemplate.queryForObject("SELECT CONCAT('${asset}', '/api/resource', file.path, '/', file.name) FROM file WHERE file.file_id = ?", String.class, productRecord.get("main_image_high_res_file_id"))
////                responseBody.items.add(item)
////            }
////        }
//
////        return ResponseEntity.ok(responseBody);
//        return null;
//    }
//
//    public static class WishlistResponse {
//
//        @Expose
//        @SerializedName("id")
//        Long id;
//
//        @Expose
//        @SerializedName("product_count")
//        Integer productCount;
//
//        @Expose
//        @SerializedName("items")
//        List<WishlistItem> items;
//
//    }
//
//    public static class WishlistItem {
//
//        @Expose
//        @SerializedName("id")
//        Long id;
//
//        @Expose
//        @SerializedName("variant")
//        WishlistProductVariant variant;
//    }
//
//    public static class WishlistProductVariant {
//
//        @Expose
//        @SerializedName("id")
//        Long id;
//
//        @Expose
//        @SerializedName("product_id")
//        Long productId;
//
//        @Expose
//        @SerializedName("name")
//        String name;
//
//        @Expose
//        @SerializedName("category")
//        Long category;
//
//        @Expose
//        @SerializedName("price")
//        Double price;
//
//        @Expose
//        @SerializedName("discount_price")
//        Double discountPrice;
//
//        @Expose
//        @SerializedName("price_formatted")
//        String priceFormatted;
//
//        @Expose
//        @SerializedName("discount_price_formatted")
//        String discountPriceFormatted;
//
//        @Expose
//        @SerializedName("currency")
//        String currency;
//
//        @Expose
//        @SerializedName("code")
//        String code;
//
//        @Expose
//        @SerializedName("description")
//        String description;
//
//        @Expose
//        @SerializedName("main_image")
//        String mainImage;
//
//        @Expose
//        @SerializedName("main_image_high_res")
//        String mainImageHighRes;
//
//    }
//
//}