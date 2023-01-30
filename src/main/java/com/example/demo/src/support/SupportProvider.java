package com.example.demo.src.support;

import com.example.demo.config.BaseException;
import com.example.demo.src.support.model.GetSupportRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;


@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결

public class SupportProvider {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final SupportDao supportDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public SupportProvider(SupportDao supportDao) {
        this.supportDao = supportDao;
    }
    // ******************************************************************************




    // 해당 filter에 맞는 지원금들의 정보 조회
    public List<GetSupportRes> getSupportsByFilter(@RequestParam(required = false)String category, @RequestParam(required = false)String filter, @RequestParam(required = false)String order) throws BaseException {
        try {
            List<GetSupportRes> getSupportsRes = supportDao.getSupportsByFilter(category, filter, order);
            return getSupportsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    // 해당 supportIdx를 갖는 장학금의 정보 조회
    public GetSupportRes getSupport(long supportIdx) throws BaseException {
        try {
            GetSupportRes getSupportRes = supportDao.getSupport(supportIdx);
            return getSupportRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 해당 지원금 인덱스가 Support Table에 존재하는지 확인
    public int checkSupportIdx(long supportidx) throws BaseException {
        try {
            return supportDao.checkSupportIdx(supportidx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}