package com.example.demo.src.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Bookmark {
    private int bookmark_idx;
    private int post_idx;
    private int user_idx;
    private int scholarship_idx;
    private int support_idx;
}
