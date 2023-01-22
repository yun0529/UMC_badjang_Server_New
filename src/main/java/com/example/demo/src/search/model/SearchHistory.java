package com.example.demo.src.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchHistory {

    private Long support_history_idx;
    private Long user_idx;
    private String support_history_query;

}
