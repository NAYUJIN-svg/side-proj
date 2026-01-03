-- KAMCO 데이터베이스 스키마

-- 기존 테이블 삭제
DROP TABLE IF EXISTS kamco_by_mnmt_no;
DROP TABLE IF EXISTS kamco_by_cltr_nm;
DROP TABLE IF EXISTS kamco_history;

-- 이력조회 테이블
CREATE TABLE kamco_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pbct_no BIGINT NOT NULL COMMENT '공매번호',
    cltr_mnmt_no VARCHAR(50) NOT NULL COMMENT '물건관리번호',
    pbct_cltr_stat_nm VARCHAR(100) COMMENT '공매물건상태명',
    min_bid_prc BIGINT COMMENT '최저입찰가',
    fee_rate VARCHAR(20) COMMENT '수수료율',
    pbct_begn_dtm VARCHAR(14) COMMENT '입찰시작일시',
    pbct_cls_dtm VARCHAR(14) COMMENT '입찰마감일시',
    uscbd_cnt INT COMMENT '유찰횟수',
    iqry_cnt INT COMMENT '조회수',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    INDEX idx_pbct_no (pbct_no),
    INDEX idx_cltr_mnmt_no (cltr_mnmt_no),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 물건명 테이블
CREATE TABLE kamco_by_cltr_nm (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cltr_nm VARCHAR(1000) COMMENT '물건명',
    pbct_no BIGINT COMMENT '공매번호',
    pbct_cls_dtm VARCHAR(14) COMMENT '입찰마감일시',
    min_bid_prc BIGINT COMMENT '최저입찰가',
    uscbd_cnt INT COMMENT '유찰횟수',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
