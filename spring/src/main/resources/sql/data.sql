INSERT INTO meme(TITLE, IMAGE_URL, VIEW_COUNT, CREATE_DATE) VALUES('안녕1', 'https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cad31965-0c62-44ca-ae45-4e050d4ec9b8t', 1, now());
INSERT INTO meme(TITLE, IMAGE_URL, VIEW_COUNT, CREATE_DATE) VALUES('안녕9', 'https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cad31965-0c62-44ca-ae45-4e050d4ec9b8t', 9, now());
INSERT INTO meme(TITLE, IMAGE_URL, VIEW_COUNT, CREATE_DATE) VALUES('안녕4', 'https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cad31965-0c62-44ca-ae45-4e050d4ec9b8t', 4, now());
INSERT INTO meme(TITLE, IMAGE_URL, VIEW_COUNT, CREATE_DATE) VALUES('안녕2', 'https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cad31965-0c62-44ca-ae45-4e050d4ec9b8t', 2, now());

INSERT INTO category(NAME) VALUES('카테고리 명1');
INSERT INTO category(NAME) VALUES('카테고리 명2');

INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('태그 제목1', 1, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('태그 제목2', 10, 2);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('태그 제목3', 14, 2);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('태그 제목4', 6, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('태그 제목5', 11, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무', 11, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무시', 18, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무한', 12, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무한도', 13, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무한도전', 20, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무전', 18, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무한전', 8, 1);
INSERT INTO tag(NAME, VIEW_COUNT, CATEGORY_ID) VALUES('무도전', 2, 1);


INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(1, 1);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 1);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(1, 2);


