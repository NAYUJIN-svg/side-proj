package com.nyg.sideproj.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * KAMCO PBCT 이력 조회 Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KamcoHistoryRequest {

    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다")
    @Max(value = 100, message = "페이지 번호는 100 이하여야 합니다")
    private int pageNo = 1;

    @Min(value = 1, message = "조회 건수는 1 이상이어야 합니다")
    @Max(value = 100, message = "조회 건수는 100 이하여야 합니다")
    private int numOfRows = 10;

    /**
     * 물건관리번호
     * - 선택값
     * - 영숫자 + 하이픈만 허용
     */
    @Pattern(
            regexp = "^[0-9A-Za-z\\-]*$",
            message = "물건관리번호는 영숫자와 하이픈만 허용됩니다"
    )
    private String cltrMnmtNo;
}
