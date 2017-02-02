CREATE TABLE `ecommerce_device` (

  `ecommerce_device_id` BIGINT(19)   NOT NULL,
  `device_token`       VARCHAR(255) NOT NULL,
  `platform`           VARCHAR(255) NOT NULL,
  `platform_user_id`   BIGINT(19),

  KEY (`platform_user_id`),
  UNIQUE KEY (`device_token`, `platform`),
  PRIMARY KEY (`ecommerce_device_id`)
);