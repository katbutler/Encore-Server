SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `encoreDb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `encoreDb` ;

-- -----------------------------------------------------
-- Table `encoreDb`.`USERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`USERS` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`USERS` (
  `USER_ID` INT NOT NULL AUTO_INCREMENT ,
  `FIRST_NAME` VARCHAR(45) NOT NULL ,
  `LAST_NAME` VARCHAR(45) NOT NULL ,
  `EMAIL` VARCHAR(255) NOT NULL ,
  `PASSWORD` VARCHAR(45) NOT NULL ,
  `PICTURE_URL` VARCHAR(2000) NULL ,
  `BIO` VARCHAR(200) NULL ,
  `HOME_LAT` DOUBLE NULL DEFAULT 37.75 ,
  `HOME_LONG` DOUBLE NULL DEFAULT 122.68 ,
  PRIMARY KEY (`USER_ID`) ,
  UNIQUE INDEX `idUSERS_UNIQUE` (`USER_ID` ASC) ,
  UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`EVENTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`EVENTS` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`EVENTS` (
  `EVENT_ID` INT NOT NULL AUTO_INCREMENT ,
  `TITLE` VARCHAR(45) NOT NULL ,
  `DATE_CREATED` DATETIME NOT NULL ,
  `DATE_UPDATED` TIMESTAMP NOT NULL DEFAULT NOW() ,
  `EVENT_DATE` DATETIME NOT NULL ,
  `DESCRIPTION` VARCHAR(500) NOT NULL ,
  `EVENT_LAT` DOUBLE NULL ,
  `EVENT_LONG` DOUBLE NULL ,
  PRIMARY KEY (`EVENT_ID`) ,
  UNIQUE INDEX `EVENT_ID_UNIQUE` (`EVENT_ID` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`EVENT_ITEMS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`EVENT_ITEMS` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`EVENT_ITEMS` (
  `EVENT_ITEM_ID` INT NOT NULL AUTO_INCREMENT ,
  `EVENTS_EVENT_ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NOT NULL ,
  `DESCRIPTION` VARCHAR(2000) NULL ,
  `PICTURE_URL` VARCHAR(2000) NULL ,
  PRIMARY KEY (`EVENT_ITEM_ID`) ,
  UNIQUE INDEX `EVENT_ITEM_ID_UNIQUE` (`EVENT_ITEM_ID` ASC) ,
  INDEX `fk_EVENT_ITEMS_EVENTS1_idx` (`EVENTS_EVENT_ID` ASC) ,
  CONSTRAINT `fk_EVENT_ITEMS_EVENTS1`
    FOREIGN KEY (`EVENTS_EVENT_ID` )
    REFERENCES `encoreDb`.`EVENTS` (`EVENT_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`COMMENTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`COMMENTS` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`COMMENTS` (
  `COMMENT_ID` INT NOT NULL AUTO_INCREMENT ,
  `EVENT_ITEM_FK` INT NOT NULL ,
  `COMMENT_TEXT` VARCHAR(255) NULL ,
  `COMMENT_DATE` TIMESTAMP NOT NULL DEFAULT NOW() ,
  PRIMARY KEY (`COMMENT_ID`) ,
  UNIQUE INDEX `COMMENT_ID_UNIQUE` (`COMMENT_ID` ASC) ,
  INDEX `fk_COMMENTS_EVENT_ITEMS1_idx` (`EVENT_ITEM_FK` ASC) ,
  CONSTRAINT `fk_COMMENTS_EVENT_ITEMS1`
    FOREIGN KEY (`EVENT_ITEM_FK` )
    REFERENCES `encoreDb`.`EVENT_ITEMS` (`EVENT_ITEM_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`USERS_has_EVENTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`USERS_has_EVENTS` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`USERS_has_EVENTS` (
  `USERS_USER_ID` INT NOT NULL ,
  `EVENTS_EVENT_ID` INT NOT NULL ,
  PRIMARY KEY (`USERS_USER_ID`, `EVENTS_EVENT_ID`) ,
  INDEX `fk_USERS_has_EVENTS_EVENTS1_idx` (`EVENTS_EVENT_ID` ASC) ,
  INDEX `fk_USERS_has_EVENTS_USERS1_idx` (`USERS_USER_ID` ASC) ,
  CONSTRAINT `fk_USERS_has_EVENTS_USERS1`
    FOREIGN KEY (`USERS_USER_ID` )
    REFERENCES `encoreDb`.`USERS` (`USER_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USERS_has_EVENTS_EVENTS1`
    FOREIGN KEY (`EVENTS_EVENT_ID` )
    REFERENCES `encoreDb`.`EVENTS` (`EVENT_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`ACTIVITIES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`ACTIVITIES` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`ACTIVITIES` (
  `ACTIVITY_ID` INT NOT NULL AUTO_INCREMENT ,
  `ACTIVITY_DATE` TIMESTAMP NOT NULL DEFAULT NOW() ,
  `DESCRIPTION` VARCHAR(2000) NOT NULL ,
  `ACTIVITY_TYPE` INT NOT NULL ,
  PRIMARY KEY (`ACTIVITY_ID`) ,
  UNIQUE INDEX `ACTIVITY_ID_UNIQUE` (`ACTIVITY_ID` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`USERS_has_ACTIVITIES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`USERS_has_ACTIVITIES` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`USERS_has_ACTIVITIES` (
  `USERS_USER_ID` INT NOT NULL ,
  `ACTIVITIES_ACTIVITY_ID` INT NOT NULL ,
  PRIMARY KEY (`USERS_USER_ID`, `ACTIVITIES_ACTIVITY_ID`) ,
  INDEX `fk_USERS_has_ACTIVITIES1_ACTIVITIES1_idx` (`ACTIVITIES_ACTIVITY_ID` ASC) ,
  INDEX `fk_USERS_has_ACTIVITIES1_USERS1_idx` (`USERS_USER_ID` ASC) ,
  CONSTRAINT `fk_USERS_has_ACTIVITIES1_USERS1`
    FOREIGN KEY (`USERS_USER_ID` )
    REFERENCES `encoreDb`.`USERS` (`USER_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USERS_has_ACTIVITIES1_ACTIVITIES1`
    FOREIGN KEY (`ACTIVITIES_ACTIVITY_ID` )
    REFERENCES `encoreDb`.`ACTIVITIES` (`ACTIVITY_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`USERS_has_ACTIVITIES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`USERS_has_ACTIVITIES` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`USERS_has_ACTIVITIES` (
  `USERS_USER_ID` INT NOT NULL ,
  `ACTIVITIES_ACTIVITY_ID` INT NOT NULL ,
  PRIMARY KEY (`USERS_USER_ID`, `ACTIVITIES_ACTIVITY_ID`) ,
  INDEX `fk_USERS_has_ACTIVITIES1_ACTIVITIES1_idx` (`ACTIVITIES_ACTIVITY_ID` ASC) ,
  INDEX `fk_USERS_has_ACTIVITIES1_USERS1_idx` (`USERS_USER_ID` ASC) ,
  CONSTRAINT `fk_USERS_has_ACTIVITIES1_USERS1`
    FOREIGN KEY (`USERS_USER_ID` )
    REFERENCES `encoreDb`.`USERS` (`USER_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USERS_has_ACTIVITIES1_ACTIVITIES1`
    FOREIGN KEY (`ACTIVITIES_ACTIVITY_ID` )
    REFERENCES `encoreDb`.`ACTIVITIES` (`ACTIVITY_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encoreDb`.`USERS_FOLLOWING`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `encoreDb`.`USERS_FOLLOWING` ;

CREATE  TABLE IF NOT EXISTS `encoreDb`.`USERS_FOLLOWING` (
  `USER_ID` INT NOT NULL ,
  `FOLLOWING_USER_ID` INT NOT NULL ,
  PRIMARY KEY (`USER_ID`, `FOLLOWING_USER_ID`) ,
  INDEX `fk_USERS_has_USERS_USERS2_idx` (`FOLLOWING_USER_ID` ASC) ,
  INDEX `fk_USERS_has_USERS_USERS1_idx` (`USER_ID` ASC) ,
  CONSTRAINT `fk_USERS_has_USERS_USERS1`
    FOREIGN KEY (`USER_ID` )
    REFERENCES `encoreDb`.`USERS` (`USER_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USERS_has_USERS_USERS2`
    FOREIGN KEY (`FOLLOWING_USER_ID` )
    REFERENCES `encoreDb`.`USERS` (`USER_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `encoreDb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
