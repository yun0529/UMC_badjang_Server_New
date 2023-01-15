package com.example.demo.src.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetSchoolRes {
    private int school_idx;
    private int scholarship_idx;
    private int user_idx;
    private String school_createAt;
    private String school_updateAt;
    private String school_status;
}
