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