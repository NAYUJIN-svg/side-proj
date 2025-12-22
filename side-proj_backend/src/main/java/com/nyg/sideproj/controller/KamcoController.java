package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.service.KamcoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kamco")
public class KamcoController {

    private final KamcoService kamcoService;

    // 물건관리번호 화면
    @GetMapping("/mnmt")
    public String mnmtList(Model model) {
        model.addAttribute("items", kamcoService.getByMnmt());
        return "kamco_mnmt"; // templates/kamco_mnmt.html
    }

    // 물건명 화면
    @GetMapping("/cltr")
    public String cltrList(Model model) {
        model.addAttribute("items", kamcoService.getByCltr());
        return "kamco_cltr"; // templates/kamco_cltr.html
    }
}
