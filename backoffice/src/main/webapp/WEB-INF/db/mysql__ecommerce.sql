-- # mysql__ecommerce_banner_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_banner` (

  `ecommerce_banner_id`        BIGINT(19)  NOT NULL,
  `image_url_platform_file_id` BIGINT(19)  NOT NULL,
  `ecommerce_product_id`       BIGINT(19),
  `ecommerce_category_id`      BIGINT(19),
  `name`                       VARCHAR(255),
  `type`                       VARCHAR(10) NOT NULL,
  `order`                      INT(11),

  KEY (`name`),
  KEY (`type`),
  KEY (`ecommerce_product_id`),
  KEY (`ecommerce_category_id`),
  KEY (`image_url_platform_file_id`),
  KEY (`order`),
  PRIMARY KEY (`ecommerce_banner_id`)
);


-- # mysql__ecommerce_branch_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_branch` (

  `ecommerce_branch_id` BIGINT(19)      NOT NULL,
  `name`                VARCHAR(255)    NOT NULL,
  `address`             VARCHAR(255),
  `note`                VARCHAR(1000),
  `longitude`           DECIMAL(15, 10) NOT NULL,
  `latitude`            DECIMAL(15, 10) NOT NULL,

  KEY (`address`),
  KEY (`longitude`),
  KEY (`latitude`),
  KEY (`note`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_branch_id`)
);


-- # mysql__ecommerce_branch_opening_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_branch_opening` (

  `ecommerce_branch_opening_id` BIGINT(19) NOT NULL,
  `ecommerce_branch_id`         BIGINT(19) NOT NULL,
  `day`                         VARCHAR(255),
  `opening`                     VARCHAR(255),

  KEY (`ecommerce_branch_id`),
  KEY (`day`),
  KEY (`opening`),
  PRIMARY KEY (`ecommerce_branch_opening_id`)
);


-- # mysql__ecommerce_branch_transport_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_branch_transport` (

  `ecommerce_branch_transport_id` BIGINT(19) NOT NULL,
  `ecommerce_branch_id`           BIGINT(19) NOT NULL,
  `icon_platform_file_id`         BIGINT(19),
  `text`                          VARCHAR(1000),

  KEY (`ecommerce_branch_id`),
  KEY (`icon_platform_file_id`),
  KEY (`text`),
  PRIMARY KEY (`ecommerce_branch_transport_id`)
);


-- # mysql__ecommerce_brand_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_brand` (

  `ecommerce_brand_id` BIGINT(19)   NOT NULL,
  `name`               VARCHAR(255) NOT NULL,
  `order`              INT(11),

  KEY (`order`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_brand_id`)
);


-- # mysql__ecommerce_cart_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_cart` (

  `ecommerce_cart_id`            BIGINT(19) NOT NULL,
  `platform_user_id`             BIGINT(19) NOT NULL,
  `ecommerce_discount_id`        BIGINT(19),
  `ecommerce_discount_coupon_id` BIGINT(19),

  KEY (`platform_user_id`),
  KEY (`ecommerce_discount_id`),
  KEY (`ecommerce_discount_coupon_id`),
  PRIMARY KEY (`ecommerce_cart_id`)
);


-- # mysql__ecommerce_cart_product_item_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_cart_product_item` (

  `ecommerce_cart_product_item_id` BIGINT(19) NOT NULL,
  `ecommerce_product_id`           BIGINT(19) NOT NULL,
  `ecommerce_product_variant_id`   BIGINT(19) NOT NULL,
  `ecommerce_cart_id`              BIGINT(19) NOT NULL,
  `quantity`                       INT(11)    NOT NULL,

  KEY (`ecommerce_product_variant_id`),
  KEY (`ecommerce_product_id`),
  KEY (`quantity`),
  KEY (`ecommerce_cart_id`),
  PRIMARY KEY (`ecommerce_cart_product_item_id`)
);


