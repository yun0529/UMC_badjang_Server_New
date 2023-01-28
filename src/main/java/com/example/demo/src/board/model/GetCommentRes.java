package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentRes {
    private int comment_idx;
    private int user_idx;
    private int post_idx;
    private String comment_content;
    private String comment_recommend;
    private String comment_anonymity;
    private String comment_createAt;
    private String comment_updatedAt;
    private String comment_status;

    public GetCommentRes(int comment_idx, String comment_content, String comment_updatedAt) {
        this.comment_idx = comment_idx;
        this.comment_content = comment_content;
        this.comment_updatedAt = comment_updatedAt;
    }
}
