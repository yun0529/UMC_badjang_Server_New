package com.example.demo.src.support.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSupportReq {
    private String support_policy;
    private String support_name;
    private String support_institution;
    private String support_content;
    private String support_image;
    private String support_homepage;
    private String support_scale;
    private String support_term;
    private String support_presentation;
    private String support_province;
    private String support_city;
    private String support_univ;
    private String support_college;
    private String support_department;
    private String support_grade;
    private String support_semester;
    private String support_category;
}