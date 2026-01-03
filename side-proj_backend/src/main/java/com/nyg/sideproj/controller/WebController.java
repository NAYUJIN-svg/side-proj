package com.nyg.sideproj.controller;

import com.nyg.sideproj.service.KamcoHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Thymeleaf 웹 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {

    private final KamcoHistoryService historyService;

    @GetMapping("/")
    public String index(Model model) {
        log.info("메인 페이지 요청");
        return "index";
    }


}