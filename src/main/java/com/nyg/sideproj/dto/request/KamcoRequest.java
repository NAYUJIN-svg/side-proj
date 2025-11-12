package com.nyg.sideproj.dto.request;

import lombok.Data;

/**
 * KAMCO API Request DTO
 */
@Data
public class KamcoRequest {
    private int pageNo = 1;
    private int numOfRows = 10;
}
