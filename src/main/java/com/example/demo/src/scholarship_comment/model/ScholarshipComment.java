package com.example.demo.src.scholarship_comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScholarshipComment {
    private Integer scholarship_comment_idx;
    private Integer scholarship_idx;
    private Integer user_idx;
    private String scholarship_comment_content;

}
