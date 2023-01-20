package com.example.demo.src.scholarship;

import com.example.demo.config.BaseException;
import com.example.demo.src.scholarship.model.PostScholarshipReq;
import com.example.demo.src.scholarship.model.PostScholarshipRes;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.INCREASE_FAIL_SCHOLARSHIP_VIEW;

/**
 * Service란?
 * Controller에 의해 호출되어 실제 비즈니스 로직과 트랜잭션을 처리: Create, Update, Delete 의 로직 처리
 * 요청한 작업을 처리하는 관정을 하나의 작업으로 묶음
 * dao를 호출하여 DB CRUD를 처리 후 Controller로 반환
 */
@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결
public class ScholarshipService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final ScholarshipDao scholarshipDao;
    private final ScholarshipProvider scholarshipProvider;

    //private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    @Autowired //readme 참고
    public ScholarshipService(ScholarshipDao scholarshipDao, ScholarshipProvider scholarshipProvider) {
        this.scholarshipDao = scholarshipDao;
        this.scholarshipProvider = scholarshipProvider;
        //this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    }

    // ******************************************************************************


    // 장학금 조회수1증가 수정(Patch)
    public void increaseScholarshipView(long scholarshipIdx) throws BaseException {
        try {
            int result = scholarshipDao.increaseScholarshipView(scholarshipIdx); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(INCREASE_FAIL_SCHOLARSHIP_VIEW);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 장학금 추가(Post)
    public PostScholarshipRes createScholarship(PostScholarshipReq postScholarshipReq) throws BaseException {
        try {
            long scholarship_idx = scholarshipDao.createScholarship(postScholarshipReq);
            return new PostScholarshipRes(scholarship_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
