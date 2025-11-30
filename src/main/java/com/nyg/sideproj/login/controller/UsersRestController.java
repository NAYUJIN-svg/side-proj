package com.nyg.sideproj.login.controller;

import com.nyg.sideproj.login.dto.SignupRequestDTO;
import com.nyg.sideproj.login.model.Users;
import com.nyg.sideproj.login.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    private UserMapper userMapper;

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDTO dto) {
        int result = userMapper.signup(dto);
        if(result > 0) {
            return "회원가입 성공";
        } else {
            return "회원가입 실패";
        }
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody SignupRequestDTO dto) {
        Users user = userMapper.findByEmail(dto.getEmail());
        if(user != null && user.getPassword().equals(dto.getPassword())) {
            return "로그인 성공";
        } else {
            return "로그인 실패";
        }
    }
}
