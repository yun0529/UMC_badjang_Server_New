package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostBoardReq {
    private int user_idx;
    private String post_category;
    private String post_name;
    private String post_content;
    private String post_image;
    private int post_view;
    private int post_recommend;
    private int post_comment;
    private String post_createAt;
    private String post_updateAt;
    private String post_status;
    private String post_anonymity;
}
