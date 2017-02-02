CREATE TABLE `platform_uuid` (

  `platform_uuid_id` BIGINT(19)   NOT NULL AUTO_INCREMENT,
  `table_name`       VARCHAR(255) NOT NULL,
  `value`            BIGINT(19)   NOT NULL,

  KEY (`value`),
  UNIQUE KEY (`table_name`),
  PRIMARY KEY (`platform_uuid_id`)

);
