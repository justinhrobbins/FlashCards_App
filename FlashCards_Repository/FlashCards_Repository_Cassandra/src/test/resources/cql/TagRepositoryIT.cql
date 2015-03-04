create table tag (id uuid PRIMARY KEY, name varchar);
create index name on tag (name);
insert into tag (id, name) values (eaa488a0-b0d8-11e4-af90-12e3f512a338, 'tag1');
insert into tag (id, name) values (eaa48bd4-b0d8-11e4-af90-12e3f512a338, 'tag2');