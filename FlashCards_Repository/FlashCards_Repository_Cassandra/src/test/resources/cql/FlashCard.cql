create table flashcard (
    id bigint,
    question varchar,
    answer varchar,
    tags map<bigint, varchar>,
    PRIMARY KEY (id));

insert into flashcard (id, question, answer, tags)
    values (1, 'question1', 'answer1', { 1  : 'tag1'});
insert into flashcard (id, question, answer, tags)
    values (2, 'question2', 'answer2', { 2  : 'tag2'});