package com.example.demo.src.scholarship;

import com.example.demo.config.BaseException;
import com.example.demo.src.scholarship.model.GetScholarshipRes;
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
/**
 * Provider란?
 * Controller에 의해 호출되어 실제 비즈니스 로직과 트랜잭션을 처리: Read의 비즈니스 로직 처리
 * 요청한 작업을 처리하는 관정을 하나의 작업으로 묶음
 * dao를 호출하여 DB CRUD를 처리 후 Controller로 반환
 */
public class ScholarshipProvider {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final ScholarshipDao scholarshipDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public ScholarshipProvider(ScholarshipDao scholarshipDao) {
        this.scholarshipDao = scholarshipDao;
    }
    // ******************************************************************************




    // 해당 filter에 맞는 장학금들의 정보 조회
    public List<GetScholarshipRes> getScholarshipsByFilter(@RequestParam(required = false)Integer category, @RequestParam(required = false)Integer filter, @RequestParam(required = false)Integer order) throws BaseException {
        try {
            List<GetScholarshipRes> getScholarshipsRes = scholarshipDao.getScholarshipsByFilter(category, filter, order);
            return getScholarshipsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    // 해당 userIdx를 갖는 User의 정보 조회
    public GetScholarshipRes getScholarship(long scholarshipIdx) throws BaseException {
        try {
            GetScholarshipRes getScholarshipRes = scholarshipDao.getScholarship(scholarshipIdx);
            return getScholarshipRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 해당 장학금 인덱스가 Scholarship Table에 존재하는지 확인
    public int checkScholarshipIdx(long scholarshipidx) throws BaseException {
        try {
            return scholarshipDao.checkScholarshipIdx(scholarshipidx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
