package com.example.demo.src.bookmark.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수를 받는 생성자를 생성

public class GetBookmarkScholarshipRes {
    private Long bookmark_idx;
    private Long scholarship_idx;
    private String scholarship_name;
    private String scholarship_institution;
    private String scholarship_content;
    private String scholarship_image;
    private String scholarship_homepage;
    private int scholarship_view;
    private int scholarship_comment;
    private String scholarship_scale;
    private String scholarship_term;
    private String scholarship_presentation;

}
