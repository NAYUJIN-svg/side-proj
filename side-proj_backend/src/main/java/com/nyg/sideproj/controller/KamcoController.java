package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.service.KamcoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * KAMCO Thymeleaf 화면 컨트롤러
 * - Swagger UI 유지
 * - Thymeleaf 유지
 */
@Slf4j
@Tag(name = "Kamco View Controller", description = "KAMCO Thymeleaf 화면 컨트롤러")
@Controller
@RequiredArgsConstructor
@RequestMapping("/kamco")
public class KamcoController {

    private final KamcoService kamcoService;

    /**
     * 물건명(CLTR) 목록 화면
     * → DB에 저장된 백업 데이터만 조회
     */
    @GetMapping("/cltr")
    @Operation(summary = "물건명 목록 화면 (Thymeleaf)")
    public String cltrList(Model model) {

        try {
            KamcoResponse response = kamcoService.getBackupCltrList();

            model.addAttribute("response", response);
            model.addAttribute("items", response.getItems());

            return "kamco_cltr";

        } catch (Exception e) {
            log.error("물건명 목록 조회 실패", e);
            model.addAttribute("errorMessage", "물건명 데이터를 불러오지 못했습니다.");
            return "error";
        }
    }
}
