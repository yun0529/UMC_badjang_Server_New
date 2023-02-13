package com.example.demo.src.FAQ;

import com.example.demo.config.BaseException;

import com.example.demo.src.FAQ.model.GetFAQRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;


//Provider : Read의 비즈니스 로직 처리
@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결

public class FAQProvider {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final FAQDao FAQDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public FAQProvider(FAQDao FAQDao) {
        this.FAQDao = FAQDao;
    }
    // ******************************************************************************


    // 자주묻는 질문 전체 조회
    public List<GetFAQRes> getFAQs() throws BaseException {
        try {
            List<GetFAQRes> getFAQsRes = FAQDao.getFAQs();
            return getFAQsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    // 해당 scholarshipIdx를 갖는 장학금의 정보 조회
    public GetFAQRes getFAQ(long FAQIdx) throws BaseException {
        try {
            GetFAQRes getFAQRes = FAQDao.getFAQ(FAQIdx);
            return getFAQRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 해당 FAQ 인덱스가 FAQ Table에 존재하는지 확인
    public int checkFAQIdx(long FAQidx) throws BaseException {
        try {
            return FAQDao.checkFAQIdx(FAQidx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

