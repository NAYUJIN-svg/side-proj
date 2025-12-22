package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.request.KamcoRequest;
import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.service.KamcoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * KAMCO REST API 컨트롤러
 */
@Tag(name = "Kamco API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kamco")
@Validated
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class KamcoRestController {

    private final KamcoService service;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/mnmt", produces = {"application/json", "application/xml"})
    @Operation(summary = "물건관리번호 중복제거 10개")
    public KamcoResponse getMnmtList(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int numOfRows) {
        KamcoRequest request = new KamcoRequest();
        request.setPageNo(pageNo);
        request.setNumOfRows(numOfRows);
        return service.getMnmtList(request.getPageNo(), request.getNumOfRows());
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/cltr", produces = {"application/json", "application/xml"})
    @Operation(summary = "물건명 중복제거 10개")
    public KamcoResponse getCltrList(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int numOfRows) {
        KamcoRequest request = new KamcoRequest();
        request.setPageNo(pageNo);
        request.setNumOfRows(numOfRows);
        return service.getCltrList(request.getPageNo(), request.getNumOfRows());
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/backup/mnmt", produces = "application/json")
    @Operation(summary = "물건관리번호 백업 데이터 조회")
    public KamcoResponse getBackupMnmtList() {
        return service.getBackupMnmtList();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/backup/cltr", produces = "application/json")
    @Operation(summary = "물건명 백업 데이터 조회")
    public KamcoResponse getBackupCltrList() {
        return service.getBackupCltrList();
    }
}
