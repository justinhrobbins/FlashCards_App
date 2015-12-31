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