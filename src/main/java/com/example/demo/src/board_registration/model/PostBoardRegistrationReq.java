package com.example.demo.src.board_registration.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBoardRegistrationReq {
    private String requester_name;
    private String requester_univ;
    private String requester_job;
    private String requester_phone;
    private String board_name;
    private String board_purpose;
    private String board_rule;
}
