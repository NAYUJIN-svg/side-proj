package com.nyg.sideproj.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * KAMCO API Request DTO
 */
@Data
public class KamcoRequest {
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다")
    @Max(value = 100, message = "페이지 번호는 100 이하여야 합니다")
    private int pageNo = 1;
    
    @Min(value = 1, message = "조회 건수는 1 이상이어야 합니다")
    @Max(value = 100, message = "조회 건수는 100 이하여야 합니다")
    private int numOfRows = 10;
}
