package com.nyg.sideproj.entity;

import com.nyg.sideproj.dto.response.KamcoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * KAMCO DB Entity
 * - kamco_by_mnmt_no 테이블과 kamco_by_cltr_nm 테이블의 모든 컴럼 포함
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KamcoItem {
    
    // ========== 공통 컴럼 ==========
    private Long PBCT_NO;           // 공매번호
    private Long MIN_BID_PRC;       // 최저입찰가
    
    // ========== kamco_by_mnmt_no 테이블 전용 컴럼 ==========
    private String CLTR_MNMT_NO;    // 물건관리번호
    private Long APSL_ASES_AVG_AMT; // 감정평가금액
    private String PBCT_BEGN_DTM;   // 입찰시작일시
    
    // ========== kamco_by_cltr_nm 테이블 전용 컴럼 ==========
    private String CLTR_NM;         // 물건명
    private String PBCT_CLS_DTM;    // 입찰마감일시
    private Integer USCBD_CNT;      // 유찰횟수
}
