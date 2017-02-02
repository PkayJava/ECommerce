CREATE TABLE `ecommerce_page` (

  `ecommerce_page_id` BIGINT(19)    NOT NULL,
  `title`             VARCHAR(255)  NOT NULL,
  `text`              VARCHAR(1000) NOT NULL,
  `order`             INT(11),

  KEY (`order`),
  KEY (`text`),
  UNIQUE KEY (`title`),
  PRIMARY KEY (`ecommerce_page_id`)
);