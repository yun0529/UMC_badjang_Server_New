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
    private String popular_content;
    private String popular_createAt;
    private String popular_updateAt;
    private String popular_status;
    private int count;
    private String user_name;
    private String board_category;
    private String post_anonymity;
    private String user_profileimage_url;
    private String post_image;
    private String post_view;
    private String post_recommend;
    private String post_name;
    private String post_comment;
}
