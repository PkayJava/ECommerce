INSERT INTO `platform_setting` (`platform_setting_id`, `key`, `value`, `description`, `version`)
VALUES
  (10, 'smtp_user', 'SMTP User', 'SMTP User', 1),
  (9, 'smtp_password', 'SMTP Password', 'SMTP Password', 1),
  (1, 'home_page', 'Home Page', 'Home Page', 1),
  (2, 'google_api_key', '', 'Google Api Key', 1),
  (3, 'asset', 'http://192.168.0.114:8080', 'Asset', 1),
  (4, 'price_format', '$#,###,##0.00', 'Price Format', 1),
  (5, 'currency', 'USD', 'Currency', 1),
  (6, 'time_format', 'hh:mm a', 'Time Format', 1),
  (7, 'date_format', 'yyyy-MM-dd', 'Date Format', 1),
  (8, 'datetime_format', 'dd.MM.yyyy hh:mm a', 'Datetime Format', 1);