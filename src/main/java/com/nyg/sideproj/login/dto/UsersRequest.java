package com.nyg.sideproj.login.dto;

import lombok.Data;

@Data
public class UsersRequest {
    private int id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String birthDate;
    private String profileImg;
    private String createdAt;
    private String updatedAt;

    public String getEmail() {
        return "";
    }

    public String getPassword() {
        return "";
    }
}
