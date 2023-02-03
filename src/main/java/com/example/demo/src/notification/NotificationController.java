package com.example.demo.src.notification;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.bookmark.BookmarkProvider;
import com.example.demo.src.bookmark.BookmarkService;
import com.example.demo.src.bookmark.model.*;
import com.example.demo.src.notification.model.GetNotificationAllRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final NotificationProvider notificationProvider;


    private final JwtService jwtService;

    public NotificationController(NotificationProvider notificationProvider, JwtService jwtService) {
        this.notificationProvider = notificationProvider;
        this.jwtService = jwtService;
    }

    /**
     * 알림
     * [GET] /notification
     */

    @ResponseBody
    @GetMapping("/notification")
    public BaseResponse<GetNotificationAllRes> getNotification() {
        try {
            int userIdx = jwtService.getUserIdx();
            GetNotificationAllRes getNotificationAllRes = notificationProvider.getNotificationAllRes(userIdx);

            return new BaseResponse<>(getNotificationAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
