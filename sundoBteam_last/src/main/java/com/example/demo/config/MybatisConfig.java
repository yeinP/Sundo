package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.repository")
public class MybatisConfig {
    public static void main(String[] args) {
        SpringApplication.run(MybatisConfig.class, args);
    }
}
