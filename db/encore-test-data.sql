
-- Event Test Data 

INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('The Flying Gentlemen', NOW(), NOW(), NOW(), 'Food festival for men');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('Fight and Carry', NOW(), NOW(), NOW(), 'I dont know');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('Its a Long Road', NOW(), NOW(), NOW(), 'Just another description.');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('Comicon Ottawa', NOW(), NOW(), NOW(), 'Come meet Scully. She wants to believe.');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('Gentleman of the Road', NOW(), NOW(), NOW(), 'Mumford and Sons outdoor music festival.');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('Greenday 21 Guns', NOW(), NOW(), NOW(), 'Greenday concert');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('Comicon Toronto', NOW(), NOW(), NOW(), 'Come meet Scully. She wants to believe.');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`) VALUES ('BlackBerry Jam', NOW(), NOW(), NOW(), 'Learn how to setup your Android apps on BlackBerry 10.');
INSERT INTO `encoreDb`.`EVENTS` (`TITLE`, `DATE_CREATED`, `DATE_UPDATED`, `EVENT_DATE`, `DESCRIPTION`, `EVENT_LAT`, `EVENT_LONG`) VALUES ('Bluesfest', NOW(), NOW(), NOW(), 'Come to Ottawas most popular music festival!!', 45.3702816, -75.7020744);

-- User Test Data

INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Katrina', 'Butler', 'kat.a.butler@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `PICTURE_URL`) VALUES ('Tom', 'Riddle', 'voldemort@hogwarts.co.uk', 'passwd', '/images/voldemort.jpg');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Troy', 'Brodie', 'tbrodie@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Dave', 'Bolland', 'dbolland@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('David', 'Clarkson', 'dclarkson@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Peter', 'Holland', 'pholland@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Nazem', 'Kadri', 'nkadri@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `PICTURE_URL`) VALUES ('Phil', 'Kessel', 'pkessel@gmail.com', 'passwd', '/images/pkessel.jpg');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `PICTURE_URL`) VALUES ('Joffery', 'Lupul', 'jlupul@gmail.com', 'passwd', '/images/jlupul.jpg');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Colton', 'Orr', 'corr@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `PICTURE_URL`) VALUES ('Mason', 'Raymond', 'mraymond@gmail.com', 'passwd', '/images/mraymond.jpg');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Cody', 'Franson', 'codyfranson@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Mark', 'Fraser', 'markfraser@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Dion', 'Phaneuf', 'dphaneuf@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Jonathan', 'Benerier', 'jbenerier@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `PICTURE_URL`) VALUES ('James', 'Reimer', 'jreimer@gmail.com', 'passwd', '/images/jreimer.jpg');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Jason', 'Spezza', 'jspezza@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `PICTURE_URL`) VALUES ('Erik', 'Karlsson', 'ekarlsson@gmail.com', 'passwd', '/images/ekarlsson.jpg');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('James', 'van Reimsdyke', 'jvanReimsdyke@gmail.com', 'passwd');
INSERT INTO `encoreDb`.`USERS` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES ('Philip', 'Fry', 'pfry@gmail.com', 'passwd');


-- Friend Relationships

-- Full friend relationship with Katrina
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='codyfranson@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='markfraser@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='jvanReimsdyke@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='pfry@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='jlupul@gmail.com'));

INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='codyfranson@gmail.com'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='markfraser@gmail.com'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='jvanReimsdyke@gmail.com'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='pfry@gmail.com'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='jlupul@gmail.com'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));



-- Users requested USERS_FOLLOWING
-- Katrina's Friend Request
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='pkessel@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='nkadri@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'), (select USER_ID from USERS where EMAIL='jreimer@gmail.com'));

-- Other Friend Requests to Katrina
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='dphaneuf@gmail.com'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='voldemort@hogwarts.co.uk'), (select USER_ID from USERS where EMAIL='kat.a.butler@gmail.com'));

-- Troy Brodie Freind request to Mason Raymond
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='tbrodie@gmail.com'), (select USER_ID from USERS where EMAIL='mraymond@gmail.com'));


-- Full friend relationship with Erik Karlesson
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'), (select USER_ID from USERS where EMAIL='jvanReimsdyke@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'), (select USER_ID from USERS where EMAIL='jspezza@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'), (select USER_ID from USERS where EMAIL='mraymond@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'), (select USER_ID from USERS where EMAIL='jreimer@gmail.com'));

INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='jvanReimsdyke@gmail.com'), (select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='jspezza@gmail.com'), (select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='mraymond@gmail.com'), (select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'));
INSERT INTO `encoreDb`.`USERS_FOLLOWING` (`USER_ID`, `FOLLOWING_USER_ID`) VALUES ((select USER_ID from USERS where EMAIL='jreimer@gmail.com'), (select USER_ID from USERS where EMAIL='ekarlsson@gmail.com'));


-- Adding events to users
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(1, 1);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(1, 2);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(1, 3);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(1, 4);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(1, 5);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(1, 6);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(2, 1);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(2, 2);
INSERT INTO `encoreDb`.`USERS_has_EVENTS` (`USERS_USER_ID`, `EVENTS_EVENT_ID`) VALUES(2, 3);


-- Adding activities
