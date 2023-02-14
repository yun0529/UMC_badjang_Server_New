package com.example.demo.src.inquiry;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.PatchInquiryReq;
import com.example.demo.src.inquiry.model.PostInquiryReq;
import com.example.demo.src.inquiry.model.PostInquiryRes;
import com.example.demo.src.scholarship_comment.ScholarshipCommentDao;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentRes;
import com.example.demo.src.support_comment.model.PatchSupportCommentReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class InquiryService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final InquiryDao inquiryDao;

    @Autowired
    public InquiryService(InquiryDao inquiryDao) {
        this.inquiryDao = inquiryDao;
    }

    /**문의 생성*/
    public PostInquiryRes createInquiry(PostInquiryReq postInquiryReq) throws BaseException{
        try {
            Integer inquiry_idx = inquiryDao.createInquiry(postInquiryReq);
            return new PostInquiryRes(inquiry_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 문의 수정 API
     */
    public void modifyInquiry(PatchInquiryReq patchInquiryReq) throws BaseException {
        try {
            int result = inquiryDao.modifyInquiry(patchInquiryReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(PATCH_INQUIRY_FAIL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 문의 삭제 API
     */
    public void deleteInquiry(Integer inquiry_idx) throws BaseException {
        try {
            int result = inquiryDao.deleteInquiry(inquiry_idx);
            if(result == 0) {
                throw new BaseException(DELETE_INQUIRY_FAIL);
            }
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
