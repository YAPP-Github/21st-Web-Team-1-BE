INSERT INTO meme(name, view_count, created_date) VALUES('안녕1', 1, now());
INSERT INTO meme(name, view_count, created_date) VALUES('안녕9', 9, now());
INSERT INTO meme(name, view_count, created_date) VALUES('안녕4', 4, now());
INSERT INTO meme(name, view_count, created_date) VALUES('안녕2', 2, now());

INSERT INTO category(name, priority) VALUES('카테고리 명1', 100);
INSERT INTO category(name, priority) VALUES('카테고리 명2', 200);

INSERT INTO tag(name, view_count, category_id) VALUES('태그 제목1', 1, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('태그 제목2', 10, 2);
INSERT INTO tag(name, view_count, category_id) VALUES('태그 제목3', 14, 2);
INSERT INTO tag(name, view_count, category_id) VALUES('태그 제목4', 6, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('태그 제목5', 11, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무', 11, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무시', 18, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무한', 12, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무한도', 13, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무한도전', 20, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무전', 18, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무한전', 8, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('무도전', 2, 1);


INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(1, 1);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 1);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(1, 2);