-- # mysql__ecommerce_category_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_category` (

  `ecommerce_category_id`        BIGINT(19)   NOT NULL,
  `name`                         VARCHAR(255) NOT NULL,
  `type`                         VARCHAR(255) NOT NULL,
  `order`                        INT(11),
  `code`                         VARCHAR(255) NOT NULL,
  `full_code`                    VARCHAR(255) NOT NULL,
  `path`                         VARCHAR(255) NOT NULL,
  `parent_path`                  VARCHAR(255) NOT NULL,
  `parent_ecommerce_category_id` BIGINT(19),

  KEY (`name`),
  KEY (`full_code`),
  KEY (`code`),
  KEY (`parent_path`),
  KEY (`path`),
  KEY (`type`),
  KEY (`order`),
  KEY (`parent_ecommerce_category_id`),
  PRIMARY KEY (`ecommerce_category_id`)
);


-- # mysql__ecommerce_color_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_color` (

  `ecommerce_color_id`   BIGINT(19)   NOT NULL,
  `value`                VARCHAR(255),
  `code`                 VARCHAR(255),
  `img_platform_file_id` BIGINT(19),
  `reference`            VARCHAR(100) NOT NULL,

  KEY (`value`),
  KEY (`code`),
  KEY (`img_platform_file_id`),
  UNIQUE KEY (`reference`),
  PRIMARY KEY (`ecommerce_color_id`)
);


-- # mysql__ecommerce_device_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_device` (

  `ecommerce_device_id` BIGINT(19)   NOT NULL,
  `device_token`       VARCHAR(255) NOT NULL,
  `platform`           VARCHAR(255) NOT NULL,
  `platform_user_id`   BIGINT(19),

  KEY (`platform_user_id`),
  UNIQUE KEY (`device_token`, `platform`),
  PRIMARY KEY (`ecommerce_device_id`)
);


-- # mysql__ecommerce_discount_coupon_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_discount_coupon` (

  `ecommerce_discount_coupon_id` BIGINT(19)   NOT NULL,
  `ecommerce_discount_id`        BIGINT(19)   NOT NULL,
  `code`                         VARCHAR(255) NOT NULL,
  `used`                         BIT(1)       NOT NULL,
  `used_date`                    DATETIME,
  `platform_user_id`             BIGINT(19),
  `ecommerce_order_id`           BIGINT(19),

  KEY (`ecommerce_discount_id`),
  KEY (`ecommerce_order_id`),
  KEY (`platform_user_id`),
  KEY (`used`),
  KEY (`used_date`),
  UNIQUE KEY (`code`),
  PRIMARY KEY (`ecommerce_discount_coupon_id`)
);


