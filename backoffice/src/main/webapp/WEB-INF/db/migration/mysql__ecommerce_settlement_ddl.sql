CREATE TABLE `ecommerce_settlement` (

  `ecommerce_settlement_id` BIGINT(19)     NOT NULL,
  `ecommerce_order_id`      BIGINT(19)     NOT NULL,
  `ecommerce_payment_id`    BIGINT(19)     NOT NULL,
  `amount`                  DECIMAL(15, 4) NOT NULL,
  `status`                  VARCHAR(100)   NOT NULL,
  `payment_type`            VARCHAR(100)   NOT NULL,
  `server_param1`           VARCHAR(255),
  `server_param2`           VARCHAR(255),
  `server_param3`           VARCHAR(255),
  `server_param4`           VARCHAR(255),
  `server_param5`           VARCHAR(255),
  `transaction_param1`      VARCHAR(255),
  `transaction_param2`      VARCHAR(255),
  `transaction_param3`      VARCHAR(255),
  `transaction_param4`      VARCHAR(255),
  `transaction_param5`      VARCHAR(255),
  `date_created`            DATETIME       NOT NULL,

  KEY (`ecommerce_order_id`),
  KEY (`ecommerce_payment_id`),
  KEY (`amount`),
  KEY (`status`),
  KEY (`payment_type`),
  KEY (`date_created`),
  PRIMARY KEY (`ecommerce_settlement_id`)
);