CREATE TABLE `ecommerce_payment` (

  `ecommerce_payment_id` BIGINT(19)     NOT NULL,
  `name`                 VARCHAR(255)   NOT NULL,
  `description`          VARCHAR(255),
  `price`                DECIMAL(15, 4) NOT NULL,
  `type`                 VARCHAR(100)   NOT NULL,
  `client_param1`       VARCHAR(255),
  `client_param2`       VARCHAR(255),
  `client_param3`       VARCHAR(255),
  `client_param4`       VARCHAR(255),
  `client_param5`       VARCHAR(255),
  `server_param1`       VARCHAR(255),
  `server_param2`       VARCHAR(255),
  `server_param3`       VARCHAR(255),
  `server_param4`       VARCHAR(255),
  `server_param5`       VARCHAR(255),

  KEY (`name`),
  KEY (`type`),
  KEY (`description`),
  KEY (`price`),
  PRIMARY KEY (`ecommerce_payment_id`)
);