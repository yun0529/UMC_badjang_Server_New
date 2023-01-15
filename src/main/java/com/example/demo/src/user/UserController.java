package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

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
     * 모든 회원 조회 API
     * [GET] /users
     *
     * 또는
     *
     * 해당 이메일의 유저 정보 조회
     * [GET] /users?email=
     *
     * @return BaseResponse<List<GetUserRes>>
     */
    @ResponseBody
    @Transactional
    @GetMapping("")
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false)String email) {
        try{
            if (email == null) {
                List<GetUserRes> getUserRes = userProvider.getUsers();
                return new BaseResponse<>(getUserRes);
            }
            List<GetUserRes> getUserRes = userProvider.getUsersByEmail(email);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     *
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @Transactional
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        if(postUserReq.getUser_email() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(!isRegexEmail(postUserReq.getUser_email())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        if(postUserReq.getUser_password() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PW);
        }
        if(!isRegexPw(postUserReq.getUser_password())){
            return new BaseResponse<>(POST_USERS_INVALID_PW);
        }
        if(postUserReq.getUser_name() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        if(postUserReq.getUser_name().length() < 2 || postUserReq.getUser_name().length() > 20){
            return new BaseResponse<>(POST_USERS_INVALID_NAME);
        }
        if(postUserReq.getUser_birth() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_BIRTH);
        }
        if(!isRegexBirth(postUserReq.getUser_birth())){
            return new BaseResponse<>(POST_USERS_INVALID_BIRTH);
        }
        if(postUserReq.getUser_phone() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        if(!isRegexPhone(postUserReq.getUser_phone())){
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
