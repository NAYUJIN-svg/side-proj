package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.KamcoRequest;
import com.nyg.sideproj.dto.KamcoResponse;
import com.nyg.sideproj.service.KamcoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Kamco API", description = "캠코 공공데이터 조회 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kamco")
public class KamcoRestController {

    private final KamcoService kamcoService;

    @GetMapping("/raw")
    @Operation(summary = "원본 데이터 조회", description = "public_data_raw 테이블에서 데이터 조회")
    public ResponseEntity<KamcoResponse> getRawData(
            @Parameter(description = "페이지 번호", example = "1") @RequestParam(defaultValue = "1") int pageNo,
            @Parameter(description = "페이지당 데이터 수", example = "10") @RequestParam(defaultValue = "10") int numOfRows,
            @Parameter(description = "처분방식코드", example = "0001") @RequestParam(required = false) String dpslMtdCd,
            @Parameter(description = "물건소재지(시도)", example = "강원도") @RequestParam(required = false) String sido) {
        log.info("[API /raw] Received request: pageNo={}, numOfRows={}, dpslMtdCd={}, sido={}", 
                pageNo, numOfRows, dpslMtdCd, sido);
        KamcoRequest request = KamcoRequest.builder()
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .dpslMtdCd(dpslMtdCd)
                .sido(sido)
                .build();
        return ResponseEntity.ok(kamcoService.getRawData(request));
    }

    @GetMapping("/mnmt")
    @Operation(summary = "물건관리번호 기준 데이터 조회", description = "중복 제거 후 상위 데이터 조회")
    public ResponseEntity<KamcoResponse> getByMnmt(
            @Parameter(description = "페이지 번호", example = "1") @RequestParam(defaultValue = "1") int pageNo,
            @Parameter(description = "페이지당 데이터 수", example = "10") @RequestParam(defaultValue = "10") int numOfRows,
            @Parameter(description = "처분방식코드", example = "0001") @RequestParam(required = false) String dpslMtdCd,
            @Parameter(description = "물건소재지(시도)", example = "강원도") @RequestParam(required = false) String sido) {
        log.info("[API /mnmt] Received request: pageNo={}, numOfRows={}, dpslMtdCd={}, sido={}", 
                pageNo, numOfRows, dpslMtdCd, sido);
        KamcoRequest request = KamcoRequest.builder()
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .dpslMtdCd(dpslMtdCd)
                .sido(sido)
                .build();
        return ResponseEntity.ok(kamcoService.getByMnmtNo(request));
    }

    @GetMapping("/cltr")
    @Operation(summary = "물건명 기준 데이터 조회", description = "중복 제거 후 상위 데이터 조회")
    public ResponseEntity<KamcoResponse> getByCltr(
            @Parameter(description = "페이지 번호", example = "1") @RequestParam(defaultValue = "1") int pageNo,
            @Parameter(description = "페이지당 데이터 수", example = "10") @RequestParam(defaultValue = "10") int numOfRows,
            @Parameter(description = "처분방식코드", example = "0001") @RequestParam(required = false) String dpslMtdCd,
            @Parameter(description = "물건소재지(시도)", example = "강원도") @RequestParam(required = false) String sido) {
        log.info("[API /cltr] Received request: pageNo={}, numOfRows={}, dpslMtdCd={}, sido={}", 
                pageNo, numOfRows, dpslMtdCd, sido);
        KamcoRequest request = KamcoRequest.builder()
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .dpslMtdCd(dpslMtdCd)
                .sido(sido)
                .build();
        return ResponseEntity.ok(kamcoService.getByCltrNm(request));
    }

    @GetMapping("/sync")
    @Operation(summary = "공공데이터 동기화", description = "캠코 공공데이터 API를 호출하여 DB에 저장")
    public ResponseEntity<String> syncData(
            @Parameter(description = "페이지 번호", example = "1") @RequestParam(defaultValue = "1") int pageNo,
            @Parameter(description = "페이지당 데이터 수", example = "10") @RequestParam(defaultValue = "10") int numOfRows) {
        log.info("[API /sync] Syncing data: pageNo={}, numOfRows={}", pageNo, numOfRows);
        int count = kamcoService.syncFromApi(pageNo, numOfRows);
        return ResponseEntity.ok("Synced " + count + " items");
    }

    @PostMapping("/test")
    @Operation(summary = "요청 테스트", description = "요청 데이터 확인용 POST API")
    public ResponseEntity<KamcoResponse> testPost(@RequestBody KamcoRequest request) {
        log.info("[API /test POST] Request received: {}", request);
        log.info("[API /test POST] pageNo={}, numOfRows={}, dpslMtdCd={}, sido={}", 
                request.getPageNo(), request.getNumOfRows(), request.getDpslMtdCd(), request.getSido());
        return ResponseEntity.ok(kamcoService.getRawData(request));
    }
}
