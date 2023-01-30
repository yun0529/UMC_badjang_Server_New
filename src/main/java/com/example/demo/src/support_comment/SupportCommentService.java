package com.example.demo.src.support_comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.support_comment.model.DeleteSupportCommentReq;
import com.example.demo.src.support_comment.model.PatchSupportCommentReq;
import com.example.demo.src.support_comment.model.PostSupportCommentReq;
import com.example.demo.src.support_comment.model.PostSupportCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class SupportCommentService {
    private final SupportCommentDao supportCommentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SupportCommentService(SupportCommentDao supportCommentDao) {
        this.supportCommentDao = supportCommentDao;
    }


    /**
     * 댓글 작성 API
     */
    public PostSupportCommentRes createSupportComment(PostSupportCommentReq postSupportCommentReq) throws BaseException {
        try {
            Integer support_comment_idx = supportCommentDao.createSupportComment(postSupportCommentReq);
            return new PostSupportCommentRes(support_comment_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 댓글 수정 API
     */
    public void modifySupportComment(PatchSupportCommentReq patchSupportCommentReq) throws BaseException {
        try {
            int result = supportCommentDao.modifySupportComment(patchSupportCommentReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
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
    public void deleteSupportComment(Integer support_idx, Integer support_comment_idx) throws BaseException {
        try {
            int result = supportCommentDao.deleteSupportComment(support_idx, support_comment_idx);
            if(result == 0) {
                throw new BaseException(DELETE_COMMENT_FAIL);
            }
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
