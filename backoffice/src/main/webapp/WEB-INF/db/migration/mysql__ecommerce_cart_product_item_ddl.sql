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