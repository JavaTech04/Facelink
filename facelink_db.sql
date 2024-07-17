-- CREATE DATABASE facelink_db
-- USE facelink_db
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name NVARCHAR(15)
);

CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email NVARCHAR(255) UNIQUE,
    phone_number NVARCHAR(13) UNIQUE,
    password NVARCHAR(150),
    is_locked BIT,
    is_enabled BIT,
		verified_account BIT,
    create_date DATE
);

CREATE TABLE userRole (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT,
    account_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE relationships (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status_name NVARCHAR(100)
);

CREATE TABLE accountInfo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT,
	bio LONGTEXT,
	cover_photo NVARCHAR(500),
    avatar NVARCHAR(500),
	first_name NVARCHAR(20),
	last_name NVARCHAR(20),
    full_name NVARCHAR(100),
    other_name NVARCHAR(50),
    date_of_birth DATE,
    gender BIT,
    education NVARCHAR(200),
    work NVARCHAR(100),
    current_city NVARCHAR(100),
    hometown NVARCHAR(150),
    relationship_id INT,
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (relationship_id) REFERENCES relationships(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE accountDetail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    followers INT,
    following INT,
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE listFriends(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	account_id BIGINT,
	friend_info BIGINT,
	status INT,
	FOREIGN KEY (friend_info) REFERENCES accounts(id)
);
CREATE TABLE posts(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	type NVARCHAR(30),
	content LONGTEXT,
	url_image LONGTEXT,
	url_video LONGTEXT,
	post_audience NVARCHAR(20),
	create_date DATETIME,
	account_id BIGINT,
	FOREIGN KEY (account_id) REFERENCES accounts(id)
);
ALTER TABLE posts MODIFY content TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Insert data
-- Insert data into roles
INSERT INTO roles (role_name) VALUES ('ADMIN'), ('USER'), ('GUEST');
-- Insert data into relationships
INSERT INTO relationships (status_name) VALUES ('Single'), ('In a relationship'), ('Married');
-- Insert data into accounts
INSERT INTO accounts (email, password, is_locked, is_enabled, verified_account, create_date)
VALUES
('nonghoangvu04@gmail.com', '$2a$12$M1R9ZxIYxr5BjfJNPRz.Ououm2lx0J.1Zm90NvZrs7wRhNolCJIkq', 0, 0, 1, '2024-12-01'),
('ntn@gmail.com', '$2a$12$Z0uXLw1M7IR7B5XvRP1u7ekHXwgm7oqfnEybxbyEgZmBuCJMC.mym', 0, 0, 0, '2024-02-01'),
('lml@gmail.com', '$2a$12$Z0uXLw1M7IR7B5XvRP1u7ekHXwgm7oqfnEybxbyEgZmBuCJMC.mym', 0, 0, 0, '2024-02-01'),
('nqa@gmail.com', '$2a$12$Z0uXLw1M7IR7B5XvRP1u7ekHXwgm7oqfnEybxbyEgZmBuCJMC.mym', 0, 0, 0, '2024-02-01'),
('charlie@gmail.com', '$2a$12$Z0uXLw1M7IR7B5XvRP1u7ekHXwgm7oqfnEybxbyEgZmBuCJMC.mym', 0, 0, 1, '2024-02-01'),
('trump@gmail.com', '$2a$12$Z0uXLw1M7IR7B5XvRP1u7ekHXwgm7oqfnEybxbyEgZmBuCJMC.mym', 0, 0, 1, '2024-02-01');
-- Insert data into userRole
INSERT INTO userRole (account_id, role_id)
VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2);
-- Insert data into accountInfo
INSERT INTO accountInfo (account_id, avatar,first_name, last_name, full_name, other_name, date_of_birth, gender, education, work, current_city, hometown, relationship_id, cover_photo)
VALUES
(1, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721044915/n5k9h6mfdbkxqwbvmp8q.jpg','Vu', 'Nong Hoang', 'Nong Hoang Vu', 'Mr Vuz', '2004-12-01', 1, 'FPT University', 'FPT Software', 'Ha Noi', 'Huu Lung Lang Son', 2, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721044931/frwxjbjf7necprhoebe3.jpg'),
(2, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045030/u9cxsfzvynh6bd1oz9c0.jpg','Nguyet', 'Nguyen Thi' , 'Nguyen Thi', 'NTN', '2000-05-14', 1, 'Vu School', 'Vu Work', 'Son Tay', 'Hanoi, Vietnam', 2, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045030/u9cxsfzvynh6bd1oz9c0.jpg'),
(3, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045041/vznufxhuxghgcvusimqa.jpg','Le', 'Luong My' , 'Luong My', 'LML', '2000-05-01', 1, 'Vu School', 'Vu Work', 'Lang Son', 'Huu Lung Lang Son', 1, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045041/bus3xrdyjbivnwmerpq5.jpg'),
(4, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045005/vivmctfyqtioxrky8xy0.jpg','Anh', 'Quynh' , 'Quynh Anh', 'NQA', '2006-05-01', 1, 'Vu School', 'Vu Work', 'Bac Ninh', 'Bac Ninh', 1, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721050755/nvxdic1owqdd94nxwrw3.jpg'),
(5, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721046071/bddoksqc2wwhqrydyosb.jpg','Puth', 'Charlie' , 'Charlie Puth', 'CP', '1991-02-02', 1, 'Vu School', 'Singer song writer record producer', 'Rumson, New Jersey, U.S', 'Rumson, New Jersey, U.S.', 1, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721050676/gwbnpaafwyuf2zljokfo.png'),
(6, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721048311/v6uttdy4fkam7vo77hwu.jpg','Trump', 'Donald' , 'Donal Trump', 'Trump', '1946-06-14', 1, 'Vu School', 'President of the USA', 'New York City, U.S.', 'New York City, U.S.', 3, 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721050569/ujkda9tdhrx6nrp6eald.jpg');
-- Insert data into accountDetail
INSERT INTO accountDetail (followers, following, account_id)
VALUES
(9880000, 0, 1),
(233, 47, 2),
(493, 26, 3),
(2102, 43, 4),
(5545324, 32, 5),
(9545324, 42, 6);
-- Insert data into listFriends
INSERT INTO listFriends(account_id, friend_info, status) VALUES
 (1, 3, 1),
 (3, 1, 1);
 -- Insert data into posts
 INSERT INTO posts(type, content, url_image, url_video, post_audience, create_date, account_id) VALUES
 ('CONTENT_IMAGE', 'haha', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045041/xommusgtkoqcjcsbls00.jpg', NULL, 'PUBLIC', '2024-07-15', 3),
 ('CONTENT_VIDEO', 'Viral song WHO I AM -- Alan Walker, Putri Ariani, Peder Elias', NULL, 'https://res.cloudinary.com/dswqplrdx/video/upload/v1721045602/bdbfcy8dgx0i7mrhyapy.mp4', 'PUBLIC', '2024-07-15', 1),
 ('CONTENT_VIDEO', 'Joker & Harley Quinn- INSANE Love Story', NULL, 'https://res.cloudinary.com/dswqplrdx/video/upload/v1721045459/telg441zlvyli3v73m5s.mp4', 'PUBLIC', '2024-07-15', 1),
 ('CONTENT_VIDEO', 'My favorite song  @AlexanderStewart #sing #singing #thegreatestshowman', NULL, 'https://res.cloudinary.com/dswqplrdx/video/upload/v1721045808/knjw0vvi2pkghvjc1qdk.mp4', 'PUBLIC', '2024-07-15', 1),
 ('CONTENT_VIDEO', '3.31 @sabrinacarpenter @DanAndShay', NULL, 'https://res.cloudinary.com/dswqplrdx/video/upload/v1721045963/uawyt7nvfhoo6shy1zlz.mp4', 'PUBLIC', '2024-07-15', 5),
 ('CONTENT_IMAGE', '', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721044980/bklsxz8lcdn75na7k96n.jpg', NULL, 'PUBLIC', '2024-07-15', 6),
 ('CONTENT_IMAGE', 'Rock in Rio on sale now! http://Rockinrio.ticketmaster.com.br', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721046069/kbeev7kjaehk2e5lztvy.jpg', NULL, 'PUBLIC', '2024-07-15', 5),
 ('CONTENT_IMAGE', 'Im playing some of my favorite songs on SiriusXM Hits 1. Listen now on the SiriusXM app: https://sxm.app.link/CharliePuthGuestDJ', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721046069/wnmeu160d4ncm1efgjkb.jpg', NULL, 'PUBLIC', '2024-07-15', 5),
 ('CONTENT_IMAGE', '', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721044966/ajmznffm8vi92bvprj7z.jpg', NULL, 'PUBLIC', '2024-07-15', 6),
('CONTENT_IMAGE', 'Shanghai - SOLD OUT!', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721046069/pfbx4jpdugetskuck0pq.jpg', NULL, 'PUBLIC', '2024-07-15', 5),
('CONTENT_IMAGE', 'Sad with Nong Hoang Vu :(((', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045005/mlttsxv4b2obvr8otomx.jpg', NULL, 'PUBLIC', '2024-07-15', 4),
('CONTENT_IMAGE', 'Love Nong Hoang Vu', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045041/bus3xrdyjbivnwmerpq5.jpg', NULL, 'PUBLIC', '2024-07-15', 3),
('CONTENT_IMAGE', 'Cool!!!', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045040/r8c32hzxzc17w3not62b.jpg', NULL, 'PUBLIC', '2024-07-15', 3),
('CONTENT_IMAGE', 'Love Vu so much <3', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721045040/jxiizg0x0tphqi6xy17k.jpg', NULL, 'PUBLIC', '2024-07-15', 3),
('CONTENT_IMAGE', '', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721044958/vwrzlfc1t9pcou4nubm4.jpg', NULL, 'PUBLIC', '2024-07-15', 6),
('CONTENT_IMAGE', '', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721044974/xgobpxsw8vv2x9jbtwrx.jpg', NULL, 'PUBLIC', '2024-07-15', 6),
('CONTENT_IMAGE', '"One fine day, you will forget gain unforgettable things and meet people worth meeting in the world."', 'https://res.cloudinary.com/dswqplrdx/image/upload/v1721044931/frwxjbjf7necprhoebe3.jpg', NULL, 'PUBLIC', '2024-07-15', 1),
('CONTENT_VIDEO', '"No need to rush, if we are truly each other, we will be each other..."', NULL, 'https://res.cloudinary.com/dswqplrdx/video/upload/v1721051309/qxve4nfzqdptewfvw2qh.mp4', 'PUBLIC', '2024-07-16', 1);
