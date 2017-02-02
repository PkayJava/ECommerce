CREATE TABLE `ecommerce_cart_discount_item` (

  `ecommerce_cart_discount_item_id` BIGINT(19) NOT NULL,
  `ecommerce_cart_id`               BIGINT(19) NOT NULL,
  `ecommerce_discount_id`           BIGINT(19) NOT NULL,
  `quantity`                        INT(11)    NOT NULL,

  KEY (`ecommerce_cart_id`),
  KEY (`quantity`),
  KEY (`ecommerce_discount_id`),
  PRIMARY KEY (`ecommerce_cart_discount_item_id`)
);