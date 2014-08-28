-- phpMyAdmin SQL Dump
-- version 2.10.2
-- http://www.phpmyadmin.net
-- 
-- 主机: localhost
-- 生成日期: 2014 年 07 月 12 日 16:52
-- 服务器版本: 5.0.45
-- PHP 版本: 5.2.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- 数据库: `iiimms_db`
-- 

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_corpustext`
-- 

CREATE TABLE `tbl_corpustext` (
  `textId` int(11) NOT NULL auto_increment,
  `blongSetId` int(11) default NULL,
  `labeledSetId` int(11) default NULL,
  `taskLogId` varchar(30) default NULL,
  `siteId` int(11) default NULL,
  `nodeId` varchar(50) default NULL,
  `filePath` varchar(100) default NULL,
  `inTestFolder` int(11) NOT NULL default '0',
  `buildTime` datetime default NULL,
  PRIMARY KEY  (`textId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- 导出表中的数据 `tbl_corpustext`
-- 

