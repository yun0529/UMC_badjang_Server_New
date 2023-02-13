package com.example.demo.src.board.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSchoolBoardCommentRes {
    private int post_idx;
    private int comment_idx;
    private int user_idx;
    private String user_name;
    private String comment_content;
    private int comment_recommend;
    private String comment_anonymity;
    private String comment_createAt;
    private int recommend_check;
}
