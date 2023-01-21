package com.example.demo.src.support_comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SupportComment {
    private Long support_comment_idx;
    private Long support_idx;
    private Long user_idx;
    private String support_comment_content;

}
