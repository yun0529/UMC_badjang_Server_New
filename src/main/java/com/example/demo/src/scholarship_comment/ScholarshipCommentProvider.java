package com.example.demo.src.scholarship_comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.scholarship_comment.model.GetScholarshipCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ScholarshipCommentProvider {
    private final ScholarshipCommentDao scholarshipCommentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ScholarshipCommentProvider(ScholarshipCommentDao scholarshipCommentDao) {
        this.scholarshipCommentDao = scholarshipCommentDao;
    }


    /**
     * 댓글 조회 API
     */
    public List<GetScholarshipCommentRes> getScholarshipComment(Long scholarship_idx) throws BaseException {
        try {
            List<GetScholarshipCommentRes> getScholarshipCommentRes = scholarshipCommentDao.getScholarshipComment(scholarship_idx);
            return getScholarshipCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
