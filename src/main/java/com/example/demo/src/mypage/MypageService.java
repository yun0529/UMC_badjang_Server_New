package com.example.demo.src.mypage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.mypage.model.PatchInfoReq;
import com.example.demo.src.mypage.model.PatchUserReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MypageService {

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final MypageDao mypageDao;
    private final MypageProvider mypageProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    @Autowired //readme 참고
    public MypageService(MypageDao mypageDao, MypageProvider mypageProvider, JwtService jwtService) {
        this.mypageDao = mypageDao;
        this.mypageProvider = mypageProvider;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    }

    public void modifyUserProfile(int user_idx, PatchUserReq patchUserReq) throws BaseException {
        try {
            int result = mypageDao.modifyUserProfile(user_idx, patchUserReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERPROFILE);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //학교 및 지역 정보 변경
    public void saveUserUnivInfo(int user_idx, PatchInfoReq patchInfoReq) throws BaseException {
        try {
            int result = mypageDao.saveUserUnivInfo(user_idx, patchInfoReq);
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERINFO);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}