-- # mysql__ecommerce_discount_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_discount` (

  `ecommerce_discount_id` BIGINT(19)     NOT NULL,
  `name`                  VARCHAR(255)   NOT NULL,
  `type`                  VARCHAR(20)    NOT NULL,
  `value`                 DECIMAL(15, 4) NOT NULL,
  `min_cart_amount`       DECIMAL(15, 4) NOT NULL,
  `start_date`            DATETIME       NOT NULL,
  `end_date`              DATETIME       NOT NULL,
  `enabled`               BIT(1)         NOT NULL,

  KEY (`enabled`),
  KEY (`start_date`),
  KEY (`end_date`),
  KEY (`type`),
  KEY (`value`),
  KEY (`min_cart_amount`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_discount_id`)
);


-- # mysql__ecommerce_order_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_order` (

  `ecommerce_order_id`           BIGINT(19) NOT NULL,
  `ecommerce_shipping_id`        BIGINT(19) NOT NULL,
  `ecommerce_payment_id`         BIGINT(19) NOT NULL,
  `platform_user_id`             BIGINT(19) NOT NULL,
  `ecommerce_discount_coupon_id` BIGINT(19),
  `ecommerce_discount_id`        BIGINT(19),
  `coupon_type`                  VARCHAR(20),
  `coupon_value`                 DECIMAL(15, 4),
  `discount_amount`              DECIMAL(15, 4),
  `sub_total_amount`             DECIMAL(15, 4),
  `total_amount`                 DECIMAL(15, 4),
  `grand_total_amount`           DECIMAL(15, 4),
  `name`                         VARCHAR(255),
  `street`                       VARCHAR(255),
  `house_number`                 VARCHAR(255),
  `city`                         VARCHAR(255),
  `zip`                          VARCHAR(255),
  `email`                        VARCHAR(255),
  `phone`                        VARCHAR(255),
  `note`                         VARCHAR(255),
  `date_created`                 DATETIME,
  `buyer_status`                 VARCHAR(255),
  `order_status`                 VARCHAR(255),
  `ecommerce_region_id`          BIGINT(19),
  `shipping_name`                VARCHAR(255),
  `shipping_price`               DECIMAL(15, 4),
  `shipping_price_addon`         DECIMAL(15, 4),
  `payment_name`                 VARCHAR(255),
  `payment_price`                DECIMAL(15, 4),

  KEY (`ecommerce_discount_coupon_id`),
  KEY (`ecommerce_discount_id`),
  KEY (`coupon_type`),
  KEY (`coupon_value`),
  KEY (`ecommerce_shipping_id`),
  KEY (`ecommerce_payment_id`),
  KEY (`ecommerce_region_id`),
  KEY (`platform_user_id`),
  KEY (`name`),
  KEY (`street`),
  KEY (`house_number`),
  KEY (`city`),
  KEY (`zip`),
  KEY (`email`),
  KEY (`date_created`),
  KEY (`phone`),
  KEY (`grand_total_amount`),
  KEY (`total_amount`),
  KEY (`sub_total_amount`),
  KEY (`discount_amount`),
  KEY (`shipping_price_addon`),
  KEY (`note`),
  KEY (`shipping_name`),
  KEY (`shipping_price`),
  KEY (`buyer_status`),
  KEY (`order_status`),
  PRIMARY KEY (`ecommerce_order_id`)
);


-- # mysql__ecommerce_order_item_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_order_item` (

  `ecommerce_order_item_id`             BIGINT(19) NOT NULL,
  `ecommerce_order_id`                  BIGINT(19) NOT NULL,
  `ecommerce_category_id`               BIGINT(19) NOT NULL,
  `quantity`                            INT(11),
  `total_price`                         DECIMAL(15, 4),
  `ecommerce_product_id`                BIGINT(19) NOT NULL,
  `ecommerce_product_variant_id`        BIGINT(19),
  `product_url`                         VARCHAR(255),
  `product_name`                        VARCHAR(255),
  `product_price`                       DECIMAL(15, 4),
  `product_shipping_price`              DECIMAL(15, 4),
  `product_reference`                   VARCHAR(255),
  `product_discount_price`              DECIMAL(15, 4),
  `product_description`                 VARCHAR(255),
  `product_main_image`                  VARCHAR(255),
  `product_main_image_platform_file_id` BIGINT(19),
  `variant_reference`                   VARCHAR(255),
  `ecommerce_color_id`                  BIGINT(19),
  `color_value`                         VARCHAR(255),
  `color_code`                          VARCHAR(255),
  `color_img`                           VARCHAR(255),
  `color_reference`                     VARCHAR(255),
  `color_img_platform_file_id`          BIGINT(19),
  `ecommerce_size_id`                   BIGINT(19),
  `size_value`                          VARCHAR(255),
  `size_reference`                      VARCHAR(255),

  KEY (`ecommerce_order_id`),
  KEY (`ecommerce_category_id`),
  KEY (`quantity`),
  KEY (`total_price`),
  KEY (`ecommerce_product_id`),
  KEY (`ecommerce_product_variant_id`),
  KEY (`product_url`),
  KEY (`product_name`),
  KEY (`product_price`),
  KEY (`product_shipping_price`),
  KEY (`product_discount_price`),
  KEY (`product_description`),
  KEY (`product_main_image`),
  KEY (`product_main_image_platform_file_id`),
  KEY (`variant_reference`),
  KEY (`ecommerce_color_id`),
  KEY (`color_value`),
  KEY (`color_code`),
  KEY (`color_img`),
  KEY (`color_reference`),
  KEY (`color_img_platform_file_id`),
  KEY (`ecommerce_size_id`),
  KEY (`size_value`),
  KEY (`size_reference`),
  PRIMARY KEY (`ecommerce_order_item_id`)
);


