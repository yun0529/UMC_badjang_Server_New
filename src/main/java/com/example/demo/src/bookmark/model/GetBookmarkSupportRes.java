package com.example.demo.src.bookmark.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수를 받는 생성자를 생성

public class GetBookmarkSupportRes {
    private int bookmark_idx;
    private int support_idx;
    private String support_policy;
    private String support_name;
    private String support_institution;
    private String support_content;
    private String support_image;
    private String support_homepage;
    private int support_view;
    private int support_comment;
    private String support_scale;
    private String support_term;
    private String support_presentation;
}
