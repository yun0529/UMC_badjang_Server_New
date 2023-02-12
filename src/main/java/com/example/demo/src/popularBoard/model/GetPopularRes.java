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
    private String user_name;
    private int school_name_idx;
    private String popular_createAt;
    private String popular_updateAt;
    private String popular_status;
    private String board_category;
}
