package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.service.KamcoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * KAMCO REST API 컨트롤러
 */
@Tag(name = "Kamco API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kamco")

public class KamcoRestController {

    private final KamcoService service;

    //1. 단건 조회 (물건관리번호 테이블)
    @GetMapping(value = "/mnmt", produces = "application/xml")
    @Operation(summary = "물건관리번호 단건 조회")
    public KamcoResponse getMnmtList() {
        return service.getMnmtList();

    }

    //2. 이력 리스트 조회 (CLTR_HISTORY 테이블)
    @GetMapping(value = "/cltr-history", produces = "application/xml")
    @Operation(summary = "물건명 이력 리스트 조회")
    public List<KamcoResponse> getCltrHistory() {
        return service.getCltrHistory();
    }
}
