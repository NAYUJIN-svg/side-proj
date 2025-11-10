package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.KamcoRequest;
import com.nyg.sideproj.dto.KamcoResponse;
import com.nyg.sideproj.service.KamcoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kamco")
public class KamcoRestController {


    private final KamcoService kamcoService;


    @GetMapping("/raw")
    @Operation(summary = "원본 데이터 조회", description = "public_data_raw 테이블에서 데이터 조회")
    public ResponseEntity<KamcoResponse> getRawData(@Valid KamcoRequest request) {
        KamcoResponse response = kamcoService.getRawData(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mnmt")
    @Operation(summary = "물건관리번호 기준 데이터 조회", description = "중복 제거 후 상위 데이터 조회")
    public ResponseEntity<KamcoResponse> getByMnmt(@Valid KamcoRequest request) {
        KamcoResponse response = kamcoService.getByMnmtNo(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cltr")
    @Operation(summary = "물건명 기준 데이터 조회", description = "중복 제거 후 상위 데이터 조회")
    public ResponseEntity<KamcoResponse> getByCltr(@Valid KamcoRequest request) {
        KamcoResponse response = kamcoService.getByCltrNm(request);
        return ResponseEntity.ok(response);
    }





}
