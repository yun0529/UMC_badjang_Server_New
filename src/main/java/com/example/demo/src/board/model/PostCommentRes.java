package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentRes {
    private int comment_idx;
    private int post_idx;
    private String comment_content;
    private String comment_createAt;
}
