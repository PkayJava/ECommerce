CREATE TABLE `ecommerce_branch` (

  `ecommerce_branch_id` BIGINT(19)      NOT NULL,
  `name`                VARCHAR(255)    NOT NULL,
  `address`             VARCHAR(255),
  `note`                VARCHAR(1000),
  `longitude`           DECIMAL(15, 10) NOT NULL,
  `latitude`            DECIMAL(15, 10) NOT NULL,
  `enabled`             BIT(1),

  KEY (`address`),
  KEY (`enabled`),
  KEY (`longitude`),
  KEY (`latitude`),
  KEY (`note`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_branch_id`)
);