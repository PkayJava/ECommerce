CREATE TABLE `ecommerce_discount` (

  `ecommerce_discount_id` BIGINT(19)     NOT NULL,
  `name`                  VARCHAR(255)   NOT NULL,
  `type`                  VARCHAR(20)    NOT NULL,
  `value`                 DECIMAL(15, 4) NOT NULL,
  `min_cart_amount`       DECIMAL(15, 4) NOT NULL,
  `start_date`            DATETIME       NOT NULL,
  `end_date`              DATETIME       NOT NULL,
  `enabled`               BIT(1)         NOT NULL,

  KEY (`enabled`),
  KEY (`start_date`),
  KEY (`end_date`),
  KEY (`type`),
  KEY (`value`),
  KEY (`min_cart_amount`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_discount_id`)
);