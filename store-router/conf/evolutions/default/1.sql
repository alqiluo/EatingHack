# --- !Ups

# Dump of table user
# ------------------------------------------------------------

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL DEFAULT '',
  `hashed_password` varchar(255) NOT NULL DEFAULT '',
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  `creation_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `session_str` varchar(255) DEFAULT NULL,
  `udid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `yummly_recipe` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `yummly_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image_url` text,
  `cook_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `yummly_ingredient` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `yummly_recipe_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs

DROP TABLE IF EXISTS `user`;

DROP TABLE IF EXISTS `yummly_recipe`;

DROP TABLE IF EXISTS `yummly_ingredient`;