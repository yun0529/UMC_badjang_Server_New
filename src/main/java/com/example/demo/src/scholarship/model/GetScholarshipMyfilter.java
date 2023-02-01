package com.example.demo.src.scholarship.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetScholarshipMyfilter {
    private String scholarship_univ;
    private String scholarship_college;
    private String scholarship_department;
    private Integer scholarship_grade;
    private String scholarship_semester;
    private String scholarship_province;
    private String scholarship_city;
}
