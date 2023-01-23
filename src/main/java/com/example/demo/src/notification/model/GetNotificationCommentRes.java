package com.example.demo.src.notification.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetNotificationCommentRes {
    private int post_idx;
    private int comment_idx;
    private String post_name;
    private String comment_content;
}
