CREATE TABLE `ecommerce_color` (

  `ecommerce_color_id`   BIGINT(19)   NOT NULL,
  `value`                VARCHAR(255),
  `code`                 VARCHAR(255),
  `img_platform_file_id` BIGINT(19),
  `reference`            VARCHAR(100) NOT NULL,

  KEY (`value`),
  KEY (`code`),
  KEY (`img_platform_file_id`),
  UNIQUE KEY (`reference`),
  PRIMARY KEY (`ecommerce_color_id`)
);