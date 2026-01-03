package com.nyg.sideproj.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KamcoHistoryItem {

    private Long id;
    private String pbctNo;
    private String cltrMnmtNo;
    private String pbctCltrStatNm;
    private Long minBidPrc;
    private String feeRate;
    private LocalDateTime createdAt;
}
