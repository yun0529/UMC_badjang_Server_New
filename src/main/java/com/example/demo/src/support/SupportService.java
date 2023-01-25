package com.example.demo.src.support;

import com.example.demo.config.BaseException;
import com.example.demo.src.support.model.PostSupportReq;
import com.example.demo.src.support.model.PostSupportRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.INCREASE_FAIL_SUPPORT_VIEW;


@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결
public class SupportService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final SupportDao supportDao;
    private final SupportProvider supportProvider;



    @Autowired //readme 참고
    public SupportService(SupportDao supportDao, SupportProvider supportProvider) {
        this.supportDao = supportDao;
        this.supportProvider = supportProvider;
        //this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    }

    // ******************************************************************************


    // 지원금 조회수1증가 수정(Patch)
    public void increaseSupportView(long supportIdx) throws BaseException {
        try {
            int result = supportDao.increaseSupportView(supportIdx); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(INCREASE_FAIL_SUPPORT_VIEW);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 지원금 추가(Post)
    public PostSupportRes createSupport(PostSupportReq postSupportReq) throws BaseException {
        try {
            long support_idx = supportDao.createSupport(postSupportReq);
            return new PostSupportRes(support_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}