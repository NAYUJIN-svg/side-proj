package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.request.KamcoHistoryRequest;
import com.nyg.sideproj.dto.response.KamcoHistoryResponse;
import com.nyg.sideproj.service.KamcoHistoryService;
import com.nyg.sideproj.service.KamcoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kamco")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
public class KamcoRestController {

    private final KamcoService kamcoService;            // CLTR
    private final KamcoHistoryService historyService;   // PBCT

    /* ===============================
       CLTR 목록 조회
    =============================== */
    @GetMapping("/cltr")
    public ResponseEntity<?> getCltr(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int numOfRows
    ) {
        return ResponseEntity.ok(
                kamcoService.getCltrList(pageNo, numOfRows)
        );
    }

    /* ===============================
       PBCT 이력 조회
    =============================== */
    @GetMapping("/history")
    public ResponseEntity<KamcoHistoryResponse> getHistory(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int numOfRows
    ) {

        KamcoHistoryRequest request = KamcoHistoryRequest.builder()
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .build();

        return ResponseEntity.ok(
                historyService.getHistoryData(request)
        );
    }

    /* ===============================
       PBCT 이력 전체 삭제 (테스트용)
    =============================== */
    @DeleteMapping("/history")
    public ResponseEntity<Void> clearHistory() {
        historyService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
