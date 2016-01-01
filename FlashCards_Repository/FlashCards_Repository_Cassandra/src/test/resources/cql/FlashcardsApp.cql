CREATE KEYSPACE flashcardsapp WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };
USE flashcardsapp;

create table flashcard (
    id bigint,
    question varchar,
    answer varchar,
    tags map<bigint, varchar>,
    PRIMARY KEY (id));

insert into flashcard (id, question, answer, tags)
    values (1, 'question1', 'answer1', { 1  : 'tag1'});
insert into flashcard (id, question, answer, tags)
    values (2, 'question2', 'answer2', { 1  : 'tag2'});

create table tag (
    id bigint,
    name varchar,
    PRIMARY KEY (id));
create index name on tag (name);
insert into tag (id, name) values (1, 'tag1');
insert into tag (id, name) values (2, 'tag2');

create table tag_flashcard (
    tag_id bigint,
    flashcard_id bigint,
    question varchar,
    answer varchar,
    PRIMARY KEY (tag_id, flashcard_id ));

insert into tag_flashcard (tag_id, flashcard_id, question, answer)
    values (1, 1, 'question1', 'answer1');
insert into tag_flashcard (tag_id, flashcard_id, question, answer)
    values (2, 2, 'question2', 'answer2');

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