-- # mysql__ecommerce_page_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_page` (

  `ecommerce_page_id` BIGINT(19)    NOT NULL,
  `title`             VARCHAR(255)  NOT NULL,
  `text`              VARCHAR(1000) NOT NULL,
  `order`             INT(11),

  KEY (`order`),
  KEY (`text`),
  UNIQUE KEY (`title`),
  PRIMARY KEY (`ecommerce_page_id`)
);


-- # mysql__ecommerce_payment_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_payment` (

  `ecommerce_payment_id` BIGINT(19)     NOT NULL,
  `name`                 VARCHAR(255)   NOT NULL,
  `description`          VARCHAR(255),
  `price`                DECIMAL(15, 4) NOT NULL,
  `type`                 VARCHAR(100)   NOT NULL,
  `client_param1`       VARCHAR(255),
  `client_param2`       VARCHAR(255),
  `client_param3`       VARCHAR(255),
  `client_param4`       VARCHAR(255),
  `client_param5`       VARCHAR(255),
  `server_param1`       VARCHAR(255),
  `server_param2`       VARCHAR(255),
  `server_param3`       VARCHAR(255),
  `server_param4`       VARCHAR(255),
  `server_param5`       VARCHAR(255),

  KEY (`name`),
  KEY (`type`),
  KEY (`description`),
  KEY (`price`),
  PRIMARY KEY (`ecommerce_payment_id`)
);


-- # mysql__ecommerce_product_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_product` (

  `ecommerce_product_id`                 BIGINT(19)   NOT NULL,
  `platform_user_id`                     BIGINT(19)   NOT NULL,
  `name`                                 VARCHAR(255),
  `price`                                DECIMAL(15, 4),
  `url`                                  VARCHAR(255),
  `ready`                                BIT(1),
  `shipping_price`                       DECIMAL(15, 4),
  `quantity`                             INT(11)      NOT NULL,
  `ecommerce_category_id`                BIGINT(19)   NOT NULL,
  `ecommerce_brand_id`                   BIGINT(19),
  `discount_price`                       DECIMAL(15, 4),
  `reference`                            VARCHAR(100) NOT NULL,
  `description`                          TEXT,
  `main_image_platform_file_id`          BIGINT(19),
  `main_image_high_res_platform_file_id` BIGINT(19),
  `last_modified`                        DATETIME,
  `popularity`                           INT(11),
  `normal_price`                         DECIMAL(15, 4),

  KEY (`main_image_platform_file_id`),
  KEY (`main_image_high_res_platform_file_id`),
  KEY (`discount_price`),
  KEY (`ecommerce_category_id`),
  KEY (`ecommerce_brand_id`),
  KEY (`url`),
  KEY (`shipping_price`),
  KEY (`quantity`),
  KEY (`price`),
  KEY (`name`),
  KEY (`ready`),
  KEY (`last_modified`),
  KEY (`popularity`),
  KEY (`normal_price`),
  KEY (`platform_user_id`),
  UNIQUE KEY (`reference`),
  FULLTEXT KEY (`description`),
  PRIMARY KEY (`ecommerce_product_id`)
);


-- # mysql__ecommerce_product_image_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_product_image` (

  `ecommerce_product_image_id` BIGINT(19) NOT NULL,
  `ecommerce_product_id`       BIGINT(19) NOT NULL,
  `name`                       VARCHAR(255),
  `platform_file_id`           BIGINT(19) NOT NULL,

  KEY (`name`),
  KEY (`platform_file_id`),
  KEY (`ecommerce_product_id`),
  PRIMARY KEY (`ecommerce_product_image_id`)
);


