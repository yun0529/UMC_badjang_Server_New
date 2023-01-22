package com.example.demo.src.support_comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSupportCommentRes {
    private Integer support_comment_idx;
    private Integer support_idx;
    private Integer user_idx;
    private String support_comment_content;
    private String support_comment_updateAt;
}
