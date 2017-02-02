CREATE TABLE `ecommerce_shipping` (

  `ecommerce_shipping_id` BIGINT(19)     NOT NULL,
  `name`                  VARCHAR(255)   NOT NULL,
  `min_cart_amount`       DOUBLE(15, 4)  NOT NULL,
  `ecommerce_branch_id`   BIGINT(19),
  `description`           VARCHAR(255),
  `availability_time`     VARCHAR(255),
  `availability_date`     VARCHAR(255),
  `type`                  VARCHAR(255)   NOT NULL,
  `price`                 DECIMAL(15, 4) NOT NULL,

  KEY (`type`),
  KEY (`price`),
  UNIQUE KEY (`name`, `ecommerce_branch_id`),
  PRIMARY KEY (`ecommerce_shipping_id`)
);