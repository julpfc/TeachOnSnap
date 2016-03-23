-- TeachOnSnap MySQL Script
-- @Author: David Júlvez Estrada
-- 2014-2016
-- -----------------------------------------------------
-- Schema teachonsnap
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `teachonsnap` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `teachonsnap` ;

-- -----------------------------------------------------
-- Table `teachonsnap`.`tlanguage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlanguage` (
  `idLanguage` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing languages availables at the application.',
  `language` VARCHAR(2) NOT NULL COMMENT 'Code ISO 639-1',
  PRIMARY KEY (`idLanguage`),
  UNIQUE INDEX `UK_language` (`language` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlanguagedefault`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlanguagedefault` (
  `idLanguage` TINYINT UNSIGNED NOT NULL COMMENT 'Default language for users when their language is not supported by the application.',
  PRIMARY KEY (`idLanguage`),
  CONSTRAINT `FK_tlanguagedefault_tlanguage` FOREIGN KEY (`idLanguage`) REFERENCES `teachonsnap`.`tlanguage` (`idLanguage`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tuser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tuser` (
  `iduser` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing application´s users.',
  `email` VARCHAR(75) NOT NULL COMMENT 'User´s email',
  `password` VARCHAR(45) NOT NULL COMMENT 'User´s password - PASSWORD()',
  `idLanguage` TINYINT UNSIGNED NOT NULL COMMENT 'User´s language preference',
  `firstName` VARCHAR(20) NOT NULL COMMENT 'User´s first name',
  `lastName` VARCHAR(45) NOT NULL COMMENT 'User´s last name',
  `dateIni` DATE NOT NULL COMMENT 'Registration date',
  PRIMARY KEY (`iduser`),
  UNIQUE INDEX `UK_email` (`email` ASC),
  INDEX `FK_tuser_tlanguage` (`idLanguage` ASC),
  INDEX `IDX_FullName` (`firstName` ASC, `lastName` ASC),  
  CONSTRAINT `FK_tuser_tlanguage` FOREIGN KEY (`idLanguage`) REFERENCES `teachonsnap`.`tlanguage` (`idLanguage`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tuserauthor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tuserauthor` (
  `idUser` SMALLINT UNSIGNED NOT NULL COMMENT 'Table describing authors.',
  PRIMARY KEY (`idUser`),
  CONSTRAINT `FK_tuserauthor_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tuserauthoruri`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tuserauthoruri` (
  `idUser` SMALLINT UNSIGNED NOT NULL COMMENT 'Table describing author´s URIs',
  `URIname` VARCHAR(140) NOT NULL COMMENT 'Author´s full name without special characters (URL friendly).',
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `UK_URIName` (`URIname` ASC),
  CONSTRAINT `FK_tuserauthoruri_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tuseradmin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tuseradmin` (
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Table describing administrators.',
  PRIMARY KEY (`idUser`),
  CONSTRAINT `FK_tuseradmin_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tusertmptoken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tusertmptoken` (
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Table describing temporary identification tokens.',
  `token` VARCHAR(255) NOT NULL COMMENT 'Temporary identification token for this user.',
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `UK_token` (`token` ASC),
  CONSTRAINT `FK_tusertmptoken_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tuserextrainfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tuserextrainfo` (
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Table describing additional identifying information about users.',
  `extrainfo` VARCHAR(255) NOT NULL COMMENT 'Additional information about this user.',
  PRIMARY KEY (`idUser`),
  CONSTRAINT `FK_tuserextrainfo_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tuserbanned`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tuserbanned` (
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Table describing banned users.',
  `reason` VARCHAR(255) NOT NULL COMMENT 'Ban reason.',
  `date` DATETIME NOT NULL COMMENT 'Ban date.',
  `idAdmin` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Admin who banned this user.',
  PRIMARY KEY (`idUser`),
  INDEX `FK_tuserbanned_tusera` (`idAdmin` ASC),
  CONSTRAINT `FK_tuserbanned_tusera` FOREIGN KEY (`idAdmin`) REFERENCES `teachonsnap`.`tuser` (`iduser`),
  CONSTRAINT `FK_tuserbanned_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tusergroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tusergroup` (
  `idUserGroup` SMALLINT(8) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing user groups.',
  `groupName` VARCHAR(50) NOT NULL COMMENT 'Group name.',  
  PRIMARY KEY (`idUserGroup`),
  UNIQUE INDEX `UK_groupName` (`groupName` ASC)  
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tusergroupmember`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tusergroupmember` (
  `idUserGroup` SMALLINT(8) UNSIGNED NOT NULL COMMENT 'Table describing group members.',
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Group member.',
  PRIMARY KEY (`idUserGroup`, `idUser`),
  INDEX `FK_tusergroupmember_tuser` (`idUser` ASC),
  CONSTRAINT `FK_tusergroupmember_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`),
  CONSTRAINT `FK_tusergroupmember_tusergroup` FOREIGN KEY (`idUserGroup`) REFERENCES `teachonsnap`.`tusergroup` (`idUserGroup`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlesson` (
  `idLesson` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing lessons.',
  `idUser` SMALLINT UNSIGNED NOT NULL COMMENT 'Lesson´s author.',
  `idLanguage` TINYINT UNSIGNED NOT NULL COMMENT 'Lesson´s language.',
  `title` VARCHAR(140) NOT NULL COMMENT 'Lesson´s title.',
  `date` DATETIME NOT NULL COMMENT 'Lesson´s creation date.',
  `URIname` VARCHAR(140) NOT NULL COMMENT 'Lesson´s friendly URI.',
  PRIMARY KEY (`idLesson`),
  UNIQUE INDEX `UK_title` (`title` ASC),
  INDEX `FK_tlesson_tlanguage` (`idLanguage` ASC),
  INDEX `FK_tlesson_tuser` (`idUser` ASC),
  UNIQUE INDEX `UK_URIname` (`URIname` ASC),
  CONSTRAINT `FK_tlesson_tlanguage` FOREIGN KEY (`idLanguage`) REFERENCES `teachonsnap`.`tlanguage` (`idLanguage`),
  CONSTRAINT `FK_tlesson_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessontxt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessontxt` (
  `idLesson` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Table describing Lesson´s optional texts.',
  `text` VARCHAR(140) NOT NULL COMMENT 'Optional text for this lesson.',
  PRIMARY KEY (`idLesson`),
  CONSTRAINT `FK_tlessontxt_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessononline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessononline` (
  `idLesson` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing published lessons which are visible to other users.',
  `date` DATETIME NOT NULL COMMENT 'Publishing date.',
  PRIMARY KEY (`idLesson`),
  INDEX `IDX_date` (`date` ASC),
  CONSTRAINT `FK_tlessononline_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;



-- -----------------------------------------------------
-- Table `teachonsnap`.`tlink`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlink` (
  `idLink` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing links, used by lessons.',
  `URL` VARCHAR(255) NOT NULL COMMENT 'Link´s URL.',
  `desc` VARCHAR(140) NOT NULL COMMENT 'URL´s description.',
  PRIMARY KEY (`idLink`),
  UNIQUE INDEX `UK_URL` (`URL` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessonsource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessonsource` (
  `idLesson` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Table describing lesson´s sources.',
  `idLink` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Link to this lesson´s source.',
  PRIMARY KEY (`idLesson`, `idLink`),
  INDEX `FK_tlessonsource_tlink` (`idLink` ASC),
  CONSTRAINT `FK_tlessonsource_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`),
  CONSTRAINT `FK_tlessonsource_tlink` FOREIGN KEY (`idLink`) REFERENCES `teachonsnap`.`tlink` (`idLink`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessonmoreinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessonmoreinfo` (
  `idLesson` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Table describing lesson´s links to further information.',
  `idLink` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Further information link.',
  PRIMARY KEY (`idLesson`, `idLink`),
  INDEX `FK_tlessonmoreinfo_tlink` (`idLink` ASC),
  CONSTRAINT `FK_tlessonmoreinfo_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`),
  CONSTRAINT `FK_tlessonmoreinfo_tlink` FOREIGN KEY (`idLink`) REFERENCES `teachonsnap`.`tlink` (`idLink`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`ttag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`ttag` (
  `idTag` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing tags. Used to describe lessons contents.',
  `tag` VARCHAR(45) NOT NULL COMMENT 'Tag.',
  PRIMARY KEY (`idTag`),
  UNIQUE INDEX `UK_tag` (`tag` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessontag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessontag` (
  `idLesson` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Table describing lesson´s tags.',
  `idTag` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Tag describing this lesson´s content.',
  PRIMARY KEY (`idLesson`, `idTag`),
  INDEX `FK_tlessontag_ttag` (`idTag` ASC),
  CONSTRAINT `FK_tlessontag_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`),
  CONSTRAINT `FK_tlessontag_ttag` FOREIGN KEY (`idTag`) REFERENCES `teachonsnap`.`ttag` (`idTag`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tmediarepository`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tmediarepository` (
  `idmediarepository` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing available media file repositories.',
  `URI` VARCHAR(255) NOT NULL COMMENT 'URI to access this repository.',
  `filePathSeparator` VARCHAR(6) NOT NULL COMMENT 'File path separator, depends on the OS where the files are stored.',
  `desc` VARCHAR(45) NOT NULL  COMMENT 'Description of the repository.',
  PRIMARY KEY (`idmediarepository`),
  UNIQUE INDEX `UK_URI` (`URI` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tmediatype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tmediatype` (
  `idmediatype` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing media types supported by the application.',
  `mediatype` VARCHAR(5) NOT NULL COMMENT 'Media content type.',
  `desc` VARCHAR(45) NOT NULL COMMENT 'Description of the media type.',
  PRIMARY KEY (`idmediatype`),
  UNIQUE INDEX `mediatype_UNIQUE` (`mediatype` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tmediamimetype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tmediamimetype` (
  `idmediamimetype` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing MIME types supported by the application.',
  `idmediatype` TINYINT UNSIGNED NOT NULL COMMENT 'Media type asociated to this mimetype.',
  `mimetype` VARCHAR(45) NOT NULL COMMENT 'MIME type - Content-type.',
  `extension` VARCHAR(5) NOT NULL COMMENT 'File extension associated to this MIME type.',
  PRIMARY KEY (`idmediamimetype`),
  UNIQUE INDEX `UK_mimetype` (`mimetype` ASC),
  INDEX `FK_tmediamimetype_tmediatype` (`idmediatype` ASC),
  CONSTRAINT `FK_tmediamimetype_tmediatype` FOREIGN KEY (`idmediatype`) REFERENCES `teachonsnap`.`tmediatype` (`idmediatype`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessonmedia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessonmedia` (
  `idlessonmedia` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing media associated to a lesson.',
  `idlesson` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Lesson with media content.',
  PRIMARY KEY (`idlessonmedia`),
  UNIQUE INDEX `UK_idLesson` (`idlesson` ASC),
  CONSTRAINT `FK_tlessonmedia_tlesson` FOREIGN KEY (`idlesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tmediafile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tmediafile` (
  `idmediafile` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing media files.',
  `idlessonmedia` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Lesson where the file is played.',
  `idmediarepository` TINYINT UNSIGNED NOT NULL COMMENT 'Repository where the file is stored.',
  `idmediamimetype` TINYINT UNSIGNED NOT NULL COMMENT 'Media file´s content-type.',
  `filename` VARCHAR(45) NOT NULL COMMENT 'File name.',
  `filesize` INT UNSIGNED NOT NULL COMMENT 'File size (bytes).',
  PRIMARY KEY (`idmediafile`),
  INDEX `FK_tmediafile_tmediarepo` (`idmediarepository` ASC, `filesize` ASC),  
  UNIQUE INDEX `UK_idlessonmedia_idmediarepo_idmediamime` (`idmediamimetype` ASC,`idlessonmedia` ASC,`idmediarepository` ASC),
  INDEX `FK_tmediafile_tlessonmedia` (`idlessonmedia` ASC, `filesize` ASC),
  CONSTRAINT `FK_tmediafile_tlessonmedia` FOREIGN KEY (`idlessonmedia`) REFERENCES `teachonsnap`.`tlessonmedia` (`idlessonmedia`),
  CONSTRAINT `FK_tmediafile_tmediarepo` FOREIGN KEY (`idmediarepository`) REFERENCES `teachonsnap`.`tmediarepository` (`idmediarepository`),
  CONSTRAINT `FK_tmediafile_tmediamime` FOREIGN KEY (`idmediamimetype`) REFERENCES `teachonsnap`.`tmediamimetype` (`idmediamimetype`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tcomment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tcomment` (
  `idComment` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing user´s comments.',
  `idLesson` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Lesson the comment is about.',
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'User who writes the comment.',
  `date` DATETIME NOT NULL COMMENT 'Creation date.',
  PRIMARY KEY (`idComment`),
  INDEX `FK_tcomment_tuser` (`idUser` ASC),
  INDEX `FK_tcomment_tlesson` (`idLesson` ASC),
  CONSTRAINT `FK_tcomment_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`),
  CONSTRAINT `FK_tcomment_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tcommentbody`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tcommentbody` (
  `idComment` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing comment´s body/text.',
  `body` TEXT NOT NULL COMMENT 'Comment´s text.',
  PRIMARY KEY (`idComment`),
  CONSTRAINT `FK_tcommentbody_tcomment` FOREIGN KEY (`idComment`) REFERENCES `teachonsnap`.`tcomment` (`idComment`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tcommentresponse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tcommentresponse` (
  `idCommentResponse` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing comment´s responses.',
  `idCommentParent` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Comment responsed.',
  PRIMARY KEY (`idCommentResponse`),
  INDEX `FK_tcommentresponse_tcommentP` (`idCommentParent` ASC),
  CONSTRAINT `FK_tcommentresponse_tcommentP` FOREIGN KEY (`idCommentParent`) REFERENCES `teachonsnap`.`tcomment` (`idComment`),
  CONSTRAINT `FK_tcommentresponse_tcommentR` FOREIGN KEY (`idCommentResponse`) REFERENCES `teachonsnap`.`tcomment` (`idComment`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tcommentedit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tcommentedit` (
  `idComment` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Table describing comments editions.',
  `dateEdit` DATETIME NOT NULL COMMENT 'Edition date.',
  PRIMARY KEY (`idComment`),  
  CONSTRAINT `FK_tcommentedit_tcomment` FOREIGN KEY (`idComment`) REFERENCES `teachonsnap`.`tcomment` (`idComment`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `teachonsnap`.`tcommentbanned`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tcommentbanned` (
  `idComment` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing banned comments.',
  `reason` VARCHAR(255) NOT NULL COMMENT 'Ban reason.',
  `date` DATETIME NOT NULL COMMENT 'Ban date.',
  `idAdmin` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Admin who banned the comment.',
  PRIMARY KEY (`idComment`),
  INDEX `FK_tcommentbanned_tuser` (`idAdmin` ASC),
  CONSTRAINT `FK_tcommentbanned_tuser` FOREIGN KEY (`idAdmin`) REFERENCES `teachonsnap`.`tuser` (`iduser`),
  CONSTRAINT `FK_tcommentbanned_tcomment` FOREIGN KEY (`idComment`) REFERENCES `teachonsnap`.`tcomment` (`idComment`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessontest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessontest` (
  `idlessontest` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing self evaluation tests.',
  `idlesson` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Lesson this test is evaluating.',
  `numQuestions` TINYINT UNSIGNED NOT NULL COMMENT 'Number of questions in the test.',
  `numAnswers` TINYINT UNSIGNED NOT NULL COMMENT 'Number of options/answers per question.',
  `multiplechoice` TINYINT UNSIGNED NOT NULL COMMENT 'Indicates if the questions are multiple choice.',
  PRIMARY KEY (`idlessontest`),
  UNIQUE INDEX `UK_idlesson` (`idlesson` ASC),
  CONSTRAINT `FK_tlessontest_tlesson` FOREIGN KEY (`idlesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlessontestdraft`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tlessontestdraft` (
  `idlessontest` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing self evaluation test´s drafts.',
  PRIMARY KEY (`idlessontest`),
  CONSTRAINT `FK_tlessontestdraft_tlessont` FOREIGN KEY (`idlessontest`) REFERENCES `teachonsnap`.`tlessontest` (`idlessontest`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tquestion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tquestion` (
  `idquestion` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing questions of a self evaluation test.',
  `idlessontest` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Self evaluation test.',
  `text` VARCHAR(140) NOT NULL COMMENT 'Question.',
  `priority` TINYINT UNSIGNED NOT NULL COMMENT 'Order within the test.',
  PRIMARY KEY (`idquestion`),
  UNIQUE INDEX `UK_idlessontest_text` (`idlessontest` ASC, `text` ASC),
  CONSTRAINT `FK_tquestion_tlessontest` FOREIGN KEY (`idlessontest`) REFERENCES `teachonsnap`.`tlessontest` (`idlessontest`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tanswer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tanswer` (
  `idAnswer` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing answers of self evaluation tests.',
  `idquestion` MEDIUMINT UNSIGNED NOT NULL COMMENT 'Answer´s question.',
  `text` VARCHAR(140) NOT NULL COMMENT 'Answer.',
  `correct` TINYINT UNSIGNED NOT NULL COMMENT 'Indicates if this answer responds corectly to the question.',
  `reason` VARCHAR(140) NOT NULL COMMENT 'Reason why the answer is correct or incorrect.',
  PRIMARY KEY (`idAnswer`),
  UNIQUE INDEX `UK_idquestion_text` (`idquestion` ASC, `text` ASC),
  CONSTRAINT `FK_tanswer_tquestion` FOREIGN KEY (`idquestion`) REFERENCES `teachonsnap`.`tquestion` (`idquestion`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tfollowuser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tfollowuser` (
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Table describing author´s followers.',
  `idFollower` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Follower of this author.',
  PRIMARY KEY (`idUser`, `idFollower`),
  INDEX `FK_tfollowuser_tuserf` (`idFollower` ASC),
  CONSTRAINT `FK_tfollowuser_tuserf` FOREIGN KEY (`idFollower`) REFERENCES `teachonsnap`.`tuser` (`iduser`),
  CONSTRAINT `FK_tfollowuser_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tfollowgroupuser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tfollowgroupuser` (
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Table describing author´s follower groups.',
  `idFollowerGroup` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Group following this author.',
  PRIMARY KEY (`idUser`, `idFollowerGroup`),
  INDEX `FK_tfollowgroupuser_tusergroup` (`idFollowerGroup` ASC),
  CONSTRAINT `FK_tfollowgroupuser_tusergroup` FOREIGN KEY (`idFollowerGroup`) REFERENCES `teachonsnap`.`tusergroup` (`idUserGroup`),
  CONSTRAINT `FK_tfollowgroupuser_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tfollowgrouptag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tfollowgrouptag` (
  `idTag` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing tag´s follower group.',
  `idFollowerGroup` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Group following this tag.',
  PRIMARY KEY (`idTag`, `idFollowerGroup`),
  INDEX `FK_tfollowgrouptag_tusergroup` (`idFollowerGroup` ASC),
  CONSTRAINT `FK_tfollowgrouptag_tusergroup` FOREIGN KEY (`idFollowerGroup`) REFERENCES `teachonsnap`.`tusergroup` (`idUserGroup`),
  CONSTRAINT `FK_tfollowgrouptag_ttag` FOREIGN KEY (`idTag`) REFERENCES `teachonsnap`.`ttag` (`idTag`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tfollowlesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tfollowlesson` (
  `idLesson` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing lesson´s followers.',
  `idFollower` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'Follower of this lesson.',
  PRIMARY KEY (`idLesson`, `idFollower`),
  INDEX `FK_tfollowlesson_tuserf` (`idFollower` ASC),
  CONSTRAINT `FK_tfollowlesson_tuserf` FOREIGN KEY (`idFollower`) REFERENCES `teachonsnap`.`tuser` (`iduser`),
  CONSTRAINT `FK_tfollowlesson_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tvisit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tvisit` (
  `idVisit` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing visits(HTTP sessions) to the application.',
  `ip` INT(11) UNSIGNED NOT NULL COMMENT 'Internet protocol address (IP).',
  `date` DATETIME NOT NULL COMMENT 'Visit date.',
  PRIMARY KEY (`idVisit`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tvisitlesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tvisitlesson` (
  `idLesson` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing visited lessons.',
  `idVisit` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Visit(session) when the lesson was visited.',
  PRIMARY KEY (`idLesson`, `idVisit`),
  INDEX `FK_tvisitlesson_tvisit` (`idVisit` ASC),
  CONSTRAINT `FK_tvisitlesson_tlesson` FOREIGN KEY (`idLesson`) REFERENCES `teachonsnap`.`tlesson` (`idLesson`),
  CONSTRAINT `FK_tvisitlesson_tvisit` FOREIGN KEY (`idVisit`) REFERENCES `teachonsnap`.`tvisit` (`idVisit`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tvisittag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tvisittag` (
  `idTag` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing searched tags.',
  `idVisit` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Visit(session) when the tag was searched.',
  PRIMARY KEY (`idTag`, `idVisit`),
  INDEX `FK_tvisittag_tvisit` (`idVisit` ASC),
  CONSTRAINT `FK_tvisittag_ttag` FOREIGN KEY (`idTag`) REFERENCES `teachonsnap`.`ttag` (`idTag`),
  CONSTRAINT `FK_tvisittag_tvisit` FOREIGN KEY (`idVisit`) REFERENCES `teachonsnap`.`tvisit` (`idVisit`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tvisituser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tvisituser` (
  `idVisit` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing user logged in a visit(session).',
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'User who logged in this visit.',
  PRIMARY KEY (`idVisit`),
  INDEX `FK_tvisituser_tuser` (`idUser` ASC),
  CONSTRAINT `FK_tvisituser_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`),
  CONSTRAINT `FK_tvisituser_tVisit` FOREIGN KEY (`idVisit`) REFERENCES `teachonsnap`.`tvisit` (`idVisit`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tvisittest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tvisittest` (
  `idVisitTest` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Table describing test results.',
  `idVisit` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Visit(session) when logged user performed the test.',
  `idLessonTest` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Performed test.',
  `points` TINYINT(3) UNSIGNED NOT NULL COMMENT 'Total points user scored.',
  `date` DATETIME NOT NULL COMMENT 'Date when the test was performed.',
  PRIMARY KEY (`idVisitTest`),
  UNIQUE INDEX `UK_idVisit_idLT_date` (`idLessonTest` ASC, `idVisit` ASC, `date` ASC),
  INDEX `FK_tvisittest_tvisituser` (`idVisit` ASC),
  CONSTRAINT `FK_tvisittest_tvisituser` FOREIGN KEY (`idVisit`) REFERENCES `teachonsnap`.`tvisituser` (`idVisit`),
  CONSTRAINT `UK_idVisit_idLT_date` FOREIGN KEY (`idLessonTest`) REFERENCES `teachonsnap`.`tlessontest` (`idlessontest`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tvisittestkos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tvisittestkos` (
  `idVisitTest` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing missed/failed question on a test.',
  `idQuestion` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Missed/failed question.',
  PRIMARY KEY (`idVisitTest`, `idQuestion`),
  INDEX `FK_tvisittestkos_tquestion` (`idQuestion` ASC),
  CONSTRAINT `FK_tvisittestkos_tvisittest` FOREIGN KEY (`idVisitTest`) REFERENCES `teachonsnap`.`tvisittest` (`idVisitTest`),
  CONSTRAINT `FK_tvisittestkos_tquestion` FOREIGN KEY (`idQuestion`) REFERENCES `teachonsnap`.`tquestion` (`idquestion`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teachonsnap`.`tvisittestrank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachonsnap`.`tvisittestrank` (
  `idLessonTest` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Table describing best scores in tests.',
  `idUser` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'User who performed the test.',
  `idVisitTest` MEDIUMINT(8) UNSIGNED NOT NULL COMMENT 'Best user´s result.',
  `attempts` TINYINT(3) UNSIGNED NOT NULL COMMENT 'Number of performed attempts to achieve the score.',
  PRIMARY KEY (`idLessonTest`, `idUser`),
  INDEX `FK_tvtestrank_tuser` (`idUser` ASC),
  INDEX `FK_tvtestrank_tvt` (`idVisitTest` ASC),
  CONSTRAINT `FK_tvtestrank_tvt` FOREIGN KEY (`idVisitTest`) REFERENCES `teachonsnap`.`tvisittest` (`idVisitTest`),
  CONSTRAINT `FK_tvtestrank_tuser` FOREIGN KEY (`idUser`) REFERENCES `teachonsnap`.`tuser` (`iduser`),
  CONSTRAINT `FK_tvtestrank_tlt` FOREIGN KEY (`idLessonTest`) REFERENCES `teachonsnap`.`tlessontest` (`idlessontest`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- END Schema teachonsnap
-- -----------------------------------------------------
