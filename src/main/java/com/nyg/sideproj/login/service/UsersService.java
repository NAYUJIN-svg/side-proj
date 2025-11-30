package com.nyg.sideproj.login.service;

import com.nyg.sideproj.login.dto.SignupRequestDTO;
import com.nyg.sideproj.login.mapper.UserMapper;
import com.nyg.sideproj.login.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {

    private final UserMapper userMapper;

    public UsersService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 회원가입
     *
     * @param dto 회원가입 DTO
     * @return 성공 시 1, 실패 시 0
     */
    @Transactional
    public int signup(SignupRequestDTO dto) {
        // 1. 이메일 중복 체크
        Users existingUser = userMapper.findByEmail(dto.getEmail());
        if (existingUser != null) {
            return 0; // 이미 존재하는 이메일
        }

        // 2. 회원가입 시도
        int rowsInserted = userMapper.signup(dto);

        // 3. 성공 여부 반환
        return rowsInserted; // 1이면 성공, 0이면 실패
    }

    public boolean login(String email, String password) {
        Users user = userMapper.findByEmail(email);
        if (user == null) {
            return false; // 존재하지 않는 이메일
        }

        // 실제 서비스에서는 비밀번호 해시 비교 필요
        return user.getPassword().equals(password);
    }
}

