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