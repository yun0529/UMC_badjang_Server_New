package com.example.demo.src.scholarship_comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetScholarshipCommentRes {

    private Integer scholarship_comment_idx;
    private Integer scholarship_idx;
    private Integer user_idx;
    private String user_name;
    private String user_profileimage_url;
    private String scholarship_comment_content;
    private String scholarship_comment_updateAt;

}
