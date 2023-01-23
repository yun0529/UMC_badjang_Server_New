package com.example.demo.src.user.model;

import lombok.Data;

@Data
public class PostInfoReq {
    private int user_idx;
    private String user_univ;
    private String user_college;
    private String user_department;
    private String user_grade;
    private String user_semester;
    private String user_province;
    private String user_city;
}
