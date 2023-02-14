package com.example.demo.src.notification;

import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.model.GetBookmarkAllRes;
import com.example.demo.src.bookmark.model.GetBookmarkScholarshipRes;
import com.example.demo.src.notification.model.GetNotificationAllRes;
import com.example.demo.src.notification.model.GetNotificationCommentRes;
import com.example.demo.src.notification.model.GetNotificationScholarshipRes;
import com.example.demo.src.notification.model.GetNotificationSupportRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class NotificationProvider {


    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NotificationDao notificationDao;

    public NotificationProvider(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Transactional(readOnly = true)
    public GetNotificationAllRes getNotificationAllRes(int userIdx) throws BaseException {
        try {
            List<GetNotificationScholarshipRes> getNotificationScholarshipRes = notificationDao.getNotificationScholarshipRes(userIdx);
            List<GetNotificationSupportRes> getNotificationSupportRes = notificationDao.getNotificationSupportRes(userIdx);
            List<GetNotificationCommentRes> getNotificationCommentRes = notificationDao.getNotificationCommentRes(userIdx);

            GetNotificationAllRes getNotificationAllRes = new GetNotificationAllRes(getNotificationCommentRes, getNotificationScholarshipRes, getNotificationSupportRes);

            return getNotificationAllRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




}