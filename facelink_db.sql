CREATE DATABASE facelink_db

USE facelink_db

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
		bio NVARCHAR(500),
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
);
ALTER TABLE accountInfo MODIFY bio TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

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
-- Insert data
-- Insert data into roles
INSERT INTO roles (role_name) VALUES ('ADMIN'), ('USER'), ('GUEST');
-- Insert data into relationships
INSERT INTO relationships (status_name) VALUES ('Single'), ('In a relationship'), ('Married');
-- Insert data into accounts
INSERT INTO accounts (email, phone_number, password, is_locked, is_enabled, verified_account, create_date)
VALUES
('nonghoangvu04@gmail.com', '0777049058', '$2a$12$M1R9ZxIYxr5BjfJNPRz.Ououm2lx0J.1Zm90NvZrs7wRhNolCJIkq', 0, 0, 1, '2023-01-01'),
('mark@example.com', '0987654321', '$2a$12$YRvrZEPi5h9JQiFIRE8Td.M2adyJRQGhFNSpm.4ADRC0oDbb.MgT2', 0, 0, 0, '2023-02-01'),
('partners@mtpentertainment.com', '0857575707', '$2a$12$YRvrZEPi5h9JQiFIRE8Td.M2adyJRQGhFNSpm.4ADRC0oDbb.MgT2', 0, 0, 1, '2023-02-01');
-- Insert data into userRole
INSERT INTO userRole (role_id, account_id)
VALUES
(1, 1),
(2, 1),
(2, 2),
(2, 3);
-- Insert data into accountInfo
INSERT INTO accountInfo (account_id, avatar,first_name, last_name, full_name, other_name, date_of_birth, gender, education, work, current_city, hometown, relationship_id)
VALUES
(1, 'https://scontent.fhan20-1.fna.fbcdn.net/v/t39.30808-1/446732501_1658022081613061_7024694374018705809_n.jpg?stp=dst-jpg_p200x200&_nc_cat=102&ccb=1-7&_nc_sid=50d2ac&_nc_ohc=g_uMphr0BXcQ7kNvgHkIoPQ&_nc_ht=scontent.fhan20-1.fna&oh=00_AYDIPjYmR9TM3JN8c7goC3XEVR2gAWrU_Nb9iiOfmuCmVw&oe=6692DF66','Vu', 'Nong Hoang', 'Nong Hoang Vu', 'Mr Vuz', '2004-01-12', 1, 'FPT University', 'FPT Software', 'Ha Noi', 'Huu Lung Lang Son', 2),
(2, 'https://scontent.fhan2-4.fna.fbcdn.net/v/t39.30808-1/430796318_10115540567190571_8582399341104871939_n.jpg?stp=dst-jpg_p200x200&_nc_cat=1&ccb=1-7&_nc_sid=0ecb9b&_nc_ohc=I2cw9UNy1E0Q7kNvgELFfM-&_nc_ht=scontent.fhan2-4.fna&oh=00_AYBsuqzvh98o25U1ESEHNqoSd6LC_qCr0SVHjmphGtJzDw&oe=669350F3','Mark', 'Zuckerberg' , 'Mark Zuckerberg', 'Fake', '1984-05-14', 1, 'Harvard University', 'Founder and CEO at Meta', 'Palo Alto, California', ' Dobbs Ferry, New York', 3),
(3, 'https://scontent.fhan2-4.fna.fbcdn.net/v/t39.30808-1/444489138_1019981339484259_4504008877707254740_n.jpg?stp=dst-jpg_p200x200&_nc_cat=1&ccb=1-7&_nc_sid=f4b9fd&_nc_ohc=NQmtIzYF-E0Q7kNvgFKBu8k&_nc_ht=scontent.fhan2-4.fna&oh=00_AYApikj4BuK2pgRDH2hN-8fzd_IuGtDp3-9jkG02zJBIiQ&oe=66946E1E','Sơn Tùng', 'MTP' , 'Sơn Tùng MTP', 'Fake', '1994-07-15', 1, '	Conservatory of Ho Chi Minh City', '	
Singer song writer actor businessman', 'Thai Binh', ' Ho Chi Minh City', 1);
-- Insert data into accountDetail
INSERT INTO accountDetail (followers, following, account_id)
VALUES
(100000, 0, 1),
(14, 120, 2),
(1400000, 4, 3);
-- Insert data into listFriends
INSERT INTO listFriends(account_id, friend_info, status) VALUES (1, 3, 1), (3, 1, 1);