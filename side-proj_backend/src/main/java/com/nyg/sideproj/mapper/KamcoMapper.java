package com.nyg.sideproj.mapper;

import com.nyg.sideproj.entity.KamcoItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * KAMCO MyBatis Mapper
 */
@Mapper
public interface KamcoMapper {

    @Select("SELECT CLTR_MNMT_NO, PBCT_NO, CLTR_NM, MIN_BID_PRC, PBCT_BEGN_DTM, PBCT_CLS_DTM, APSL_ASES_AVG_AMT, USCBD_CNT FROM kamco_items")
    List<KamcoItem> findAll();


    @Update("DROP TABLE IF EXISTS kamco_by_mnmt_no")
    void dropMnmtTable();
    
    @Update("DROP TABLE IF EXISTS kamco_by_cltr_nm")
    void dropCltrTable();
    
    @Update("CREATE TABLE kamco_by_mnmt_no (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "CLTR_MNMT_NO VARCHAR(50), " +
            "PBCT_NO BIGINT, " +
            "MIN_BID_PRC BIGINT, " +
            "APSL_ASES_AVG_AMT BIGINT, " +
            "PBCT_BEGN_DTM VARCHAR(14)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4")
    void createMnmtTable();
    
    @Update("CREATE TABLE kamco_by_cltr_nm (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "CLTR_NM VARCHAR(1000), " +
            "PBCT_NO BIGINT, " +
            "PBCT_CLS_DTM VARCHAR(14), " +
            "MIN_BID_PRC BIGINT, " +
            "USCBD_CNT INT) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4")
    void createCltrTable();
    
    /**
     * 물건관리번호 테이블에 데이터 삽입 (CLTR_NM 제외)
     */
    @Insert("INSERT INTO kamco_by_mnmt_no (CLTR_MNMT_NO, PBCT_NO, MIN_BID_PRC, APSL_ASES_AVG_AMT, PBCT_BEGN_DTM) " +
            "VALUES (#{CLTR_MNMT_NO}, #{PBCT_NO}, #{MIN_BID_PRC}, #{APSL_ASES_AVG_AMT}, #{PBCT_BEGN_DTM})")
    void insertByMnmtNo(KamcoItem item);
    
    /**
     * 물건명 테이블에 데이터 삽입 (CLTR_MNMT_NO 제외)
     */
    @Insert("INSERT INTO kamco_by_cltr_nm (CLTR_NM, PBCT_NO, PBCT_CLS_DTM, MIN_BID_PRC, USCBD_CNT) " +
            "VALUES (#{CLTR_NM}, #{PBCT_NO}, #{PBCT_CLS_DTM}, #{MIN_BID_PRC}, #{USCBD_CNT})")
    void insertByCltrNm(KamcoItem item);

    @Select("SELECT * FROM kamco_by_mnmt_no")
    List<KamcoItem> findAllMnmt();


    @Select("SELECT * FROM kamco_by_cltr_nm")
    List<KamcoItem> findAllCltr();
}
