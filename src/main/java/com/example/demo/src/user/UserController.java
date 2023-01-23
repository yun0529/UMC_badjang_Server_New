package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.POST_USERS_EMPTY_PW;
import static com.example.demo.utils.ValidationRegex.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        //이메일 정규표현: 입력받은 이메일이 email@domain.xxx와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        if (postLoginReq.getUser_email() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if (postLoginReq.getUser_password() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PW);
        }
        if (!isRegexEmail(postLoginReq.getUser_email())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 이메일조회 API
     * [GET] /users/:user_phone
     *     아이디찾기 전에 토큰을 받아서 데이터베이스 저장한다음 아이디 찾은후 데이터베이스내의 토큰 제거
     * 전화번호를 입력하면 해당device의 토큰을 날려주는게 필요함
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{user_phone}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("user_phone") String user_phone) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            GetUserRes getUserRes = userProvider.getUser(user_phone);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 비밀번호변경 API
     * [PATCH] /users/:user_email
     */
    /*@ResponseBody
    @PatchMapping("/{user_email}")
    public BaseResponse<String> modifyUserPassword(@PathVariable("user_email") String user_email, @RequestBody User user) {
        *//*if(!isRegexEmail(user_email)){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }*//*
        if (user.getUser_password() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PW);
        }
        if(!isRegexPw(user.getUser_password())){
            return new BaseResponse<>(POST_USERS_INVALID_PW);
        }
        if (!isRegexEmail(user_email)) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        try {
            *//**
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUser_idx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경**//*
            PatchUserReq patchUserReq = new PatchUserReq(user_email, user.getUser_password());
            userService.modifyUserPassword(patchUserReq);

            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/




}
