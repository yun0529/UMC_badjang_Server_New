package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.User;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    // 로그인(password 검사)
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        User user = userDao.getPwd(postLoginReq);
        String encryptPwd;
        try {
            encryptPwd=new SHA256().encrypt(postLoginReq.getUser_password());
            // 회원가입할 때 비밀번호가 암호화되어 저장되었기 떄문에 로그인을 할때도 암호화된 값끼리 비교를 해야합니다.
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR); //비밀번호 암호화 에러
        }
        // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 의미적 validation
        /*if(user.getUser_status().equals("STOP")){
            throw new BaseException(INVALID_USER_JWT);
        }
        if(user.getUser_status().equals("REST")){
            throw new BaseException(INVALID_USER_JWT);
        }*/

        if (user.getUser_password().equals(encryptPwd)){ //비말번호가 일치한다면 userIdx를 가져온다.
            int user_idx = user.getUser_idx();
            //userDao.modifyUserStatusLogIn(user_idx);
            String jwt = jwtService.createJwt(user_idx);
            return new PostLoginRes(user_idx,jwt);
        }
        else{// 없는 이메일 또는 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    // 해당 userIdx를 갖는 User의 정보 조회
   public GetUserRes getUser(String user_phone) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(user_phone);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String user_email) throws BaseException {
        try{
            return userDao.checkEmail(user_email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

