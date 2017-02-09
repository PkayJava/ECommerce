CREATE TABLE `ecommerce_banner` (

  `ecommerce_banner_id`        BIGINT(19)  NOT NULL,
  `image_url_platform_file_id` BIGINT(19)  NOT NULL,
  `ecommerce_product_id`       BIGINT(19),
  `ecommerce_category_id`      BIGINT(19),
  `name`                       VARCHAR(255),
  `type`                       VARCHAR(10) NOT NULL,
  `order`                      INT(11),
  `enabled`                    BIT(1),

  KEY (`name`),
  KEY (`enabled`),
  KEY (`type`),
  KEY (`ecommerce_product_id`),
  KEY (`ecommerce_category_id`),
  KEY (`image_url_platform_file_id`),
  KEY (`order`),
  PRIMARY KEY (`ecommerce_banner_id`)
);