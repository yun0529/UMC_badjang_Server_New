package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class DeleteCommentReq {
    private int comment_idx;

    public DeleteCommentReq(){}
}
