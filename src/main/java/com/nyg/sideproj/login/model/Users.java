package com.nyg.sideproj.login.model;

import lombok.Data;

@Data
public class Users {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String birthDate;   // DB 컬럼: birth_date
    private String profileImg;  // DB 컬럼: profile_img
}
