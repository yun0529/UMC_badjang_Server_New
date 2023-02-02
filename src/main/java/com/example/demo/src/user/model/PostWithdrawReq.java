package com.example.demo.src.user.model;

import lombok.Data;

@Data
public class PostWithdrawReq {
    private int user_idx;
    private String text;
}
