package com.nyg.sideproj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * KAMCO DB Entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KamcoItem {
    
    // 공통 컬럼
    private Long pbctNo;           // 공매번호
    private Long minBidPrc;        // 최저입찰가
    
    // kamco_by_mnmt_no 테이블 전용 컬럼
    private String cltrMnmtNo;     // 물건관리번호
    private Long apslAsesAvgAmt;   // 감정평가금액
    private String pbctBegnDtm;    // 입찰시작일시
    
    // kamco_by_cltr_nm 테이블 전용 컬럼
    private String cltrNm;         // 물건명
    private String pbctClsDtm;     // 입찰마감일시
    private Integer uscbdCnt;      // 유찰횟수
}