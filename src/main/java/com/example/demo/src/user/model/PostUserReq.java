package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String user_email;
    private String user_password;
    private String user_name;
    private String user_birth;
    private String user_phone;
    private String user_type;
    private String user_push_yn;
}
