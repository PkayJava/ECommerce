CREATE TABLE `ecommerce_vendor_order` (

  `ecommerce_vendor_order_id` BIGINT(19)     NOT NULL,
  `ecommerce_order_id`        BIGINT(19)     NOT NULL,
  `date_created`              DATETIME       NOT NULL,
  `order_status`              VARCHAR(255)   NOT NULL,
  `vendor_status`             VARCHAR(255)   NOT NULL,
  `platform_user_id`          BIGINT(19)     NOT NULL,
  `total`                     DECIMAL(15, 4) NOT NULL,

  KEY (`ecommerce_order_id`),
  KEY (`vendor_status`),
  KEY (`order_status`),
  KEY (`date_created`),
  KEY (`total`),
  KEY (`platform_user_id`),
  PRIMARY KEY (`ecommerce_vendor_order_id`)
);