package com.example.demo.src.board.school;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.school.model.GetOneOfSchoolBoardRes;
import com.example.demo.src.board.school.model.GetSchoolBoardCommentRes;
import com.example.demo.src.board.school.model.GetSchoolBoardDetailRes;
import com.example.demo.src.board.school.model.GetSchoolBoardRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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


    public List<GetSchoolBoardRes> getSchoolBoard(int schoolNameIdx) throws BaseException {
        try {
            List<GetSchoolBoardRes> getSchoolBoardRes = schoolBoardDao.getSchoolBoard(schoolNameIdx);

            return getSchoolBoardRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetSchoolBoardDetailRes getSchoolBoardDetail(int schoolNameIdx, int postIdx) throws BaseException {

        try {
            schoolBoardDao.updateView(postIdx);
            List<GetOneOfSchoolBoardRes> getOneOfSchoolBoardRes = schoolBoardDao.getOneOfSchoolBoardRes(postIdx);
            List<GetSchoolBoardCommentRes> getSchoolBoardCommentRes = schoolBoardCommentDao.getSchoolBoardComment(postIdx);

            GetSchoolBoardDetailRes getSchoolBoardDetailRes = new GetSchoolBoardDetailRes(getOneOfSchoolBoardRes, getSchoolBoardCommentRes);

            return getSchoolBoardDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




}