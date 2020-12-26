CREATE DATABASE `newyahoo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `newyahoo`;

CREATE TABLE `checkers_admins` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `level` int(10) unsigned NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `checkers_games` (
  `game_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `players` blob,
  `oldratings` blob,
  `newratings` blob,
  `game_data` blob,
  `flags` bigint(20) unsigned DEFAULT NULL,
  `result` blob,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

CREATE TABLE `checkers_ignoreds` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `ban_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ban_type` smallint(5) unsigned NOT NULL,
  `ban_time` bigint(20) NOT NULL,
  `reason` text NOT NULL,
  `admin` varchar(32) NOT NULL,
  `ip` varchar(16) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `checkers_profiles` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `rating` int(11) NOT NULL DEFAULT '1200',
  `wins` int(10) unsigned NOT NULL DEFAULT '0',
  `losses` int(10) unsigned NOT NULL DEFAULT '0',
  `draws` int(10) unsigned NOT NULL DEFAULT '0',
  `streak` int(11) NOT NULL DEFAULT '0',
  `aborteds` int(10) unsigned NOT NULL DEFAULT '0',
  `flags` bigint(20) unsigned NOT NULL,
  `ip` varchar(16) NOT NULL DEFAULT '0.0.0.0',
  PRIMARY KEY (`name`),
  KEY `index_2` (`ip`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `checkers_rooms` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `label` text,
  `public` tinyint(1) NOT NULL,
  `country` varchar(2) NOT NULL,
  `welcome_msg` text NOT NULL,
  `id_count` int(10) unsigned NOT NULL,
  PRIMARY KEY (`name`,`country`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `games` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `label` text,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ids` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `flags` bigint(20) unsigned NOT NULL DEFAULT '192',
  `more_flags` int(10) unsigned NOT NULL DEFAULT '0',
  `password` varchar(16) NOT NULL,
  `ycookie` varchar(72) NOT NULL DEFAULT '0',
  `e-mail` text NOT NULL,
  `ip` varchar(16) NOT NULL DEFAULT '0.0.0.0',
  `cookie_expires` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_access` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `avatar` smallint(5) unsigned NOT NULL DEFAULT '0',
  `friends` blob,
  `ignoreds` blob,
  `games_common_sound` tinyint(1) NOT NULL DEFAULT '1',
  `prowler_g` tinyint(1) NOT NULL DEFAULT '2',
  `games_common_profanity` smallint(5) unsigned NOT NULL DEFAULT '1',
  `games_common_hidestar` tinyint(1) NOT NULL DEFAULT '0',
  `games_common_smallwindows` tinyint(1) NOT NULL DEFAULT '0',
  `games_common_automove` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool2_admins` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `level` int(10) unsigned NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool2_games` (
  `game_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `players` blob,
  `oldratings` blob,
  `newratings` blob,
  `game_data` blob,
  `flags` bigint(20) unsigned DEFAULT NULL,
  `result` blob,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool2_ignoreds` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `ban_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ban_type` smallint(5) unsigned NOT NULL,
  `ban_time` bigint(20) NOT NULL,
  `reason` text NOT NULL,
  `admin` varchar(32) NOT NULL,
  `ip` varchar(16) NOT NULL,
  PRIMARY KEY (`name`,`admin`,`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool2_profiles` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `rating` int(11) NOT NULL DEFAULT '1200',
  `wins` int(10) unsigned NOT NULL DEFAULT '0',
  `losses` int(10) unsigned NOT NULL DEFAULT '0',
  `draws` int(10) unsigned NOT NULL DEFAULT '0',
  `streak` int(11) NOT NULL DEFAULT '0',
  `aborteds` int(10) unsigned NOT NULL DEFAULT '0',
  `flags` bigint(20) unsigned NOT NULL,
  `ip` varchar(16) NOT NULL,
  PRIMARY KEY (`name`,`ip`),
  KEY `index_2` (`ip`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool2_rooms` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `label` text,
  `public` tinyint(1) NOT NULL,
  `country` varchar(2) NOT NULL,
  `welcome_msg` text NOT NULL,
  `id_count` int(10) unsigned NOT NULL,
  PRIMARY KEY (`name`,`country`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool_admins` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `level` int(10) unsigned NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool_games` (
  `game_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `players` blob,
  `oldratings` blob,
  `newratings` blob,
  `game_data` blob,
  `flags` bigint(20) unsigned DEFAULT NULL,
  `result` blob,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool_ignoreds` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `ban_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ban_type` smallint(5) unsigned NOT NULL,
  `ban_time` bigint(20) NOT NULL,
  `reason` text NOT NULL,
  `admin` varchar(32) NOT NULL,
  `ip` varchar(16) NOT NULL,
  PRIMARY KEY (`name`,`admin`,`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool_profiles` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `rating` int(11) NOT NULL DEFAULT '1200',
  `wins` int(10) unsigned NOT NULL DEFAULT '0',
  `losses` int(10) unsigned NOT NULL DEFAULT '0',
  `draws` int(10) unsigned NOT NULL DEFAULT '0',
  `streak` int(11) NOT NULL DEFAULT '0',
  `aborteds` int(10) unsigned NOT NULL DEFAULT '0',
  `flags` bigint(20) unsigned NOT NULL,
  `ip` varchar(16) NOT NULL DEFAULT '0.0.0.0',
  PRIMARY KEY (`name`),
  KEY `index_2` (`ip`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pool_rooms` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `label` text,
  `public` tinyint(1) NOT NULL,
  `country` varchar(2) NOT NULL,
  `welcome_msg` text NOT NULL,
  `id_count` int(10) unsigned NOT NULL,
  PRIMARY KEY (`name`,`country`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;