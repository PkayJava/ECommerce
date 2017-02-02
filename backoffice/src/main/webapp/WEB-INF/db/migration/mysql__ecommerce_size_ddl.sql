CREATE TABLE `ecommerce_size` (

  `ecommerce_size_id` BIGINT(19)   NOT NULL,
  `value`             VARCHAR(255),
  `reference`         VARCHAR(100) NOT NULL,

  KEY (`value`),
  UNIQUE KEY (`reference`),
  PRIMARY KEY (`ecommerce_size_id`)
);