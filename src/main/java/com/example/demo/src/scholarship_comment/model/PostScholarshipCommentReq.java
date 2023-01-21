package com.example.demo.src.scholarship_comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostScholarshipCommentReq {
    private Long scholarship_idx;
    private Long user_idx;
    private String scholarship_comment_content;

}
