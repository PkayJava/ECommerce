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