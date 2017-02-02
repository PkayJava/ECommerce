CREATE TABLE `ecommerce_discount` (

  `ecommerce_discount_id` BIGINT(19)   NOT NULL,
  `name`                 VARCHAR(255) NOT NULL,
  `type`                 VARCHAR(255) NOT NULL,
  `value`                VARCHAR(255) NOT NULL,
  `value_formatted`      VARCHAR(255) NOT NULL,
  `min_cart_amount`      VARCHAR(255) NOT NULL,

  KEY (`type`),
  KEY (`value`),
  KEY (`value_formatted`),
  KEY (`min_cart_amount`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_discount_id`)
);