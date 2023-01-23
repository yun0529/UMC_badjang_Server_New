package com.example.demo.src.support_comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.support_comment.model.GetSupportCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class SupportCommentProvider {
    private final SupportCommentDao supportCommentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SupportCommentProvider(SupportCommentDao supportCommentDao) {
        this.supportCommentDao = supportCommentDao;
    }

    /**
     * 댓글 조회 API
     */
    public List<GetSupportCommentRes> getSupportComment(Integer support_idx) throws BaseException {
        try {
            List<GetSupportCommentRes> getSupportCommentRes = supportCommentDao.getSupportComment(support_idx);
            return getSupportCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
