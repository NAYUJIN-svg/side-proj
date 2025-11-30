package com.nyg.sideproj.login.controller;

import com.nyg.sideproj.login.dto.SignupRequestDTO;
import com.nyg.sideproj.login.model.Users;
import com.nyg.sideproj.login.service.UsersService;
import com.nyg.sideproj.login.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users") // 웹 UI 전용 경로
public class UsersController {

    private final UsersService userService;

    public UsersController(UsersService userService) {
        this.userService = userService;
    }

    // =======================
    // 회원가입 페이지 보여주기
    // =======================
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupRequestDTO", new SignupRequestDTO());
        return "Users/signup"; // templates/Users/signup.html
    }

    // =======================
    // 회원가입 처리
    // =======================
    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute SignupRequestDTO signupRequestDTO, Model model) {
        int result = userService.signup(signupRequestDTO);

        if (result > 0) {
            // 회원가입 성공 시 로그인 페이지로 리다이렉트
            return "redirect:/users/login";
        } else {
            // 실패 시 다시 회원가입 페이지로 이동 + 에러 메시지 표시
            model.addAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
            return "users/signup";
        }
    }

    // =======================
    // 로그인 페이지 보여주기
    // =======================
    @GetMapping("/login")
    public String loginPage() {
        return "users/login"; // templates/Users/login.html
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              Model model) {
        boolean success = userService.login(email, password); // 서비스에서 로그인 체크
        if (success) {
            return "redirect:/"; // 로그인 성공 시 메인 페이지
        } else {
            model.addAttribute("error", "로그인 실패: 이메일 또는 비밀번호가 틀립니다.");
            return "users/login"; // 로그인 페이지로 다시
        }
    }

}
