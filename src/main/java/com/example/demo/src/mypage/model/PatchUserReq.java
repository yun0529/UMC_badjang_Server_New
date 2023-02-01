package com.example.demo.src.mypage.model;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchUserReq {
    private String user_profileimage_url;
    private String user_name;
}
