package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostBoardRes {
    private int post_idx;
    private String post_category;
    private int user_idx;
    private String post_content;
    private String post_createAt;
}
