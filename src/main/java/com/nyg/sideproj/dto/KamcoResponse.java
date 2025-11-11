package com.nyg.sideproj.dto;

import com.nyg.sideproj.entity.KamcoData;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class KamcoResponse {
    @Schema(description = "결과 코드", example = "00")
    private String resultCode;

    @Schema(description = "결과 메시지", example = "NORMAL SERVICE.")
    private String resultMsg;

    @Schema(description = "조회 데이터 리스트")
    private List<KamcoData> items;
}
