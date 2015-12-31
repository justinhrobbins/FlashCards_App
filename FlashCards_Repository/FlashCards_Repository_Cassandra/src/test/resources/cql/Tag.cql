create table tag (
    id bigint,
    name varchar,
    PRIMARY KEY (id));
create index name on tag (name);
insert into tag (id, name) values (1, 'tag1');
insert into tag (id, name) values (2, 'tag2');