package com.example.demo.src.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetSchoolRes {
    private int scholarship_idx;
    private int user_idx;
    private String scholarship_createAt;
    private String scholarship_updateAt;
    private String scholarship_status;
}
