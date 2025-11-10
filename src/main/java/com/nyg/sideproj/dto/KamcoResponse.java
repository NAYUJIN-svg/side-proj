package com.nyg.sideproj.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
@RequiredArgsConstructor
public class KamcoResponse {
    @Schema(description = "결과 코드", example = "00")
    private String resultCode;

    @Schema(description = "결과 메시지", example = "NORMAL SERVICE.")
    private String resultMsg;

    @Schema(description = "조회 데이터 리스트")
    private List<KamcoItem> items;

    @Data
    public static class KamcoItem {
        @Schema(description = "공고번호", example = "394537")
        private Long plnmNo;

        @Schema(description = "공매번호", example = "438449")
        private Long pbctNo;

        @Schema(description = "공매조건번호", example = "1500249")
        private Long pbctCdtnNo;

        @Schema(description = "물건번호", example = "498127")
        private Long cltrNo;

        @Schema(description = "물건이력번호", example = "2060241")
        private Long cltrHstrNo;

        @Schema(description = "화면그룹코드", example = "0003")
        private String scrnGrpCd;

        @Schema(description = "용도명", example = "토지 / 대지")
        private String ctgrFullNm;

        @Schema(description = "입찰번호", example = "097")
        private String bidMnmtNo;

        @Schema(description = "물건명", example = "서울 성동구 도선동 49")
        private String cltrNm;

        @Schema(description = "물건관리번호", example = "2009-00042-001")
        private String cltrMnmtNo;

        @Schema(description = "입찰시작일시", example = "20160524100000")
        private String pbctBegnDtm;

        @Schema(description = "입찰마감일시", example = "20160526170000")
        private String pbctClsDtm;

        @Schema(description = "최저입찰가", example = "357724000")
        private Long minBidPrc;

        @Schema(description = "감정가", example = "439192000")
        private Long apslAsesAvgAmt;

        @Schema(description = "물건상세정보", example = "대지 213.2 ㎡")
        private String goodsNm;
    }
    }
