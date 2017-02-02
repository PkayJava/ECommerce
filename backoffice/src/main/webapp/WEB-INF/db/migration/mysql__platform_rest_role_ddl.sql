CREATE TABLE `platform_rest_role` (

  `platform_rest_role_id` BIGINT(19) NOT NULL,
  `platform_rest_id`      BIGINT(19) NOT NULL,
  `platform_role_id`      BIGINT(19) NOT NULL,

  UNIQUE KEY (`platform_rest_id`, `platform_role_id`),
  PRIMARY KEY (`platform_rest_role_id`)
);