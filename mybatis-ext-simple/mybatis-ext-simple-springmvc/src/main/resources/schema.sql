-- ----------------------------
-- Table structure for weshop_user
-- ----------------------------
DROP TABLE IF EXISTS `weshop_user`;
CREATE TABLE `weshop_user` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `gender` tinyint(2) unsigned DEFAULT 0,
  `birthday` date DEFAULT NULL,
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login_ip` varchar(15) NOT NULL DEFAULT '',
  `user_level_id` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nickname` varchar(60) NOT NULL DEFAULT '',
  `mobile` varchar(20) NOT NULL,
  `register_ip` varchar(45) NOT NULL DEFAULT '',
  `avatar` varchar(255) NOT NULL DEFAULT '',
  `wechat_open_id` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;