package com.nyg.sideproj.util;

import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.entity.KamcoItem;

/**
 * Entity를 DTO로 변환하는 Mapper
 */
public class KamcoItemMapper {
    
    /**
     * Entity를 Response DTO로 변환
     */
    public static KamcoResponse.Item toResponse(KamcoItem item) {
        KamcoResponse.Item dto = new KamcoResponse.Item();
        dto.setCLTR_MNMT_NO(item.getCltrMnmtNo());
        dto.setPBCT_NO(item.getPbctNo());
        dto.setCLTR_NM(item.getCltrNm());
        dto.setMIN_BID_PRC(item.getMinBidPrc());
        dto.setPBCT_BEGN_DTM(item.getPbctBegnDtm());
        dto.setPBCT_CLS_DTM(item.getPbctClsDtm());
        dto.setAPSL_ASES_AVG_AMT(item.getApslAsesAvgAmt());
        dto.setUSCBD_CNT(item.getUscbdCnt());
        return dto;
    }
}