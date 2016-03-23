-- TeachOnSnap MySQL INITIAL DATA Script
-- @Author: David Júlvez Estrada
-- 2014-2016

-- -----------------------------------------------------
-- Table `teachonsnap`.`tlanguage`
-- -----------------------------------------------------
INSERT INTO `teachonsnap`.`tlanguage` (`idLanguage`, `language`) VALUES ('1', 'en');
INSERT INTO `teachonsnap`.`tlanguage` (`idLanguage`, `language`) VALUES ('2', 'es');


-- -----------------------------------------------------
-- Table `teachonsnap`.`tlanguagedefault`
-- -----------------------------------------------------
INSERT INTO `teachonsnap`.`tlanguagedefault` (`idLanguage`) VALUES ('1');

-- -----------------------------------------------------
-- Table `teachonsnap`.`tmediatype`
-- -----------------------------------------------------
ALTER TABLE teachonsnap_it.tmediatype AUTO_INCREMENT = 0;
INSERT INTO `teachonsnap_it`.`tmediatype` (`idmediatype`, `mediatype`, `desc`) VALUES (0, 'VIDEO', 'Tipo video');
INSERT INTO `teachonsnap_it`.`tmediatype` (`idmediatype`, `mediatype`, `desc`) VALUES (1, 'AUDIO', 'Tipo audio');
INSERT INTO `teachonsnap_it`.`tmediatype` (`idmediatype`, `mediatype`, `desc`) VALUES (2, 'IMAGE', 'Tipo imagen');

-- -----------------------------------------------------
-- Table `teachonsnap`.`tmediarepository`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tmediarepository` (`idmediarepository`, `URI`, `filePathSeparator`, `desc`) VALUES ('1', 'C:\\\\System\\\\cfp\\\\repo', '\\\\', 'Repo por defecto');

-- -----------------------------------------------------
-- Table `teachonsnap`.`tmediamimetype`
-- -----------------------------------------------------
INSERT INTO `teachonsnap_it`.`tmediamimetype` (`idmediamimetype`, `idmediatype`, `mimetype`, `extension`) VALUES ('1', '0', 'video/mp4', 'mp4');
INSERT INTO `teachonsnap_it`.`tmediamimetype` (`idmediamimetype`, `idmediatype`, `mimetype`, `extension`) VALUES ('2', '0', 'video/webm', 'webm');
INSERT INTO `teachonsnap_it`.`tmediamimetype` (`idmediamimetype`, `idmediatype`, `mimetype`, `extension`) VALUES ('3', '1', 'audio/mpeg', 'mp3');
INSERT INTO `teachonsnap_it`.`tmediamimetype` (`idmediamimetype`, `idmediatype`, `mimetype`, `extension`) VALUES ('4', '0', 'video/3gpp', '3gp');
INSERT INTO `teachonsnap_it`.`tmediamimetype` (`idmediamimetype`, `idmediatype`, `mimetype`, `extension`) VALUES ('5', '2', 'image/jpeg', 'jpg');
