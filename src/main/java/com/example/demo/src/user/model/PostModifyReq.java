package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostModifyReq {
    private int user_idx;
    private String user_name;
    private String user_phone;
}
