package com.example.demo.src.scholarship;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.scholarship.model.GetScholarshipRes;
import com.example.demo.src.scholarship.model.PostScholarshipReq;
import com.example.demo.src.scholarship.model.PostScholarshipRes;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentRes;
import com.example.demo.src.user.model.PostUserRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/scholarships")

public class ScholarshipController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final ScholarshipProvider scholarshipProvider;

    @Autowired
    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipProvider scholarshipProvider, ScholarshipService scholarshipService) {
        this.scholarshipProvider = scholarshipProvider;
        this.scholarshipService = scholarshipService;
    }


    /**
     * 필터에 맞는 장학금들 조회 API
     * [GET] /scholarships?category=&filter=&order=
     */
    @ResponseBody   // return되는 자바 객체를 JSON으로 바꿔서 HTTP body에 담는 어노테이션.
    //  JSON은 HTTP 통신 시, 데이터를 주고받을 때 많이 쓰이는 데이터 포맷.
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<List<GetScholarshipRes>> getScholarships(@RequestParam(required = false)Integer category, @RequestParam(required = false)Integer filter, @RequestParam(required = false)Integer order){
        //  @RequestParam은, 1개의 HTTP Request 파라미터를 받을 수 있는 어노테이션(?뒤의 값). default로 RequestParam은 반드시 값이 존재해야 하도록 설정되어 있지만, (전송 안되면 400 Error 유발)
        //  지금 예시와 같이 required 설정으로 필수 값에서 제외 시킬 수 있음
        //  defaultValue를 통해, 기본값(파라미터가 없는 경우, 해당 파라미터의 기본값 설정)을 지정할 수 있음

        //  카테고리 null : 전체
        //	           1 : 교내 재학생 장학금
        //	           2 : 교내 신입생 입학성적 우수장학금
        //  필터 	null : 인기순(조회순)
        //	           1 : 생성시간
        //	           2 : 인기순(조회순)
        //	           3 : 댓글순
        //  정렬	    null : desc
        //	           1 : asc

        try {
            List<GetScholarshipRes> getScholarshipsRes = scholarshipProvider.getScholarshipsByFilter(category, filter, order);
            return new BaseResponse<>(getScholarshipsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


     /**
     * 장학금 1개 조회 API
     * [GET] /scholarships/:scholarshipIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{scholarshipIdx}") // (GET) 127.0.0.1:9000/scholarship/:scholarshipIdx
    public BaseResponse<GetScholarshipRes> getScholarship(@PathVariable("scholarshipIdx") long scholarshipIdx) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 scholarshipId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            if (scholarshipProvider.checkScholarshipIdx(scholarshipIdx) == 0) {
                throw new BaseException(SCHOLARSHIP_EMPTY_SCHOLARSHIP_IDX);
            }
            scholarshipService.increaseScholarshipView(scholarshipIdx);
            GetScholarshipRes getScholarshipRes = scholarshipProvider.getScholarship(scholarshipIdx);
            return new BaseResponse<>(getScholarshipRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 장학금 추가 API
     * [POST] /scholarships/new-scholarship
     */
    @ResponseBody
    @PostMapping("/new-scholarship")
    public BaseResponse<PostScholarshipRes> createScholarship(@RequestBody PostScholarshipReq postScholarshipReq) {
        //장학금 이름을 입력하지 않으면 에러메시지
        if (postScholarshipReq.getScholarship_name() == null) {
            return new BaseResponse<>(POST_SCHOLARSHIP_EMPTY_NAME);
        }
        try {
            PostScholarshipRes postScholarshipRes = scholarshipService.createScholarship(postScholarshipReq);
            return new BaseResponse<>(postScholarshipRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}

