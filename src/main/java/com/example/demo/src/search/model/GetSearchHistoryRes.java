package com.example.demo.src.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSearchHistoryRes {

    private Long search_history_idx;
    private Long user_idx;
    private String search_history_query;
}