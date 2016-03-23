-- TeachOnSnap MySQL INITIAL DATA for INTEGRATION TESTS
-- @Author: David Júlvez Estrada
-- 2014-2016

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tlink`
-- -----------------------------------------------------
INSERT INTO  `teachonsnap_it`.tlink(url, `desc`) VALUES('http://url','url');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tuser`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tuser` (`iduser`, `email`, `password`, `idLanguage`, `firstName`, `lastName`, `dateIni`) VALUES ('1', 'teachonsnap@gmail.com',password('secret'), '1', 'firstName', 'lastName', now());

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tlesson`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tlesson` (`idLesson`, `idUser`, `idLanguage`, `title`, `date`, `URIname`) VALUES ('1', '1', '1', 'title', now(), 'uri');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tlessononline`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tlessononline` (`idLesson`, `date`) VALUES ('1', now());

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`ttag`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`ttag` (`idTag`, `tag`) VALUES ('1', 'tag');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tusergroup`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tusergroup` (`idUserGroup`, `groupName`) VALUES ('1', 'name');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tlessontest`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tlessontest` (`idlessontest`, `idlesson`, `numQuestions`, `numAnswers`, `multiplechoice`) VALUES ('1', '1', '1', '1', '0');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tquestion`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tquestion` (`idquestion`, `idlessontest`, `text`, `priority`) VALUES ('1', '1', 'text', '1');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tanswer`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tanswer` (`idAnswer`, `idquestion`, `text`, `correct`, `reason`) VALUES ('1', '1', 'text', '0', 'reason');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tcomment`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tcomment` (`idComment`, `idLesson`, `idUser`, `date`) VALUES ('1', '1', '1', now());

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tcommentbody`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tcommentbody` (`idComment`, `body`) VALUES ('1', 'comment');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tvisit`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tvisit` (`idVisit`, `ip`, `date`) VALUES ('1', inet_aton('127.0.0.1'), '2015-01-01');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tvisitlesson`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tvisitlesson` (`idLesson`, `idVisit`) VALUES ('1', '1');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tvisittag`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tvisittag` (`idTag`, `idVisit`) VALUES ('1', '1');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tvisituser`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tvisituser` (`idVisit`, `idUser`) VALUES ('1', '1');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tvisitest`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tvisittest` (`idVisitTest`, `idVisit`, `idLessonTest`, `points`, `date`) VALUES ('1', '1', '1', '1', now());

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tlessonmedia`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tlessonmedia` (`idlessonmedia`, `idlesson`) VALUES ('1', '1');

-- -----------------------------------------------------
-- Table `teachonsnap_it`.`tmediafile`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tmediafile` (`idmediafile`, `idlessonmedia`, `idmediarepository`, `idmediamimetype`, `filename`, `filesize`) VALUES ('1', '1', '1', '1', 'file.ext', '100');