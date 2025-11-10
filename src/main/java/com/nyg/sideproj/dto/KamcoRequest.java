package com.nyg.sideproj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KamcoRequest {

    @Schema(description = "인증키 (URL Encode)", example = "YOUR_SERVICE_KEY", required = true)
    @NotNull
    private String serviceKey;

    @Schema(description = "페이지 번호", example = "1", required = true)
    private int pageNo;

    @Schema(description = "페이지당 데이터 수", example = "10", required = true)
    private int numOfRows;

    @Schema(description = "처분방식코드", example = "0001")
    private String dpslMtdCd;

    @Schema(description = "물건소재지(시도)", example = "강원도")
    private String sido;
}
