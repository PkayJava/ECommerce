CREATE TABLE `ecommerce_shop` (

  `ecommerce_shop_id`          BIGINT(19) NOT NULL,
  `name`                       VARCHAR(255),
  `description`                VARCHAR(255),
  `url`                        VARCHAR(255),
  `logo_platform_file_id`      BIGINT(19),
  `google_ua`                  VARCHAR(100),
  `language`                   VARCHAR(100),
  `flag_icon_platform_file_id` BIGINT(19),
  `enabled`                    BIT(1),

  KEY (`flag_icon_platform_file_id`),
  KEY (`enabled`),
  KEY (`google_ua`),
  KEY (`language`),
  KEY (`logo_platform_file_id`),
  KEY (`description`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`ecommerce_shop_id`)
);