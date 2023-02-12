package com.example.demo.src.board.school;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.school.model.GetOneOfSchoolBoardRes;
import com.example.demo.src.board.school.model.GetSchoolBoardCommentRes;
import com.example.demo.src.board.school.model.GetSchoolBoardDetailRes;
import com.example.demo.src.board.school.model.GetSchoolBoardRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class SchoolBoardProvider {


    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SchoolBoardDao schoolBoardDao;
    private final SchoolBoardCommentDao schoolBoardCommentDao;

    public SchoolBoardProvider(SchoolBoardDao schoolBoardDao, SchoolBoardCommentDao schoolBoardCommentDao) {
        this.schoolBoardDao = schoolBoardDao;
        this.schoolBoardCommentDao = schoolBoardCommentDao;
    }


    public List<GetSchoolBoardRes> getSchoolBoard(int userIdx, int schoolNameIdx) throws BaseException {
        try {
            List<GetSchoolBoardRes> getSchoolBoardRes = schoolBoardDao.getSchoolBoard(userIdx, schoolNameIdx);

            return getSchoolBoardRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public GetSchoolBoardDetailRes getSchoolBoardDetail(int userIdx, int postIdx) throws BaseException {

        try {
            schoolBoardDao.updateView(postIdx);
            List<GetOneOfSchoolBoardRes> getOneOfSchoolBoardRes = schoolBoardDao.getOneOfSchoolBoardRes(userIdx, postIdx);
            List<GetSchoolBoardCommentRes> getSchoolBoardCommentRes = schoolBoardCommentDao.getSchoolBoardComment(userIdx, postIdx);

            GetSchoolBoardDetailRes getSchoolBoardDetailRes = new GetSchoolBoardDetailRes(getOneOfSchoolBoardRes, getSchoolBoardCommentRes);

            return getSchoolBoardDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




}