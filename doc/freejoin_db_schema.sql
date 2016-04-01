CREATE TABLE IF NOT EXISTS `fj_users_groups_assignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL COMMENT 'yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`ID`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `fj_groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(256) NOT NULL,
  `created_at` date DEFAULT NULL COMMENT 'yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`ID`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `fj_users_activities_assignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `activity_id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL COMMENT 'yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`ID`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `fj_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(256) NOT NULL,
  `mobile` bigint(12) NOT NULL,
  `created_at` date DEFAULT NULL COMMENT 'yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`ID`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `fj_activities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL COMMENT 'activity title',
  `start_time` datetime NOT NULL DEFAULT NULL COMMENT 'yyyy-MM-dd HH:mm:ss',
  `end_time` datetime DEFAULT NULL COMMENT 'yyyy-MM-dd HH:mm:ss',
  `content` varchar(2048) DEFAULT NULL,
  `group_id` bigint(20),
  `created_by` varchar(256) DEFAULT NULL,
  `created_at` date DEFAULT NULL COMMENT 'yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;






CREATE INDEX idx_user_id ON fj_users_groups_assignment (user_id); 
CREATE INDEX idx_group_id ON fj_users_groups_assignment (group_id);

CREATE INDEX idx_fj_groups_id ON fj_groups (id);

CREATE INDEX idx_users_activities_user_id ON fj_users_activities_assignment (user_id);
CREATE INDEX idx_users_activities_activity_id ON fj_users_activities_assignment (activity_id);

CREATE INDEX idx_fj_users_id ON fj_users (id);

CREATE INDEX idx_fj_activities_id ON fj_activities (id);