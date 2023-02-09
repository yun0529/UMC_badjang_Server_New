package com.example.demo.src.board.school;


import com.example.demo.config.BaseException;
import com.example.demo.src.board.school.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class SchoolBoardService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SchoolBoardDao schoolBoardDao;
    private final SchoolBoardCommentDao schoolBoardCommentDao;


    @Autowired
    public SchoolBoardService(SchoolBoardDao schoolBoardDao, SchoolBoardCommentDao schoolBoardCommentDao) {
        this.schoolBoardDao = schoolBoardDao;
        this.schoolBoardCommentDao = schoolBoardCommentDao;
    }


    public int postSchoolBoard(int userIdx, int schoolNameIdx, PostSchoolBoardReq postSchoolBoardReq) throws BaseException {
        try {
            schoolBoardDao.postSchoolBoard(userIdx, schoolNameIdx, postSchoolBoardReq);

            return 1;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public int patchSchoolBoard(int userIdx, int postIdx, PatchSchoolBoardReq patchSchoolBoardReq) throws BaseException {
        int checkSchoolBoardWriter = schoolBoardDao.checkSchoolBoardWriter(userIdx, postIdx);
        if (checkSchoolBoardWriter == 0) {
            throw new BaseException(SCHOOL_BOARD_AUTH_FAIL);
        }
        try {
            schoolBoardDao.patchSchoolBoard(postIdx, patchSchoolBoardReq);
            return 1;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public int deleteSchoolBoard(int userIdx, int postIdx) throws BaseException {
        int checkSchoolBoardWriter = schoolBoardDao.checkSchoolBoardWriter(userIdx, postIdx);

        if (checkSchoolBoardWriter == 0) {
            throw new BaseException(SCHOOL_BOARD_AUTH_FAIL);
        }

        try {
            schoolBoardDao.deleteSchoolBoard(userIdx, postIdx);
            return 1;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public String postSchoolBoardRecommend(int userIdx, int postIdx) throws BaseException {
        int checkSchoolBoardWriter = schoolBoardDao.checkSchoolBoardWriter(userIdx, postIdx);

        if (checkSchoolBoardWriter == 1) {
            throw new BaseException(POST_SCHOOL_BOARD_RECOMMEND_MINE);
        }
        try {
            String postSchoolBoardRecommendRes = schoolBoardDao.postSchoolBoardRecommend(userIdx, postIdx);
            schoolBoardDao.schoolBoardRecommendUpdate(postIdx);
            return postSchoolBoardRecommendRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public int postSchoolBoardComment(int userIdx, int postIdx, PostSchoolBoardCommentReq postSchoolBoardCommentReq) throws BaseException {
        try {
            schoolBoardCommentDao.postSchoolBoardComment(userIdx, postIdx, postSchoolBoardCommentReq);
            schoolBoardCommentDao.schoolBoardCommentUpdate(postIdx);
            return 1;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public int patchSchoolBoardComment(int userIdx, int commentIdx, PatchSchoolBoardCommentReq patchSchoolBoardCommentReq) throws BaseException {
        int checkSchoolBoardCommentWriter = schoolBoardCommentDao.checkSchoolBoardCommentWriter(userIdx, commentIdx);

        if (checkSchoolBoardCommentWriter == 0) {
            throw new BaseException(SCHOOL_BOARD_COMMENT_AUTH_FAIL);
        }
        try {
            schoolBoardCommentDao.patchSchoolBoardComment(commentIdx, patchSchoolBoardCommentReq);
            return 1;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public int deleteSchoolBoardComment(int userIdx, int commentIdx) throws BaseException {
        int checkSchoolBoardCommentWriter = schoolBoardCommentDao.checkSchoolBoardCommentWriter(userIdx, commentIdx);

        if (checkSchoolBoardCommentWriter == 0) {
            throw new BaseException(SCHOOL_BOARD_COMMENT_AUTH_FAIL);
        }
        try {
            schoolBoardCommentDao.deleteSchoolBoardComment(userIdx, commentIdx);
            schoolBoardCommentDao.schoolBoardCommentUpdate(commentIdx);
            return 1;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public String postSchoolBoardCommentRecommend(int userIdx, int commentIdx) throws BaseException {
        int checkSchoolBoardCommentWriter = schoolBoardCommentDao.checkSchoolBoardCommentWriter(userIdx, commentIdx);

        if (checkSchoolBoardCommentWriter == 1) {
            throw new BaseException(POST_SCHOOL_BOARD_COMMENT_RECOMMEND_MINE);
        }
        try {
            String postSchoolBoardRecommendRes = schoolBoardCommentDao.postSchoolBoardCommentRecommend(userIdx, commentIdx);
            schoolBoardCommentDao.schoolBoardCommentRecommendUpdate(commentIdx);
            return postSchoolBoardRecommendRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}



