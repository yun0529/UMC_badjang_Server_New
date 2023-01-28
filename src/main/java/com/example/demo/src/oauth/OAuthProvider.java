package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.UserDao;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class OAuthProvider {

    private final OAuthDao oAuthDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OAuthProvider(OAuthDao oAuthDao, JwtService jwtService) {
        this.oAuthDao = oAuthDao;
        this.jwtService = jwtService;
    }


    public int checkEmail(String user_email) throws BaseException {
        try{
            return oAuthDao.checkEmail(user_email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkSavedUser(String user_email) throws BaseException {
        try {
            return oAuthDao.checkSavedUser(user_email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
