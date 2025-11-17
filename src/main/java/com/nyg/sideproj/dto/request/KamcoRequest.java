package com.nyg.sideproj.dto.request;


import lombok.Data;

/**
 * KAMCO API Request DTO
 * 페이지와 조회 건수만 포함
 */
@Data
public class KamcoRequest {

    private int pageNo = 1;     //기본값 1
    private int numOfRows = 10;


}
