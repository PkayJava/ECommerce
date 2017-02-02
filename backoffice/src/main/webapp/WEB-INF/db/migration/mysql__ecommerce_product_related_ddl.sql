CREATE TABLE `ecommerce_product_related` (

  `ecommerce_product_related_id` BIGINT(19) NOT NULL,
  `ecommerce_product_id`         BIGINT(19) NOT NULL,
  `related_ecommerce_product_id` BIGINT(19) NOT NULL,

  UNIQUE KEY (`ecommerce_product_id`, `related_ecommerce_product_id`),
  PRIMARY KEY (`ecommerce_product_related_id`)
);