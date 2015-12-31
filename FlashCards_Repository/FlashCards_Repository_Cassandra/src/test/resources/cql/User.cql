create table user (
    id bigint,
    country varchar,
    createddate timestamp,
    email varchar,
    firstname varchar,
    fullname varchar,
    language varchar,
    lastlogindate timestamp,
    lastname varchar,
    nickname varchar,
    openid varchar,
    updateddate timestamp,
    createduserid bigint,
    modifieduserid bigint,
    PRIMARY KEY (id));
create index user_open_id on user (openid);
insert into user (id, country, createddate, email, firstname, fullname, language, lastlogindate, lastname, nickname, openid, updateddate, createduserid, modifieduserid) VALUES (1,NULL,'2012-08-18 00:00:00','','apiuser',NULL,NULL,'2012-08-18 00:00:00','apiuser',NULL,'apiuser','2012-08-18 00:00:00',1,NULL);
