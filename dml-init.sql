--
-- 转存表中的数据 `leo_app`
--

INSERT INTO `leo_app` (`pk_id`, `vc_name`, `vc_code`, `vc_secret`, `vc_timeout`, `vc_url`, `vc_logo`, `vc_color`, `dc_status`, `dc_visible`, `dc_mulriple`, `fk_creator`, `dt_create_time`, `fk_updater`, `dt_update_time`) VALUES
  ('19ae06ac-0a29-11e7-8989-9ffd19844ced', '用户中心', 'leo', '', '30min', 'http://localhost:8080', NULL, '#ff9e5c', '01', '01', '01', NULL, '2017-03-16 17:13:53', NULL, '2017-03-16 17:13:53'),
  ('dffb97a8-0a28-11e7-8989-9ffd19844ced', '项目管理', 'virgo', '', '30min', 'http://localhost:8080', NULL, '#aaaaaa', '01', '01', '01', NULL, '2017-03-16 17:13:53', NULL, '2017-03-16 17:13:53');

--
-- 转存表中的数据 `leo_prop`
--

INSERT INTO `leo_prop` (`pk_id`, `vc_code`, `vc_value`, `fk_creator`, `dt_create_time`, `fk_updater`, `dt_update_time`) VALUES
  ('331212b2-0f6c-11e7-8989-9ffd19844ced', 'console_copyright', '&copy; 2017 ufrog.net', NULL, '2017-03-23 09:56:23', NULL, '2017-03-23 09:56:23'),
  ('3f20c864-0f6c-11e7-8989-9ffd19844ced', 'console_name', '用户中心', NULL, '2017-03-23 09:56:23', NULL, '2017-03-23 09:56:23'),
  ('eb14d620-0f6b-11e7-8989-9ffd19844ced', 'console_title', '用户中心 - 资源管理平台', NULL, '2017-03-23 09:56:23', NULL, '2017-03-23 09:56:23');

--
-- 转存表中的数据 `leo_user`
--

INSERT INTO `leo_user` (`pk_id`, `vc_account`, `vc_email`, `vc_cellphone`, `vc_name`, `vc_password`, `dc_status`, `dc_type`, `dc_forced`, `fk_creator`, `dt_create_time`, `fk_updater`, `dt_update_time`) VALUES
  ('9e574668-0ecd-11e7-8989-9ffd19844ced', 'rhllor', NULL, NULL, '光之王', 'c650a718f5b27b56b7ba4e50000eda1baf0e0bb7b351a6a908654ff4a01d08bc68d4e9c12e8f62cf', '10', '99', '00', NULL, '2017-03-22 15:03:14', NULL, '2017-03-22 15:03:14');
