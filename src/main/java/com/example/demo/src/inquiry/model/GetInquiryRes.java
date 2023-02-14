package com.example.demo.src.inquiry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetInquiryRes {
    Integer inquiry_idx;
    Integer user_idx;
    String inquiry_title;
    String inquiry_content;
    String inquiry_image;
    String inquiry_createAt;
    String inquiry_updateAt;

}
