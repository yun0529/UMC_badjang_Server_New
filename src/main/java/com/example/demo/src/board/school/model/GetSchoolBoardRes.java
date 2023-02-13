package com.example.demo.src.board.school.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor

public class GetSchoolBoardRes {
    private int post_idx;
    private int user_idx;
    private String post_name;
    private String post_content;
    private String post_image;
    private int post_view;
    private int post_recommend;
    private int post_comment;
    private String post_anonymity;
    private String post_category;
    private String post_school_name;
    private String post_createAt;
    private int recommend_check;
}
