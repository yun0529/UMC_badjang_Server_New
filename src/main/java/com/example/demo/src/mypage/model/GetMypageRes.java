package com.example.demo.src.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
public class GetMypageRes {
    private String user_name;
    private String user_profileimage_url;
    private String bookmark_yn;
    private String new_post_yn;
    private String inq_answer_yn;
    private String comment_yn;
}
