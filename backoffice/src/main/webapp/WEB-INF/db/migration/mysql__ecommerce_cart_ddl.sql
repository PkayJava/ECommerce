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