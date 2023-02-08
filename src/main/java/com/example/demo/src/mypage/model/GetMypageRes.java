package com.example.demo.src.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
public class GetMypageRes {
    private int user_idx;
    private String user_email;
    private String user_name;
    private String user_profileimage_url;
    private String user_type;
    private String user_birth;
    private String user_phone;
    private String user_push_yn;
    private String user_on_off;
    private String user_univ;
    private String user_college;
    private String user_department;
    private String user_grade;
    private String user_semester;
    private String user_province;
    private String user_city;
}
