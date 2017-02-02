CREATE TABLE `platform_page` (

  `platform_page_id`   BIGINT(19)   NOT NULL,
  `platform_layout_id` BIGINT(19)   NOT NULL,
  `java_class`         VARCHAR(255) NOT NULL,
  `path`               VARCHAR(255) NOT NULL,
  `html_title`         VARCHAR(255) NOT NULL,
  `page_title`         VARCHAR(255) NOT NULL,
  `page_description`   VARCHAR(255) NOT NULL,
  `version`            INT(11)      NOT NULL DEFAULT 1,

  KEY (`platform_layout_id`),
  KEY (`html_title`),
  KEY (`page_title`),
  KEY (`version`),
  KEY (`page_description`),
  UNIQUE KEY (`java_class`),
  UNIQUE KEY (`path`),
  PRIMARY KEY (`platform_page_id`)
);