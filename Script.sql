--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `artiflow`.`project` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`user_project` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`artifact` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`status` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`review` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`artifact_version` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`reviewers` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`comments` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`artifact_type` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`user` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`notification` DROP PRIMARY KEY;

ALTER TABLE `artiflow`.`artifact_type` DROP INDEX `artiflow`.`artifact_type_name_UNIQUE`;

ALTER TABLE `artiflow`.`user` DROP INDEX `artiflow`.`user_name_UNIQUE`;

ALTER TABLE `artiflow`.`review` DROP INDEX `artiflow`.`fk_review_user1_idx`;

ALTER TABLE `artiflow`.`reviewers` DROP INDEX `artiflow`.`fk_reviewers_user1_idx`;

ALTER TABLE `artiflow`.`notification` DROP INDEX `artiflow`.`notification_id_UNIQUE`;

ALTER TABLE `artiflow`.`artifact` DROP INDEX `artiflow`.`fk_artifact_review1_idx`;

ALTER TABLE `artiflow`.`status` DROP INDEX `artiflow`.`status_id_UNIQUE`;

ALTER TABLE `artiflow`.`status` DROP INDEX `artiflow`.`status_name_UNIQUE`;

ALTER TABLE `artiflow`.`user_project` DROP INDEX `artiflow`.`fk_Project_has_user_Project_idx`;

ALTER TABLE `artiflow`.`artifact_version` DROP INDEX `artiflow`.`artifact_version_id_UNIQUE`;

ALTER TABLE `artiflow`.`artifact` DROP INDEX `artiflow`.`artifact_id_UNIQUE`;

ALTER TABLE `artiflow`.`reviewers` DROP INDEX `artiflow`.`fk_reviewers_review1_idx`;

ALTER TABLE `artiflow`.`comments` DROP INDEX `artiflow`.`fk_comments_artifact1_idx`;

ALTER TABLE `artiflow`.`user_project` DROP INDEX `artiflow`.`fk_Project_has_user_user1_idx`;

ALTER TABLE `artiflow`.`review` DROP INDEX `artiflow`.`fk_review_status1_idx`;

ALTER TABLE `artiflow`.`reviewers` DROP INDEX `artiflow`.`reviewers_id_UNIQUE`;

ALTER TABLE `artiflow`.`comments` DROP INDEX `artiflow`.`fk_comments_review1_idx`;

ALTER TABLE `artiflow`.`artifact` DROP INDEX `artiflow`.`fk_artifact_artifact_type1_idx`;

ALTER TABLE `artiflow`.`comments` DROP INDEX `artiflow`.`comments_id_UNIQUE`;

ALTER TABLE `artiflow`.`project` DROP INDEX `artiflow`.`project_id_UNIQUE`;

ALTER TABLE `artiflow`.`artifact` DROP INDEX `artiflow`.`fk_artifact_Project1_idx`;

ALTER TABLE `artiflow`.`review` DROP INDEX `artiflow`.`review_id_UNIQUE`;

ALTER TABLE `artiflow`.`artifact_type` DROP INDEX `artiflow`.`artifact_type_id_UNIQUE`;

ALTER TABLE `artiflow`.`notification` DROP INDEX `artiflow`.`notification_name_UNIQUE`;

ALTER TABLE `artiflow`.`user` DROP INDEX `artiflow`.`user_id_UNIQUE`;

ALTER TABLE `artiflow`.`comments` DROP INDEX `artiflow`.`fk_comments_user1_idx`;

DROP TABLE `artiflow`.`reviewers`;

DROP TABLE `artiflow`.`artifact_version`;

DROP TABLE `artiflow`.`project`;

DROP TABLE `artiflow`.`user`;

DROP TABLE `artiflow`.`artifact_type`;

DROP TABLE `artiflow`.`user_project`;

DROP TABLE `artiflow`.`status`;

DROP TABLE `artiflow`.`notification`;

DROP TABLE `artiflow`.`review`;

DROP TABLE `artiflow`.`comments`;

DROP TABLE `artiflow`.`artifact`;

