package com.example.demo.src.bookmark.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookmarkSupportReq {
    private int user_idx;
    private int support_idx;
}
