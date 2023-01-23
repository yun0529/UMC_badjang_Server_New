package com.example.demo.src.user1;


import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Service Create, Update, Delete 의 로직 처리
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

    /*// 회원정보 수정(Patch)
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
    }*/


}