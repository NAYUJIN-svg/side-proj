package com.nyg.sideproj.login.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String birthDate;  // DB 컬럼명과 매핑
    private String profileImg;
}
