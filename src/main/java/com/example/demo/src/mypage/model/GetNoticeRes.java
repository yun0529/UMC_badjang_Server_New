package com.example.demo.src.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class GetNoticeRes {
    private int notice_idx;
    private String notice_title;
    private String notice_content;
    private String notice_image;
    private String notice_createAt;
    private String notice_updateAt;
}

