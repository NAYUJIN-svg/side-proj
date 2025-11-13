package com.nyg.sideproj.controller;

import com.nyg.sideproj.dto.response.KamcoResponse;
import com.nyg.sideproj.service.KamcoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class KamcoController {

    private final KamcoService kamcoService;

    @GetMapping("/kamco")
    public String getKamcoItems(Model model) {
        // 서비스에서 데이터 조회
        KamcoResponse response = kamcoService.getKamcoItems();

        // response 또는 items가 null이면 빈 객체/리스트로 초기화
        if (response == null) {
            response = new KamcoResponse();
        }
        if (response.getItems() == null) {
            response.setItems(java.util.Collections.emptyList());
        }

        // 모델에 담아서 Thymeleaf로 전달
        model.addAttribute("response", response);

        // templates/kamco.html 뷰 호출
        return "kamco";
    }
}
