package com.example.demo.src.scholarship_comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.scholarship_comment.model.DeleteScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PatchScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ScholarshipCommentService {
    private final ScholarshipCommentDao scholarshipCommentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ScholarshipCommentService(ScholarshipCommentDao scholarshipCommentDao) {
        this.scholarshipCommentDao = scholarshipCommentDao;
    }


    /**
     * 댓글 작성 API
     */
    public PostScholarshipCommentRes createScholarshipComment(PostScholarshipCommentReq postScholarshipCommentReq) throws BaseException {
        try {
            long scholarship_comment_idx = scholarshipCommentDao.createScholarshipComment(postScholarshipCommentReq);
            return new PostScholarshipCommentRes(scholarship_comment_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 댓글 수정 API
     */
    public void modifyScholarshipComment(PatchScholarshipCommentReq patchScholarshipCommentReq) throws BaseException {
        try {
            int result = scholarshipCommentDao.modifyScholarshipComment(patchScholarshipCommentReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(PATCH_COMMENT_FAIL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 댓글 삭제 API
     */
    public void deleteScholarshipComment(DeleteScholarshipCommentReq deleteScholarshipCommentReq) throws BaseException {
        try {
            int result = scholarshipCommentDao.deleteScholarshipComment(deleteScholarshipCommentReq);
            if(result == 0) {
                throw new BaseException(DELETE_COMMENT_FAIL);
            }
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
