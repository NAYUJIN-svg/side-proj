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
        dto.setCLTR_MNMT_NO(item.getCLTR_MNMT_NO());
        dto.setPBCT_NO(item.getPBCT_NO());
        dto.setCLTR_NM(item.getCLTR_NM());
        dto.setMIN_BID_PRC(item.getMIN_BID_PRC());
        dto.setPBCT_BEGN_DTM(item.getPBCT_BEGN_DTM());
        dto.setPBCT_CLS_DTM(item.getPBCT_CLS_DTM());
        dto.setAPSL_ASES_AVG_AMT(item.getAPSL_ASES_AVG_AMT());
        dto.setUSCBD_CNT(item.getUSCBD_CNT());
        return dto;
    }
}
