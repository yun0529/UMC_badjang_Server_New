package com.example.demo.src.notification.model;


import com.example.demo.src.bookmark.model.GetBookmarkBoardRes;
import com.example.demo.src.bookmark.model.GetBookmarkScholarshipRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor

public class GetNotificationAllRes {
    private List<GetNotificationCommentRes> getNotificationCommentRes;
    private List<GetNotificationScholarshipRes> getBookmarkScholarshipRes;
    private List<GetNotificationSupportRes> getNotificationSupportRes;
}
