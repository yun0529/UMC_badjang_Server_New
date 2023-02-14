package com.example.demo.src.inquiry;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.GetInquiryRes;
import com.example.demo.src.popularBoard.PopularBoardDao;
import com.example.demo.src.popularBoard.model.GetPopularRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class InquiryProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final InquiryDao inquiryDao;


    @Autowired
    public InquiryProvider(InquiryDao inquiryDao) {
        this.inquiryDao = inquiryDao;
    }

    /**
     * 문의 목록 전체 조회*/
    public List<GetInquiryRes> getInquiries() throws BaseException {
        try{
            List<GetInquiryRes> getInquiryRes = inquiryDao.getInquiries();
            return getInquiryRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**idx로 문의 하나 조회*/
    public GetInquiryRes getInquiryByIdx(Integer inquiryIdx) throws BaseException {
        try {
            GetInquiryRes getInquiryRes = inquiryDao.getInquiryByIdx(inquiryIdx);
            return getInquiryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
