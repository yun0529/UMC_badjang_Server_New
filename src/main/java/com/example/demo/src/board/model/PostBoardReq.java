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
    private String post_anonymity;
    private String school_name_idx;
}
