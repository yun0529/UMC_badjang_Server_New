package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int user_idx;
    private String user_email;
    private String user_name;
    private String user_birth;
    private String user_phone;
    private Date user_reg;
}
