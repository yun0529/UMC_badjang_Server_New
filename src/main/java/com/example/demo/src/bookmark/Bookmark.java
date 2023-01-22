package com.example.demo.src.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Bookmark {
    private Long bookmark_idx;
    private Long post_idx;
    private Long user_idx;
    private Long scholarship_idx;
    private Long support_idx;
}
