package com.example.demo.dto.account;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private Long uid;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String role;
    private LocalDateTime reg_date;

}
