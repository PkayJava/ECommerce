CREATE TABLE `ecommerce_shipping_payment` (

  `ecommerce_shipping_payment_id` BIGINT(19) NOT NULL,
  `ecommerce_shipping_id`         BIGINT(19) NOT NULL,
  `ecommerce_payment_id`          BIGINT(19) NOT NULL,

  UNIQUE KEY (`ecommerce_shipping_id`, `ecommerce_payment_id`),
  PRIMARY KEY (`ecommerce_shipping_payment_id`)
);