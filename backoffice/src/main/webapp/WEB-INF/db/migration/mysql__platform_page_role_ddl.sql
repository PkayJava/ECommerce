CREATE TABLE `platform_page_role` (

  `platform_page_role_id` BIGINT(19) NOT NULL,
  `platform_page_id`      BIGINT(19) NOT NULL,
  `platform_role_id`      BIGINT(19) NOT NULL,

  UNIQUE KEY (`platform_page_id`, `platform_role_id`),
  PRIMARY KEY (`platform_page_role_id`)
);