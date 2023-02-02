package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostRecommendReq {
    private int user_idx;
    private int post_idx;
}
