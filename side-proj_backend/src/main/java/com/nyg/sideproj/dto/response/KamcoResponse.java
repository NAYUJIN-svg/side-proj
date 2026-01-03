package com.nyg.sideproj.dto.response;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * KAMCO Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class KamcoResponse {
    
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<Item> items;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        @XmlElement(name = "CLTR_MNMT_NO")
        private String CLTR_MNMT_NO;
        
        @XmlElement(name = "PBCT_NO")
        private Long PBCT_NO;
        
        @XmlElement(name = "CLTR_NM")
        private String CLTR_NM;
        
        @XmlElement(name = "MIN_BID_PRC")
        private Long MIN_BID_PRC;
        
        @XmlElement(name = "PBCT_BEGN_DTM")
        private String PBCT_BEGN_DTM;
        
        @XmlElement(name = "PBCT_CLS_DTM")
        private String PBCT_CLS_DTM;
        
        @XmlElement(name = "APSL_ASES_AVG_AMT")
        private Long APSL_ASES_AVG_AMT;
        
        @XmlElement(name = "USCBD_CNT")
        private Integer USCBD_CNT;
    }
}