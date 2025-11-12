package com.nyg.sideproj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nyg.sideproj.mapper")
public class SideProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SideProjApplication.class, args);
    }

}
