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