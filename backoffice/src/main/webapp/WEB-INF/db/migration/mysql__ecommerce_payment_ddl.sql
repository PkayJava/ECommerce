CREATE TABLE `ecommerce_payment` (

  `ecommerce_payment_id` BIGINT(19)     NOT NULL,
  `name`                VARCHAR(255)   NOT NULL,
  `description`         VARCHAR(255),
  `price`               DECIMAL(15, 4) NOT NULL,

  KEY (`name`),
  KEY (`description`),
  KEY (`price`),
  PRIMARY KEY (`ecommerce_payment_id`)
);