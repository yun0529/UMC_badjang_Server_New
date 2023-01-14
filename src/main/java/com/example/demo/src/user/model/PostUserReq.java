package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String userAccount;
    private String userPw;
    private String userImageUrl;
    private String userBackgroundImageUrl;
    private String userNickname;
    private String userName;
    private String userComment;
    private String nationalityIdx;
    private String userPhoneNumber;
    private String userBirth;
    private String userGender;
    private String userSubscribeAccess;
    private String userSavePlayListAccess;
}
