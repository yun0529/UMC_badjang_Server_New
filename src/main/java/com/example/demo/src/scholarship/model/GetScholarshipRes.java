package com.example.demo.src.scholarship.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetScholarshipRes {
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
