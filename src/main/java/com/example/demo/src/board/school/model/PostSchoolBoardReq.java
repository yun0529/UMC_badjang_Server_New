package com.example.demo.src.board.school.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSchoolBoardReq {
    private String post_name;
    private String post_content;
    private String post_image;
    private String post_anonymity;
}
