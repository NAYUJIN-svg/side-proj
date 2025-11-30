package com.nyg.sideproj.login.mapper;

import com.nyg.sideproj.login.dto.SignupRequestDTO;
import com.nyg.sideproj.login.model.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 이메일로 사용자 조회
    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results({
            @Result(property = "birthDate", column = "birth_date"),
            @Result(property = "profileImg", column = "profile_img")
    })
    Users findByEmail(String email);

    // 회원가입
    @Insert("INSERT INTO users (email, password, name, phone, birth_date, profile_img) " +
            "VALUES (#{email}, #{password}, #{name}, #{phone}, #{birthDate}, #{profileImg})")
    int signup(SignupRequestDTO dto);
}
