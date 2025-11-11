package com.nyg.sideproj.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KamcoData {
    private String mnmtNo;
    private String cltrNm;
    private String dpslMtdCd;
    private String nmrdAdrs;
    private LocalDateTime pbctBegnDtm;
    private LocalDateTime pbctEndDtm;
    private String otherInfo;
}
