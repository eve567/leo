-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2017-03-24 09:48:42
-- 服务器版本： 5.7.17
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `ufrog_leo_dev`
--

-- --------------------------------------------------------

--
-- 表的结构 `leo_app`
--

CREATE TABLE `leo_app` (
  `pk_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `vc_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `vc_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代码',
  `vc_secret` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密钥',
  `vc_timeout` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '超时时间',
  `vc_url` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问地址',
  `vc_logo` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `vc_color` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '色值',
  `dc_status` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `dc_visible` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '可见',
  `dc_mulriple` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '多用户',
  `fk_creator` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `dt_create_time` datetime NOT NULL COMMENT '创建时间',
  `fk_updater` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `dt_update_time` datetime NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用';

-- --------------------------------------------------------

--
-- 表的结构 `leo_nav`
--

CREATE TABLE `leo_nav` (
  `pk_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `vc_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `vc_subname` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '副称',
  `vc_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代码',
  `vc_path` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '路径',
  `vc_target` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目标',
  `dc_type` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型',
  `fk_parent_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上级编号',
  `fk_next_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下位编号',
  `fk_app_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用编号',
  `fk_creator` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `dt_create_time` datetime NOT NULL COMMENT '创建时间',
  `fk_updater` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `dt_update_time` datetime NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='导航';

-- --------------------------------------------------------

--
-- 表的结构 `leo_prop`
--

CREATE TABLE `leo_prop` (
  `pk_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `vc_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代码',
  `vc_value` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `fk_creator` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `dt_create_time` datetime NOT NULL COMMENT '创建时间',
  `fk_updater` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `dt_update_time` datetime NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统属性';

-- --------------------------------------------------------

--
-- 表的结构 `leo_user`
--

CREATE TABLE `leo_user` (
  `pk_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `vc_account` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `vc_email` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  `vc_cellphone` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `vc_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '称呼',
  `vc_password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `dc_status` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `dc_type` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型',
  `dc_forced` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '强制修改密码',
  `fk_creator` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `dt_create_time` datetime NOT NULL COMMENT '创建时间',
  `fk_updater` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `dt_update_time` datetime NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leo_app`
--
ALTER TABLE `leo_app`
  ADD PRIMARY KEY (`pk_id`);

--
-- Indexes for table `leo_nav`
--
ALTER TABLE `leo_nav`
  ADD PRIMARY KEY (`pk_id`),
  ADD KEY `fk_app_id` (`fk_app_id`),
  ADD KEY `fk_next_id` (`fk_next_id`),
  ADD KEY `fk_parent_id` (`fk_parent_id`);

--
-- Indexes for table `leo_prop`
--
ALTER TABLE `leo_prop`
  ADD PRIMARY KEY (`pk_id`);

--
-- Indexes for table `leo_user`
--
ALTER TABLE `leo_user`
  ADD PRIMARY KEY (`pk_id`);
