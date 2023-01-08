INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('php', 'php 개발자 밈입니다.', 115, 5, now());
INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('에타 시험기간', '에타 시험기간 밈 모음입니다', 93, 5, now());
INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('페페', '초록 개구리 페페 밈입니다.', 45, 5, now());
INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('평생 입 다물게 해줄까?', '박명수가 무한도전 123화에서 한 말', 445, 5, now());
INSERT INTO meme(name, description, view_count, share_count, created_date) VALUES('그래도... 일은 해야지...', '무한도전 중 박명수 어록 중 하나', 455, 5, now());

INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932543-7a8c2025-f162-4ffa-8486-8ca425cc2cf8.png', 960, 987, 1);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932649-cc578130-3689-4b78-941b-d42828127b79.png', 517, 706, 2);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932697-6609ff9b-431c-4eab-ac77-060ce29a9222.png', 466, 631, 2);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932754-083d9814-691e-4ce0-8e9f-8f1fe7eb6f2e.png', 488, 566, 2);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/210932924-a7c42ce4-266d-4cbd-ae0d-8f09dd4f8a50.png', 225, 225, 3);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/211177350-892daee2-3438-4fd9-a14d-6d10d3f69c74.png', 686, 377, 4);
INSERT INTO image(image_url, width, height, meme_id) VALUES('https://user-images.githubusercontent.com/62461857/211178127-989bde34-a0cd-4dd9-81b7-abbf44b7deef.png', 609, 609, 5);

INSERT INTO category(name, priority) VALUES('직업', 100);
INSERT INTO category(name, priority) VALUES('캐릭터', 200);
INSERT INTO category(name, priority) VALUES('예능', 300);
INSERT INTO category(name, priority) VALUES('인물', 300);
INSERT INTO category(name, priority) VALUES('감정', 400);
INSERT INTO category(name, priority) VALUES('기타', 500);

INSERT INTO tag(name, view_count, category_id) VALUES('개발자', 52, 1);
INSERT INTO tag(name, view_count, category_id) VALUES('에브리타임', 49, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('시험기간', 34, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('페페', 26, 2);
INSERT INTO tag(name, view_count, category_id) VALUES('유머', 140, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('박명수', 140, 4);
INSERT INTO tag(name, view_count, category_id) VALUES('서러움', 140, 5);
INSERT INTO tag(name, view_count, category_id) VALUES('분노', 140, 5);
INSERT INTO tag(name, view_count, category_id) VALUES('무한도전', 220, 3);
INSERT INTO tag(name, view_count, category_id) VALUES('무', 11, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('무시', 18, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('무한', 12, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('무한도', 13, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('무전', 18, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('무한전', 8, 6);
INSERT INTO tag(name, view_count, category_id) VALUES('무도전', 2, 6);


INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(1, 1);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(1, 5);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 2);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 3);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(2, 5);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(3, 4);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(4, 5);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(4, 6);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(4, 8);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(4, 9);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(5, 5);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(5, 6);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(5, 7);
INSERT INTO meme_tag(MEME_ID, TAG_ID) VALUES(5, 9);


