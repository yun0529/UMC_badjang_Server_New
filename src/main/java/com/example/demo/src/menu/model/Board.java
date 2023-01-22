package com.example.demo.src.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Board {
    private int postIdx;
    private String postName;
    private String postContent;
    private String postImage;
    private int postView;
    private int postRecommend;
    private int postComment;
    private Boolean postStatus;
    private Boolean postAnonymity;
    private String postCreateAt;
    private String postUpdateAt;
}
