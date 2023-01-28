package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.PostExtraReq;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostInfoReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {

        if(userProvider.checkEmail(postUserReq.getUser_email()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        LocalDate now = LocalDate.now();
        LocalDate parsedBirth = LocalDate.parse(postUserReq.getUser_birth(),
                                                DateTimeFormatter.ofPattern("yyyyMMdd"));

        int Age = now.minusYears(parsedBirth.getYear()).getYear();

        if(parsedBirth.plusYears(Age).isAfter(now)){ Age -= 1; }

        if(Age < 14){
            throw new BaseException(POST_USERS_LIMIT_BIRTH);
        }

        String pwd;
        try {
            pwd = new SHA256().encrypt(postUserReq.getUser_password());
            postUserReq.setUser_password(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int user_idx = userDao.createUser(postUserReq);
            String jwt = jwtService.createJwt(user_idx);
            return new PostUserRes(user_idx, jwt);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void saveUserUnivInfo(PostInfoReq postInfoReq) throws BaseException {
        try {
            userDao.saveUserUnivInfo(postInfoReq);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public PostUserRes saveUserExtraInfo(PostExtraReq postExtraReq) throws BaseException {

        LocalDate now = LocalDate.now();
        LocalDate parsedBirth = LocalDate.parse(postExtraReq.getUser_birth(),
                DateTimeFormatter.ofPattern("yyyyMMdd"));

        int Age = now.minusYears(parsedBirth.getYear()).getYear();

        if(parsedBirth.plusYears(Age).isAfter(now)){ Age -= 1; }

        if(Age < 14){
            throw new BaseException(POST_USERS_LIMIT_BIRTH);
        }

        try {
            userDao.saveUserExtraInfo(postExtraReq);
            int user_idx = postExtraReq.getUser_idx();
            String jwt = jwtService.createJwt(user_idx);
            return new PostUserRes(user_idx, jwt);
        } catch (Exception exception) {



    // 회원정보 수정(Patch)
    public void modifyUserPassword(PatchUserReq patchUserReq) throws BaseException {
        if(userProvider.checkEmail(patchUserReq.getUser_email()) == 0) {
            throw new BaseException(NON_EXISTENT_EMAIL);
        }
        String pwd;
        try {
            pwd = new SHA256().encrypt(patchUserReq.getUser_password());
            patchUserReq.setUser_password(pwd);
            int result = userDao.modifyUserPassword(patchUserReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERPASSWORD);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
