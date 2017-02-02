CREATE TABLE `ecommerce_category` (

  `ecommerce_category_id`        BIGINT(19)   NOT NULL,
  `name`                         VARCHAR(255) NOT NULL,
  `type`                         VARCHAR(255) NOT NULL,
  `order`                        INT(11),
  `code`                         VARCHAR(255) NOT NULL,
  `full_code`                    VARCHAR(255) NOT NULL,
  `path`                         VARCHAR(255) NOT NULL,
  `parent_path`                  VARCHAR(255) NOT NULL,
  `parent_ecommerce_category_id` BIGINT(19),

  KEY (`name`),
  KEY (`full_code`),
  KEY (`code`),
  KEY (`parent_path`),
  KEY (`path`),
  KEY (`type`),
  KEY (`order`),
  KEY (`parent_ecommerce_category_id`),
  PRIMARY KEY (`ecommerce_category_id`)
);