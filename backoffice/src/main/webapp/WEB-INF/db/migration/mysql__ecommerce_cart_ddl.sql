CREATE TABLE `ecommerce_cart` (

  `ecommerce_cart_id` BIGINT(19) NOT NULL,
  `platform_user_id`  BIGINT(19) NOT NULL,

  KEY (`platform_user_id`),
  PRIMARY KEY (`ecommerce_cart_id`)
);