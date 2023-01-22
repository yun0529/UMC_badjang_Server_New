package com.example.demo.src.scholarship.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostScholarshipReq {
    private String scholarship_name;
    private String scholarship_institution;
    private String scholarship_content;
    private String scholarship_image;
    private String scholarship_homepage;
    private String scholarship_scale;
    private String scholarship_term;
    private String scholarship_presentation;
    private String scholarship_univ;
    private String scholarship_college;
    private String scholarship_department;
    private String scholarship_grade;
    private String scholarship_semester;
    private String scholarship_province;
    private String scholarship_city;
    private String scholarship_category;
}