SHOW DATABASES;

USE testdb;





CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    birth_date DATE,
    profile_img VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 데이터 조회
SELECT * FROM users;

INSERT INTO users (email, password, name, phone, birth_date, profile_img)
VALUES
('user1@example.com', 'hashed_password1', '홍길동', '010-1234-5678', '1990-01-01', 'https://example.com/img1.jpg'),
('user2@example.com', 'hashed_password2', '김철수', '010-2345-6789', '1985-05-15', 'https://example.com/img2.jpg'),
('user3@example.com', 'hashed_password3', '이영희', NULL, '2000-12-31', NULL),
('user4@example.com', 'hashed_password4', '박민수', '010-3456-7890', NULL, 'https://example.com/img4.jpg'),
('user5@example.com', 'hashed_password5', '최지은', NULL, NULL, NULL);
INSERT INTO users (email, password, name, phone, birth_date, profile_img)
VALUES
('user6@example.com', 'hashed_password6', '정하늘', '010-4567-8901', '1992-03-22', 'https://example.com/img6.jpg'),
('user7@example.com', 'hashed_password7', '윤서진', '010-5678-9012', '1988-07-10', 'https://example.com/img7.jpg'),
('user8@example.com', 'hashed_password8', '한지민', NULL, '1995-11-05', NULL),
('user9@example.com', 'hashed_password9', '서준호', '010-6789-0123', NULL, 'https://example.com/img9.jpg'),
('user10@example.com', 'hashed_password10', '강민아', NULL, NULL, NULL);

-- 데이터 조회
SELECT * FROM users;

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 ID',
    name VARCHAR(100) NOT NULL COMMENT '사용자 이름',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT '로그인용 이메일',
    password_hash VARCHAR(255) NOT NULL COMMENT '비밀번호 해시',
    phone VARCHAR(20) DEFAULT NULL COMMENT '전화번호 (선택)',
    role ENUM('user','admin','manager') DEFAULT 'user' COMMENT '사용자 권한',
    grade INT DEFAULT 1 COMMENT '회원 등급',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '계정 생성일시'
);

INSERT INTO user (name, email, password_hash, phone, role, grade, created_at) VALUES
('Kim Minsoo', 'minsoo.kim@example.com', 'hash1', '010-1111-1111', 'user', 1, '2025-01-01 10:00:00'),
('Lee Jihyun', 'jihyun.lee@example.com', 'hash2', '010-2222-2222', 'user', 2, '2025-02-15 12:30:00'),
('Park Sungho', 'sungho.park@example.com', 'hash3', '010-3333-3333', 'admin', 3, '2025-03-20 09:45:00'),
('Choi Hyeri', 'hyeri.choi@example.com', 'hash4', '010-4444-4444', 'user', 1, '2025-04-10 14:10:00'),
('Jung Doyoung', 'doyoung.jung@example.com', 'hash5', '010-5555-5555', 'manager', 4, '2025-05-05 08:20:00'),
('Han Seojin', 'seojin.han@example.com', 'hash6', '010-6666-6666', 'user', 2, '2025-06-12 16:00:00'),
('Kang Bora', 'bora.kang@example.com', 'hash7', '010-7777-7777', 'user', 1, '2025-07-08 11:30:00'),
('Yoon Taemin', 'taemin.yoon@example.com', 'hash8', '010-8888-8888', 'admin', 5, '2025-08-21 13:15:00'),
('Seo Nari', 'nari.seo@example.com', 'hash9', '010-9999-9999', 'user', 3, '2025-09-30 17:50:00'),
('Lim Kyuhyun', 'kyuhyun.lim@example.com', 'hash10', '010-1010-1010', 'manager', 4, '2025-10-11 19:05:00');

SELECT * FROM USER;

