package com.example.demo.src.board.school.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchSchoolBoardCommentReq {
    private String comment_content;
    private String comment_anonymity;
}
