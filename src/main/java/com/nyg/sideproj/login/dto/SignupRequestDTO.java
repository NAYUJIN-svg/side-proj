package com.nyg.sideproj.login.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String birthDate; // 테스트용으로 String 사용 (LocalDate로 바꾸려면 추가 설정 필요)
    private String profileImg;
}
