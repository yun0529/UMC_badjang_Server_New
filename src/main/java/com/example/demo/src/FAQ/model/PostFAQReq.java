package com.example.demo.src.FAQ.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostFAQReq {
    private String FAQ_title;
    private String FAQ_content;
    private String FAQ_image;
}