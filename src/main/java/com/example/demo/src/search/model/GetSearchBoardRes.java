package com.example.demo.src.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSearchBoardRes {
    private int post_idx;
    private String post_name;
    private String post_content;
    private String post_image;
    private int post_view;
    private int post_recommend;
    private int post_comment;
    private String post_anonymity;
}