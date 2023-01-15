package com.example.demo.src.scholarship;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.scholarship.model.GetScholarshipRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/scholarship")
public class ScholarshipController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ScholarshipProvider scholarshipProvider;
    @Autowired
    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipProvider scholarshipProvider, ScholarshipService scholarshipService){
        this.scholarshipProvider = scholarshipProvider;
        this.scholarshipService = scholarshipService;
    }

    /**
     * 장학금조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetScholarshipRes>> getScholarships(@RequestParam(required = false) Long scholarship_idx) {
        try{
            if(scholarship_idx == null){
                List<GetScholarshipRes> getScholarshipRes = scholarshipProvider.getScholarships();
                return new BaseResponse<>(getScholarshipRes);
            }
            // Get Users
            List<GetScholarshipRes> getScholarshipRes = scholarshipProvider.getScholarshipByIdx(scholarship_idx);
            return new BaseResponse<>(getScholarshipRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    /**
//     * 회원가입 API
//     * [POST] /users
//     * @return BaseResponse<PostUserRes>
//     */
//    // Body
//    @ResponseBody
//    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
//    @PostMapping("")
//    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
//        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
//        if(postUserReq.getUserAccount() == null){
//            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
//        }
//        //이메일 정규표현
//        if(!isRegexEmail(postUserReq.getUserAccount())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
//        try{
//            PostUserRes postUserRes = userService.createUser(postUserReq);
//            return new BaseResponse<>(postUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 로그인 API
//     * [POST] /users/logIn
//     * @return BaseResponse<PostLoginRes>
//     */
//    @ResponseBody
//    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
//    @PostMapping("/logIn")
//    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
//        try{
//            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
//            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

}
