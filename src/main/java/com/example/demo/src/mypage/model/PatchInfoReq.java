package com.example.demo.src.mypage.model;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchInfoReq {
    private String user_univ;
    private String user_college;
    private String user_department;
    private int user_grade;
    private int user_semester;
    private String user_province;
    private String user_city;
}