-- # mysql__ecommerce_product_related_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_product_related` (

  `ecommerce_product_related_id` BIGINT(19) NOT NULL,
  `ecommerce_product_id`         BIGINT(19) NOT NULL,
  `related_ecommerce_product_id` BIGINT(19) NOT NULL,

  UNIQUE KEY (`ecommerce_product_id`, `related_ecommerce_product_id`),
  PRIMARY KEY (`ecommerce_product_related_id`)
);


-- # mysql__ecommerce_product_variant_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_product_variant` (

  `ecommerce_product_variant_id` BIGINT(19)   NOT NULL,
  `ecommerce_product_id`         BIGINT(19)   NOT NULL,
  `quantity`                     INT(11)      NOT NULL,
  `reference`                    VARCHAR(100) NOT NULL,
  `ecommerce_color_id`           BIGINT(19)   NOT NULL,
  `ecommerce_size_id`            BIGINT(19)   NOT NULL,

  KEY (`ecommerce_product_id`),
  KEY (`quantity`),
  UNIQUE KEY (`reference`),
  UNIQUE KEY (`ecommerce_color_id`, `ecommerce_size_id`, `ecommerce_product_id`),
  PRIMARY KEY (`ecommerce_product_variant_id`)
);


-- # mysql__ecommerce_product_variant_image_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_product_variant_image` (

  `ecommerce_product_variant_image_id` BIGINT(19) NOT NULL,
  `ecommerce_product_variant_id`       BIGINT(19) NOT NULL,
  `name`                               VARCHAR(255),
  `platform_file_id`                   BIGINT(19) NOT NULL,
  `ecommerce_product_id`               BIGINT(19) NOT NULL,

  KEY (`name`),
  KEY (`ecommerce_product_id`),
  KEY (`platform_file_id`),
  KEY (`ecommerce_product_variant_id`),
  PRIMARY KEY (`ecommerce_product_variant_image_id`)
);


-- # mysql__ecommerce_shipping_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_shipping` (

  `ecommerce_shipping_id` BIGINT(19)     NOT NULL,
  `name`                  VARCHAR(255)   NOT NULL,
  `min_cart_amount`       DOUBLE(15, 4)  NOT NULL,
  `ecommerce_branch_id`   BIGINT(19),
  `description`           VARCHAR(255),
  `availability_time`     VARCHAR(255),
  `availability_date`     VARCHAR(255),
  `type`                  VARCHAR(255)   NOT NULL,
  `price`                 DECIMAL(15, 4) NOT NULL,

  KEY (`type`),
  KEY (`price`),
  UNIQUE KEY (`name`, `ecommerce_branch_id`),
  PRIMARY KEY (`ecommerce_shipping_id`)
);


-- # mysql__ecommerce_shipping_payment_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_shipping_payment` (

  `ecommerce_shipping_payment_id` BIGINT(19) NOT NULL,
  `ecommerce_shipping_id`         BIGINT(19) NOT NULL,
  `ecommerce_payment_id`          BIGINT(19) NOT NULL,

  UNIQUE KEY (`ecommerce_shipping_id`, `ecommerce_payment_id`),
  PRIMARY KEY (`ecommerce_shipping_payment_id`)
);


