package com.example.demo.src.inquiry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostInquiryReq {
    private Integer user_idx;
    private String inquiry_title;
    private String inquiry_content;
    private String inquiry_image;
}
