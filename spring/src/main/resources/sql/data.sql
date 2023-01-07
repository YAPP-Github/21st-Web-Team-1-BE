INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('php', 'php 개발자 밈입니다.', 115, 5, now());
INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('에타 시험기간', '에타 시험기간 밈 모음입니다', 93, 5, now());
INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('페페', '초록 개구리 페페 밈입니다.', 45, 5, now());
INSERT INTO meme(name, view_count, share_count, created_date) VALUES('안녕1', 2, 5, now());
INSERT INTO meme(name, view_count, share_count, created_date) VALUES('안녕2', 3, 5, now());
INSERT INTO meme(name, view_count, share_count, created_date) VALUES('안녕3', 14, 5, now());

INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932543-7a8c2025-f162-4ffa-8486-8ca425cc2cf8.png', 960, 987, 1);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932649-cc578130-3689-4b78-941b-d42828127b79.png', 517, 706, 2);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932697-6609ff9b-431c-4eab-ac77-060ce29a9222.png', 466, 631, 2);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932754-083d9814-691e-4ce0-8e9f-8f1fe7eb6f2e.png', 488, 566, 2);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932924-a7c42ce4-266d-4cbd-ae0d-8f09dd4f8a50.png', 225, 225, 3);


INSERT INTO category(name, priority) VALUES('카테고리 명1', 100);
INSERT INTO category(name, priority) VALUES('카테고리 명2', 200);

INSERT INTO tag(name, view_count, category_id) VALUES('개발자', 52, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('에브리타임', 49, 2);
INSERT INTO tag(name, view_count, category_id) VALUES('시험기간', 34, 2);
INSERT INTO tag(name, view_count, category_id) VALUES('페페', 26, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('유머', 140, 1);
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
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 2);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 3);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(3, 4);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(1, 5);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 5);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(3, 5);


