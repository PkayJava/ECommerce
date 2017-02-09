CREATE TABLE `ecommerce_brand` (

  `ecommerce_brand_id` BIGINT(19)   NOT NULL,
  `name`               VARCHAR(255) NOT NULL,
  `order`              INT(11),
  `enabled`            BIT(1),

  KEY (`order`),
  KEY (`enabled`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_brand_id`)
);