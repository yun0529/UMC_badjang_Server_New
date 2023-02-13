package com.example.demo.src.menu.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetListRes {
    List<GetScholarshipRes> scholarshipList;
    List<GetSupportRes> supportList;
}
