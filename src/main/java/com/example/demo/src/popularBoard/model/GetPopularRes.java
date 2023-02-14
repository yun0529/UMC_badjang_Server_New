package com.example.demo.src.popularBoard.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPopularRes {
    private int popular_idx;
    private int post_idx;
    private int user_idx;
    private int school_name_idx;
    private String post_content;
    private String post_createAt;
    private String post_updateAt;
    private String postp_status;
    private int count;
    private String user_name;
    private String post_category;
    private String post_anonymity;
    private String user_profileimage_url;
    private String post_image;
    private int post_view;
    private int post_recommend;
    private String post_name;
    private int post_comment;
}
