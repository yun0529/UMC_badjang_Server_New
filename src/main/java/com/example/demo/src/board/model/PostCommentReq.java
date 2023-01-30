package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentReq {
    private int post_idx;
    private int user_idx;
    private String comment_content;
    private int comment_recommend;
    private String comment_anonymity;
    private String comment_status;
}
