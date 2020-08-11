CREATE TABLE `video`.`download_record` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`gmt_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`movie_id` varchar(32) COMMENT '视频id',
	`local_path` varchar(512) COMMENT '本地文件路径',
	`url` varchar(512) COMMENT '直播流路径（下载路径）',
	`type` int COMMENT '视频类型',
	`status` varchar(64) COMMENT '视频下载状态',
	`title` varchar(512) COMMENT '视频名称' CHARACTER SET utf8mb4,
	`width` int,
	`height` int,

	PRIMARY KEY (`id`)
) COMMENT='视频下载记录表';