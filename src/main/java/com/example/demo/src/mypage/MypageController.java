package com.example.demo.src.mypage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.mypage.model.GetMypageRes;
import com.example.demo.src.mypage.model.PatchUserReq;
import com.example.demo.src.search.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/mypage")
public class MypageController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MypageProvider mypageProvider;

    @Autowired
    private final MypageService mypageService;

    private final JwtService jwtService;

    public MypageController(MypageProvider mypageProvider, MypageService mypageService, JwtService jwtService) {
        this.mypageProvider = mypageProvider;
        this.mypageService = mypageService;
        this.jwtService = jwtService;
    }

    /**
     * 마이페이지 프로필조회 API
     * [GET] /search
     */
    @ResponseBody
    @GetMapping
    public BaseResponse<GetMypageRes> getMypage() {
        try {
            int user_idx = jwtService.getUserIdx();
            GetMypageRes getMypageRes = mypageProvider.getMypage(user_idx);
            return new BaseResponse<>(getMypageRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 프로필변경 API
     * [PATCH] /profile change
     */
    @ResponseBody
    @PatchMapping("/profile change")
    public BaseResponse<String> modifyUserProfile(@RequestBody PatchUserReq patchUserReq) {
        try {//토큰을가져오는 것은 jwtservice클래스에 들어있다.
            int user_idx = jwtService.getUserIdx(); //권한인증이된 유저의 토큰에서 userdidx를 얻어서 해당 유저 정보를 조회한다.
            mypageService.modifyUserProfile(user_idx, patchUserReq);

            String result = "프로필정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}