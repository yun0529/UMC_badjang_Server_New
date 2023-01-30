package com.example.demo.src.support;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.scholarship.model.PostScholarshipRes;
import com.example.demo.src.support.model.GetSupportRes;
import com.example.demo.src.support.model.PostSupportReq;
import com.example.demo.src.support.model.PostSupportRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/supports")

public class SupportController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final SupportProvider supportProvider;

    @Autowired
    private final SupportService supportService;

    public SupportController(SupportProvider supportProvider, SupportService supportService) {

        this.supportProvider = supportProvider;
        this.supportService = supportService;
    }

    /**
     * 필터에 맞는 지원금들 조회 API
     * [GET] /supports?category=&filter=&order=
     */
    @ResponseBody   // return되는 자바 객체를 JSON으로 바꿔서 HTTP body에 담는 어노테이션.
    //  JSON은 HTTP 통신 시, 데이터를 주고받을 때 많이 쓰이는 데이터 포맷.
    @GetMapping("")
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<List<GetSupportRes>> getSupports(@RequestParam(required = false)String category, @RequestParam(required = false)String filter, @RequestParam(required = false)String order){
        //  @RequestParam은, 1개의 HTTP Request 파라미터를 받을 수 있는 어노테이션(?뒤의 값). default로 RequestParam은 반드시 값이 존재해야 하도록 설정되어 있지만, (전송 안되면 400 Error 유발)
        //  지금 예시와 같이 required 설정으로 필수 값에서 제외 시킬 수 있음
        //  defaultValue를 통해, 기본값(파라미터가 없는 경우, 해당 파라미터의 기본값 설정)을 지정할 수 있음

        //  카테고리 null : 전체
        //  필터 	null : 인기순(조회순)
        //  정렬	    null : desc

        try {
            List<GetSupportRes> getSupportsRes = supportProvider.getSupportsByFilter(category, filter, order);
            return new BaseResponse<>(getSupportsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 지원금 1개 조회 API
     * [GET] /supports/:supportIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{supportIdx}") // (GET) 127.0.0.1:9000/supports/:supportIdx
    public BaseResponse<GetSupportRes> getSupport(@PathVariable("supportIdx") long supportIdx) {
        try {
            if (supportProvider.checkSupportIdx(supportIdx) == 0) {
                throw new BaseException(SUPPORT_EMPTY_SUPPORT_IDX);
            }
            supportService.increaseSupportView(supportIdx);
            GetSupportRes getSupportRes = supportProvider.getSupport(supportIdx);
            return new BaseResponse<>(getSupportRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 지원금 추가 API
     * [POST] /supports/new-support
     */
    @ResponseBody
    @PostMapping("/new-support")
    public BaseResponse<PostSupportRes> createSupport(@RequestBody PostSupportReq postSupportReq) {
        //장학금 이름을 입력하지 않으면 에러메시지
        if (postSupportReq.getSupport_name() == null) {
            return new BaseResponse<>(POST_SUPPORT_EMPTY_NAME);
        }
        if (postSupportReq.getSupport_policy() == null) {
            return new BaseResponse<>(POST_SUPPORT_EMPTY_POLICY);
        }
        try {
            PostSupportRes postSupportRes = supportService.createSupport(postSupportReq);
            return new BaseResponse<>(postSupportRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
