package com.example.demo.src.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchHistory {

    private int support_history_idx;
    private int user_idx;
    private String support_history_query;

}
