CREATE KEYSPACE flashcardsapp WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };
USE flashcardsapp;

create table flashcard (
    id uuid,
    question varchar,
    answer varchar,
    tags map<uuid, varchar>,
    PRIMARY KEY (id));

insert into flashcard (id, question, answer, tags)
    values (0791e3ec-c072-11e4-8dfc-aa07a5b093db, 'question1', 'answer1', { eaa488a0-b0d8-11e4-af90-12e3f512a338  : 'tag1'});
insert into flashcard (id, question, answer, tags)
    values (0791e63a-c072-11e4-8dfc-aa07a5b093db, 'question2', 'answer2', { eaa48bd4-b0d8-11e4-af90-12e3f512a338  : 'tag2'});

create table tag (
    id uuid,
    name varchar,
    PRIMARY KEY (id));
create index name on tag (name);
insert into tag (id, name) values (eaa488a0-b0d8-11e4-af90-12e3f512a338, 'tag1');
insert into tag (id, name) values (eaa48bd4-b0d8-11e4-af90-12e3f512a338, 'tag2');

create table tag_flashcard (
    tag_id uuid,
    flashcard_id uuid,
    question varchar,
    answer varchar,
    PRIMARY KEY (tag_id, flashcard_id ));

insert into tag_flashcard (tag_id, flashcard_id, question, answer)
    values (eaa488a0-b0d8-11e4-af90-12e3f512a338, 0791e3ec-c072-11e4-8dfc-aa07a5b093db, 'question1', 'answer1');
insert into tag_flashcard (tag_id, flashcard_id, question, answer)
    values (eaa48bd4-b0d8-11e4-af90-12e3f512a338, 0791e63a-c072-11e4-8dfc-aa07a5b093db, 'question2', 'answer2');

create table user (
    id uuid,
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
    createduserid uuid,
    modifieduserid uuid,
    PRIMARY KEY (id));
create index user_open_id on user (openid);
insert into user (id, country, createddate, email, firstname, fullname, language, lastlogindate, lastname, nickname, openid, updateddate, createduserid, modifieduserid) VALUES (9caa6c8e-b720-11e4-a71e-12e3f512a338,NULL,'2012-08-18 00:00:00','','apiuser',NULL,NULL,'2012-08-18 00:00:00','apiuser',NULL,'apiuser','2012-08-18 00:00:00',9caa6c8e-b720-11e4-a71e-12e3f512a338,NULL);
