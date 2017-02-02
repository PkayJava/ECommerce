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