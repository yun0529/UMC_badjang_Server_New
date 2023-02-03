package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.*;
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

    /**
     * 학교, 지역 정보 저장 API
     * [POST] /users/info
     *
     * @return BaseResponse<String>
     */

    @ResponseBody
    @Transactional
    @PostMapping("/info")
    public BaseResponse<String> saveUserUnivInfo(@RequestBody PostInfoReq postInfoReq) {
        try {
            int user_idx_JWT = jwtService.getUserIdx();
            int user_idx = postInfoReq.getUser_idx();

            if(user_idx != user_idx_JWT) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            userService.saveUserUnivInfo(postInfoReq);
            return new BaseResponse<>("정보가 저장되었습니다.");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 소셜 로그인 추가 정보 API
     * [POST] /users/sns
     *
     * @return BaseResponse<PostUserRes>
     */

    @ResponseBody
    @Transactional
    @PostMapping("/sns")
    public BaseResponse<PostUserRes> saveUserExtraInfo(@RequestBody PostExtraReq postExtraReq) {
        if(postExtraReq.getUser_name() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        if(postExtraReq.getUser_name().length() < 2 || postExtraReq.getUser_name().length() > 20){
            return new BaseResponse<>(POST_USERS_INVALID_NAME);
        }
        if(postExtraReq.getUser_birth() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_BIRTH);
        }
        if(!isRegexBirth(postExtraReq.getUser_birth())){
            return new BaseResponse<>(POST_USERS_INVALID_BIRTH);
        }
        if(postExtraReq.getUser_phone() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        if(!isRegexPhone(postExtraReq.getUser_phone())){
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }
        try {
            int user_idx_JWT = jwtService.getUserIdx();
            int user_idx = postExtraReq.getUser_idx();

            if(user_idx != user_idx_JWT)
                return new BaseResponse<>(INVALID_USER_JWT);

            PostUserRes postUserRes = userService.saveUserExtraInfo(postExtraReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 정보 변경 API
     * [POST] /users/modify
     *
     * @return BaseResponse<String>
     */

    @ResponseBody
    @Transactional
    @PostMapping("/modify")
    public BaseResponse<String> modifyUserInfo(@RequestBody PostModifyReq postModifyReq) {
        if (postModifyReq.getUser_idx() == 0) {
            return new BaseResponse<>(USERS_EMPTY_USER_IDX);
        }
        if (postModifyReq.getUser_name() == null && postModifyReq.getUser_phone() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_INFO);
        }
        if (postModifyReq.getUser_name() != null && postModifyReq.getUser_phone() != null) {
            return new BaseResponse<>(POST_USERS_MORE_INFO);
        }
        if (postModifyReq.getUser_name() != null && (postModifyReq.getUser_name().length() < 2 || postModifyReq.getUser_name().length() > 20)) {
            return new BaseResponse<>(POST_USERS_INVALID_NAME);
        }
        if (postModifyReq.getUser_phone() != null && !(isRegexPhone(postModifyReq.getUser_phone()))) {
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }

        try {
            int user_idx_JWT = jwtService.getUserIdx();
            int user_idx = postModifyReq.getUser_idx();

            if(user_idx != user_idx_JWT)
                return new BaseResponse<>(INVALID_USER_JWT);

            String resultString = userService.modifyUserInfo(postModifyReq);
            return new BaseResponse<>(resultString);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 회원 탈퇴 API
     * [POST] /users/withdraw
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @Transactional
    @PostMapping("/withdraw")
    public BaseResponse<String> withdrawUser(@RequestBody PostWithdrawReq postWithdrawReq) {
        if (postWithdrawReq.getUser_idx() == 0)
            return new BaseResponse<>(USERS_EMPTY_USER_IDX);
        if (postWithdrawReq.getText() == null || !postWithdrawReq.getText().equals("탈퇴하기"))
            return new BaseResponse<>(POST_USERS_WRONG_TEXT);

        try {
            int user_idx_JWT = jwtService.getUserIdx();
            int user_idx = postWithdrawReq.getUser_idx();

            if(user_idx != user_idx_JWT)
                return new BaseResponse<>(INVALID_USER_JWT);

            String resultString = userService.withdrawUser(postWithdrawReq);
            return new BaseResponse<>(resultString);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그아웃 API
     * [POST] /users/logout
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @Transactional
    @PostMapping("/logout")
    public BaseResponse<String> logOut(@RequestBody PostLogoutReq postLogoutReq) {
        if(postLogoutReq.getUser_idx() == 0)
            return new BaseResponse<>(USERS_EMPTY_USER_IDX);

        try {
            int user_idx_JWT = jwtService.getUserIdx();
            int user_idx = postLogoutReq.getUser_idx();

            if(user_idx != user_idx_JWT)
                return new BaseResponse<>(INVALID_USER_JWT);

            String resultString = userService.logOut(postLogoutReq.getUser_idx());

            return new BaseResponse<>(resultString);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }


    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
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
    public BaseResponse<GetUserRes1> getUser(@PathVariable("user_phone") String user_phone) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            GetUserRes1 getUserRes = userProvider.getUser(user_phone);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 비밀번호변경 API
     * [PATCH] /users/:user_email
     */
    @ResponseBody
    @PatchMapping("/{user_email}")
    public BaseResponse<String> modifyUserPassword(@PathVariable("user_email") String user_email, @RequestBody User user) {
        /*if(!isRegexEmail(user_email)){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }*/
        if (user.getUser_password() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PW);
        }
        if(!isRegexPw(user.getUser_password())){
            return new BaseResponse<>(POST_USERS_INVALID_PW);
        }

        try {
            PatchUserReq patchUserReq = new PatchUserReq(user_email, user.getUser_password());
            userService.modifyUserPassword(patchUserReq);

            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
