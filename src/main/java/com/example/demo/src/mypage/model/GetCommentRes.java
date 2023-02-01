package com.example.demo.src.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class GetCommentRes {
    private int comment_idx;
    private int post_idx;
    private String post_category;
    private String comment_content;
}
