package com.example.demo.src.support.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSupportMyfilter {
    private String support_univ;
    private String support_college;
    private String support_department;
    private Integer support_grade;
    private Integer support_semester;
    private String support_province;
    private String support_city;
}
