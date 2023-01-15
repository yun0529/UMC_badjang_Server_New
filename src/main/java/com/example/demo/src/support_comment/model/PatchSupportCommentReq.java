package com.example.demo.src.support_comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchSupportCommentReq {
    private Long support_comment_idx;
    private String support_comment_content;
}
