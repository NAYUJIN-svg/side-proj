package com.nyg.sideproj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KamcoRequest {

    @Schema(description = "페이지 번호", example = "1")
    private int pageNo;

    @Schema(description = "페이지당 데이터 수", example = "10")
    private int numOfRows;

    @Schema(description = "처분방식코드", example = "0001")
    private String dpslMtdCd;

    @Schema(description = "물건소재지(시도)", example = "강원도")
    private String sido;

    public int getOffset() {
        return (pageNo - 1) * numOfRows;
    }
}
