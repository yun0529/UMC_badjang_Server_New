package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.model.*;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BookmarkProvider {


    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookmarkDao bookmarkDao;

    public BookmarkProvider(BookmarkDao bookmarkDao) {
        this.bookmarkDao = bookmarkDao;
    }


//    public List<GetBookmarkAllRes> getBookmarkAll(long userIdx) throws BaseException {
//        try {
//            List<GetBookmarkAllRes> getBookmarkRes = bookmarkDao.getBookmarkAll(userIdx);
//            return getBookmarkRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public List<GetBookmarkBoardRes> getBookmarkBoard(long userIdx) throws BaseException {
        try {
            List<GetBookmarkBoardRes> getBookmarkBoardRes = bookmarkDao.getBookmarkBoard(userIdx);
            return getBookmarkBoardRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBookmarkScholarshipRes> getBookmarkScholarship(long userIdx) throws BaseException {
        try {
            List<GetBookmarkScholarshipRes> getBookmarkScholarshipRes = bookmarkDao.getBookmarkScholarship(userIdx);
            return getBookmarkScholarshipRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBookmarkSupportRes> getBookmarkSupport(long userIdx) throws BaseException {
        try {
            List<GetBookmarkSupportRes> getBookmarkSupportRes = bookmarkDao.getBookmarkSupport(userIdx);
            return getBookmarkSupportRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




}