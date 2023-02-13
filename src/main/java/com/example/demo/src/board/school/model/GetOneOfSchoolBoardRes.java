package com.example.demo.src.board.school.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수를 받는 생성자를 생성

public class GetOneOfSchoolBoardRes {
    private int user_idx;
    private String user_name;
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
    private int bookmark_check;
    private int recommend_check;
}
