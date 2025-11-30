package com.nyg.sideproj.login.controller;

import com.nyg.sideproj.login.dto.UsersRequest;
import com.nyg.sideproj.login.model.Users;

import com.nyg.sideproj.login.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/login")
    public String loginPage() {
        return "login/login";  // templates/login/login.html
    }

    @PostMapping("/login")
    public String login(UsersRequest dto, Model model) {
        Users loginUser = usersService.login(dto);

        if (loginUser == null) {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "login/login";  // 다시 로그인 화면
        }

        model.addAttribute("user", loginUser); // Model 대신 loginUser
        return "redirect:/";   // 로그인 성공 시 홈 화면
    }
}
