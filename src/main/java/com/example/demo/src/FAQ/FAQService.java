package com.example.demo.src.FAQ;

import com.example.demo.config.BaseException;

import com.example.demo.src.FAQ.model.PostFAQReq;
import com.example.demo.src.FAQ.model.PostFAQRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;


@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결
public class FAQService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final FAQDao FAQDao;
    private final FAQProvider FAQProvider;

    //private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    @Autowired //readme 참고
    public FAQService(FAQDao FAQDao, FAQProvider FAQProvider) {
        this.FAQDao = FAQDao;
        this.FAQProvider = FAQProvider;
        //this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    }

    // ******************************************************************************


//    // 장학금 조회수1증가 수정(Patch)
//    public void increaseScholarshipView(long scholarshipIdx) throws BaseException {
//        try {
//            int result = scholarshipDao.increaseScholarshipView(scholarshipIdx); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
//            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
//                throw new BaseException(INCREASE_FAIL_SCHOLARSHIP_VIEW);
//            }
//        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    // FAQ 작성(Post)
    public PostFAQRes createFAQ(PostFAQReq postFAQReq) throws BaseException {
        try {
            long FAQ_idx = FAQDao.createFAQ(postFAQReq);
            return new PostFAQRes(FAQ_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
