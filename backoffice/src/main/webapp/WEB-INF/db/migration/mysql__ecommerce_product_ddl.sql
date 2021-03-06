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
  `enabled`                              BIT(1),

  KEY (`enabled`),
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