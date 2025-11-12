-- 기존 테이블 삭제
DROP TABLE IF EXISTS kamco_by_mnmt_no;
DROP TABLE IF EXISTS kamco_by_cltr_nm;

-- kamco_by_mnmt_no 테이블 생성
CREATE TABLE kamco_by_mnmt_no (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    CLTR_MNMT_NO VARCHAR(50) COMMENT '물건관리번호',
    PBCT_NO BIGINT COMMENT '공매번호',
    MIN_BID_PRC BIGINT COMMENT '최저입찰가',
    APSL_ASES_AVG_AMT BIGINT COMMENT '감정평가금액',
    PBCT_BEGN_DTM VARCHAR(14) COMMENT '입찰시작일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- kamco_by_cltr_nm 테이블 생성
CREATE TABLE kamco_by_cltr_nm (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    CLTR_NM VARCHAR(1000) COMMENT '물건명',
    PBCT_NO BIGINT COMMENT '공매번호',
    PBCT_CLS_DTM VARCHAR(14) COMMENT '입찰마감일시',
    MIN_BID_PRC BIGINT COMMENT '최저입찰가',
    USCBD_CNT INT COMMENT '유찰횟수'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 구조 확인
DESCRIBE kamco_by_mnmt_no;
DESCRIBE kamco_by_cltr_nm;
