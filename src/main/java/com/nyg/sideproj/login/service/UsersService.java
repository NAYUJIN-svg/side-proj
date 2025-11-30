package com.nyg.sideproj.login.service;

import com.nyg.sideproj.login.dto.SignupRequestDTO;
import com.nyg.sideproj.login.dto.UsersRequest;
import com.nyg.sideproj.login.mapper.UserMapper;
import com.nyg.sideproj.login.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {

    @Autowired
    private UserMapper userMapper;

    // 회원가입
    @Transactional
    public boolean signup(SignupRequestDTO dto) {
        if (userMapper.findByEmail(dto.getEmail()) != null) {
            return false;  // 이미 존재하는 이메일
        }
        return userMapper.signup(dto) > 0;
    }

    // 로그인
    public Users login(String email, String password) {
        Users user = userMapper.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // 로그인 실패
    }

    public Users login(UsersRequest dto) {
        return null;
    }
}
