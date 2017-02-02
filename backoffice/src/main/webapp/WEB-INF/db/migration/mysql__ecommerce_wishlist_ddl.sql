CREATE TABLE `ecommerce_wishlist` (

  `ecommerce_wishlist_id` BIGINT(19) NOT NULL,
  `platform_user_id`      BIGINT(19) NOT NULL,
  `ecommerce_product_id`  BIGINT(19) NOT NULL,

  UNIQUE KEY (`platform_user_id`, `ecommerce_product_id`),
  PRIMARY KEY (`ecommerce_wishlist_id`)
);