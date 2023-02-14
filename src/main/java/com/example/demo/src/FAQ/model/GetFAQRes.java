package com.example.demo.src.FAQ.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFAQRes {
    private Long FAQ_idx;
    private String FAQ_title;
    private String FAQ_content;
    private String FAQ_img;
    private String FAQ_createAt;
    private String FAQ_updateAt;
    private String FAQ_status;
}