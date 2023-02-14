package com.example.demo.src.user.model;


import lombok.Data;

@Data
public class PostNotiReq {
    private int user_idx;
    private String bookmark_yn;
    private String new_post_yn;
    private String inq_answer_yn;
    private String comment_yn;
}
