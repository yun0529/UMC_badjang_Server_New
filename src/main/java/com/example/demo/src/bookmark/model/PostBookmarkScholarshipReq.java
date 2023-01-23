package com.example.demo.src.bookmark.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookmarkScholarshipReq {
    private int user_idx;
    private int scholarship_idx;
}
