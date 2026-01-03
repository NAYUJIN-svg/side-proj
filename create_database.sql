-- MariaDB 데이터베이스 생성 스크립트
-- 관리자 권한으로 실행

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS testdb 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 사용자 권한 부여 (필요시)
GRANT ALL PRIVILEGES ON testdb.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

-- 데이터베이스 사용
USE testdb;

-- 테이블 확인
SHOW TABLES;

-- 이력조회 테이블 확인 (생성 후)
DESCRIBE kamco_history;