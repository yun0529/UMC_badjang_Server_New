package com.example.demo.src.bookmark.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookmarkSchoolReq {
    private int user_idx;
    private int school_idx;
}
