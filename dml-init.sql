-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 103.20.249.125
-- Generation Time: 2017-04-25 03:45:53
-- 服务器版本： 10.1.20-MariaDB-1~jessie
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `ufrog_leo_dev`
--

--
-- 转存表中的数据 `leo_app`
--

INSERT INTO `leo_app` (`pk_id`, `vc_name`, `vc_code`, `vc_secret`, `vc_timeout`, `vc_url`, `vc_logo`, `vc_color`, `dc_status`, `dc_visible`, `dc_mulriple`, `fk_creator`, `dt_create_time`, `fk_updater`, `dt_update_time`) VALUES
('19ae06ac-0a29-11e7-8989-9ffd19844ced', '用户中心', 'leo', '2ajrIM4Kf6fyn1FbH0bfpZ5XkLEMUji5W0VddFJxNa5nePwrbRJ90PsDiFpbWN6Z', '30min', 'http://localhost:8080', NULL, '#ff9e5c', '01', '01', '01', NULL, '2017-03-16 17:13:53', NULL, '2017-03-16 17:13:53'),
('dffb97a8-0a28-11e7-8989-9ffd19844ced', '项目管理', 'virgo', 'FYvcILZspnMijF3oq1AflhUGipybDBZ5EvoPLtWqQVsWYBucZO4MbWSUa1djnt9g', '30min', 'http://localhost:8080', NULL, '#aaaaaa', '01', '01', '01', NULL, '2017-03-16 17:13:53', NULL, '2017-03-16 17:13:53');

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
COMMIT;
