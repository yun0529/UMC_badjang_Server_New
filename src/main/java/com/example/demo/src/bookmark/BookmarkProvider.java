package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.model.*;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BookmarkProvider {


    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookmarkDao bookmarkDao;

    public BookmarkProvider(BookmarkDao bookmarkDao) {
        this.bookmarkDao = bookmarkDao;
    }


    @Transactional(readOnly = true)
    public GetBookmarkAllRes getBookmarkAll(int userIdx) throws BaseException {
        try {
            List<GetBookmarkBoardRes> getBookmarkBoardRes = bookmarkDao.getBookmarkBoard(userIdx);
            List<GetBookmarkScholarshipRes> getBookmarkScholarshipRes = bookmarkDao.getBookmarkScholarship(userIdx);
            List<GetBookmarkSupportRes> getBookmarkSupportRes = bookmarkDao.getBookmarkSupport(userIdx);

            GetBookmarkAllRes getBookmarkAllRes = new GetBookmarkAllRes(getBookmarkBoardRes, getBookmarkScholarshipRes, getBookmarkSupportRes);

            return getBookmarkAllRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBookmarkBoardRes> getBookmarkBoard(int userIdx) throws BaseException {
        try {
            List<GetBookmarkBoardRes> getBookmarkBoardRes = bookmarkDao.getBookmarkBoard(userIdx);
            return getBookmarkBoardRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBookmarkScholarshipRes> getBookmarkScholarship(int userIdx) throws BaseException {
        try {
            List<GetBookmarkScholarshipRes> getBookmarkScholarshipRes = bookmarkDao.getBookmarkScholarship(userIdx);
            return getBookmarkScholarshipRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBookmarkCheckScholarshipRes getBookmarkCheckScholarship (int userIdx, int scholarshipIdx) throws BaseException {
        try {
            int nullCheck = bookmarkDao.bookmarkScholarshipNullCheck(userIdx, scholarshipIdx);
            GetBookmarkCheckScholarshipRes getBookmarkCheckScholarshipRes;
            if (nullCheck == 1) {
                getBookmarkCheckScholarshipRes = new GetBookmarkCheckScholarshipRes(scholarshipIdx, "Y");
            } else {
                getBookmarkCheckScholarshipRes = new GetBookmarkCheckScholarshipRes(scholarshipIdx, "N");
            }
            return  getBookmarkCheckScholarshipRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBookmarkSupportRes> getBookmarkSupport(int userIdx) throws BaseException {
        try {
            List<GetBookmarkSupportRes> getBookmarkSupportRes = bookmarkDao.getBookmarkSupport(userIdx);
            return getBookmarkSupportRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}