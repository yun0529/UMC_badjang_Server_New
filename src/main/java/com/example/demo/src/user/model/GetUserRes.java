package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String userNickname;
    private String userImageUrl;
    private String userBackgroundImageUrl;
    private int subscribeCount;
    private int videoCount;
    private String userComment;
    private String userAccount;
    private String userSubscribeAccess;
    private String userSavePlayListAccess;
    private String createdAt;
    private String totalViews;
}