-- # mysql__ecommerce_shop_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_shop` (

  `ecommerce_shop_id`          BIGINT(19) NOT NULL,
  `name`                       VARCHAR(255),
  `description`                VARCHAR(255),
  `url`                        VARCHAR(255),
  `logo_platform_file_id`      BIGINT(19),
  `google_ua`                  VARCHAR(100),
  `language`                   VARCHAR(100),
  `flag_icon_platform_file_id` BIGINT(19),

  KEY (`flag_icon_platform_file_id`),
  KEY (`google_ua`),
  KEY (`language`),
  KEY (`logo_platform_file_id`),
  KEY (`description`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_shop_id`)
);


-- # mysql__ecommerce_size_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_size` (

  `ecommerce_size_id` BIGINT(19)   NOT NULL,
  `value`             VARCHAR(255),
  `reference`         VARCHAR(100) NOT NULL,

  KEY (`value`),
  UNIQUE KEY (`reference`),
  PRIMARY KEY (`ecommerce_size_id`)
);


-- # mysql__ecommerce_vendor_order_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_vendor_order` (

  `ecommerce_vendor_order_id` BIGINT(19)     NOT NULL,
  `ecommerce_order_id`        BIGINT(19)     NOT NULL,
  `date_created`              DATETIME       NOT NULL,
  `order_status`              VARCHAR(255)   NOT NULL,
  `vendor_status`             VARCHAR(255)   NOT NULL,
  `platform_user_id`          BIGINT(19)     NOT NULL,
  `total`                     DECIMAL(15, 4) NOT NULL,

  KEY (`ecommerce_order_id`),
  KEY (`vendor_status`),
  KEY (`order_status`),
  KEY (`date_created`),
  KEY (`total`),
  KEY (`platform_user_id`),
  PRIMARY KEY (`ecommerce_vendor_order_id`)
);


-- # mysql__ecommerce_vendor_order_item_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_vendor_order_item` (

  `ecommerce_vendor_order_item_id`      BIGINT(19) NOT NULL,
  `ecommerce_vendor_order_id`           BIGINT(19) NOT NULL,
  `ecommerce_category_id`               BIGINT(19) NOT NULL,
  `quantity`                            INT(11),
  `total_price`                         DECIMAL(15, 4),
  `ecommerce_product_id`                BIGINT(19) NOT NULL,
  `ecommerce_product_variant_id`        BIGINT(19),
  `product_url`                         VARCHAR(255),
  `product_name`                        VARCHAR(255),
  `product_price`                       DECIMAL(15, 4),
  `product_reference`                   VARCHAR(255),
  `product_discount_price`              DECIMAL(15, 4),
  `product_description`                 VARCHAR(255),
  `product_main_image`                  VARCHAR(255),
  `product_main_image_platform_file_id` BIGINT(19),
  `variant_reference`                   VARCHAR(255),
  `ecommerce_color_id`                  BIGINT(19),
  `color_value`                         VARCHAR(255),
  `color_code`                          VARCHAR(255),
  `color_img`                           VARCHAR(255),
  `color_reference`                     VARCHAR(255),
  `color_img_platform_file_id`          BIGINT(19),
  `ecommerce_size_id`                   BIGINT(19),
  `size_value`                          VARCHAR(255),
  `size_reference`                      VARCHAR(255),

  KEY (`ecommerce_vendor_order_id`),
  KEY (`ecommerce_category_id`),
  KEY (`quantity`),
  KEY (`total_price`),
  KEY (`ecommerce_product_id`),
  KEY (`ecommerce_product_variant_id`),
  KEY (`product_url`),
  KEY (`product_name`),
  KEY (`product_price`),
  KEY (`product_discount_price`),
  KEY (`product_description`),
  KEY (`product_main_image`),
  KEY (`product_main_image_platform_file_id`),
  KEY (`variant_reference`),
  KEY (`ecommerce_color_id`),
  KEY (`color_value`),
  KEY (`color_code`),
  KEY (`color_img`),
  KEY (`color_reference`),
  KEY (`color_img_platform_file_id`),
  KEY (`ecommerce_size_id`),
  KEY (`size_value`),
  KEY (`size_reference`),
  PRIMARY KEY (`ecommerce_vendor_order_item_id`)
);


-- # mysql__ecommerce_wishlist_ddl.sql
-- ################################################################
CREATE TABLE `ecommerce_wishlist` (

  `ecommerce_wishlist_id` BIGINT(19) NOT NULL,
  `platform_user_id`      BIGINT(19) NOT NULL,
  `ecommerce_product_id`  BIGINT(19) NOT NULL,

  UNIQUE KEY (`platform_user_id`, `ecommerce_product_id`),
  PRIMARY KEY (`ecommerce_wishlist_id`)
);