CREATE TABLE `artiflow`.`reviewers` (
	`reviewers_id` INTEGER UNSIGNED NOT NULL,
	`review_id` INTEGER UNSIGNED,
	`is_optional` TINYINT DEFAULT 0,
	`user_id` INTEGER UNSIGNED,
	PRIMARY KEY (`reviewers_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`artifact_version` (
	`artifact_version_id` INTEGER UNSIGNED NOT NULL,
	`artifact_file` BLOB,
	PRIMARY KEY (`artifact_version_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`project` (
	`project_id` INTEGER UNSIGNED NOT NULL,
	`project_name` VARCHAR(45),
	PRIMARY KEY (`project_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`user` (
	`user_id` INTEGER UNSIGNED NOT NULL,
	`user_name` VARCHAR(45),
	`password` VARCHAR(45),
	PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`artifact_type` (
	`artifact_type_id` INTEGER UNSIGNED NOT NULL,
	`artifact_type_name` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`artifact_type_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`user_project` (
	`user_project` INTEGER UNSIGNED NOT NULL,
	`Project_id` INTEGER UNSIGNED,
	`user_id` INTEGER UNSIGNED,
	PRIMARY KEY (`user_project`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`status` (
	`status_id` INTEGER UNSIGNED NOT NULL,
	`status_name` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`status_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`notification` (
	`notification_id` INTEGER UNSIGNED NOT NULL,
	`notification_name` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`review` (
	`review_id` INTEGER UNSIGNED NOT NULL,
	`story_name` VARCHAR(45),
	`objective` VARCHAR(100),
	`project_id` INT,
	`author_id` INTEGER UNSIGNED,
	`status_id` INTEGER UNSIGNED,
	PRIMARY KEY (`review_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`comments` (
	`comments_id` INT NOT NULL,
	`comments` TEXT,
	`review_id` INTEGER UNSIGNED,
	`user_id` INTEGER UNSIGNED,
	`artifact_id` INT NOT NULL,
	PRIMARY KEY (`comments_id`)
) ENGINE=InnoDB;

CREATE TABLE `artiflow`.`artifact` (
	`artifact_id` INT NOT NULL,
	`artifact_name` VARCHAR(200),
	`review_id` INTEGER UNSIGNED,
	`Project_id` INTEGER UNSIGNED,
	`artifact_type_id` INTEGER UNSIGNED NOT NULL,
	PRIMARY KEY (`artifact_id`)
) ENGINE=InnoDB;

CREATE UNIQUE INDEX `artifact_type_name_UNIQUE` ON `artiflow`.`artifact_type` (`artifact_type_name` ASC);

CREATE UNIQUE INDEX `user_name_UNIQUE` ON `artiflow`.`user` (`user_name` ASC);

CREATE INDEX `fk_review_user1_idx` ON `artiflow`.`review` (`author_id` ASC);

CREATE INDEX `fk_reviewers_user1_idx` ON `artiflow`.`reviewers` (`user_id` ASC);

CREATE INDEX `fk_artifact_review1_idx` ON `artiflow`.`artifact` (`review_id` ASC);

CREATE UNIQUE INDEX `notification_id_UNIQUE` ON `artiflow`.`notification` (`notification_id` ASC);

CREATE UNIQUE INDEX `status_id_UNIQUE` ON `artiflow`.`status` (`status_id` ASC);

CREATE UNIQUE INDEX `status_name_UNIQUE` ON `artiflow`.`status` (`status_name` ASC);

CREATE INDEX `fk_Project_has_user_Project_idx` ON `artiflow`.`user_project` (`Project_id` ASC);

CREATE UNIQUE INDEX `artifact_version_id_UNIQUE` ON `artiflow`.`artifact_version` (`artifact_version_id` ASC);

CREATE UNIQUE INDEX `artifact_id_UNIQUE` ON `artiflow`.`artifact` (`artifact_id` ASC);

CREATE INDEX `fk_reviewers_review1_idx` ON `artiflow`.`reviewers` (`review_id` ASC);

CREATE INDEX `fk_comments_artifact1_idx` ON `artiflow`.`comments` (`artifact_id` ASC);

CREATE INDEX `fk_Project_has_user_user1_idx` ON `artiflow`.`user_project` (`user_id` ASC);

CREATE INDEX `fk_review_status1_idx` ON `artiflow`.`review` (`status_id` ASC);

CREATE UNIQUE INDEX `reviewers_id_UNIQUE` ON `artiflow`.`reviewers` (`reviewers_id` ASC);

CREATE INDEX `fk_comments_review1_idx` ON `artiflow`.`comments` (`review_id` ASC);

CREATE INDEX `fk_artifact_artifact_type1_idx` ON `artiflow`.`artifact` (`artifact_type_id` ASC);

CREATE UNIQUE INDEX `comments_id_UNIQUE` ON `artiflow`.`comments` (`comments_id` ASC);

CREATE UNIQUE INDEX `project_id_UNIQUE` ON `artiflow`.`project` (`project_id` ASC);

CREATE INDEX `fk_artifact_Project1_idx` ON `artiflow`.`artifact` (`Project_id` ASC);

CREATE UNIQUE INDEX `review_id_UNIQUE` ON `artiflow`.`review` (`review_id` ASC);

CREATE UNIQUE INDEX `artifact_type_id_UNIQUE` ON `artiflow`.`artifact_type` (`artifact_type_id` ASC);

CREATE UNIQUE INDEX `notification_name_UNIQUE` ON `artiflow`.`notification` (`notification_name` ASC);

CREATE UNIQUE INDEX `user_id_UNIQUE` ON `artiflow`.`user` (`user_id` ASC);

CREATE INDEX `fk_comments_user1_idx` ON `artiflow`.`comments` (`user_id` ASC);

