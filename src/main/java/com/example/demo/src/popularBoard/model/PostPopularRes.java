package com.example.demo.src.popularBoard.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPopularRes {
    private int popular_idx;
    private int post_idx;
    private int user_idx;
    private int school_name_idx;
    private String popular_content;
    private String popular_createAt;
    private String popular_updateAt;
    private String popular_status;
}
