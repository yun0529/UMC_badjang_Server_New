package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.oauth.model.PostKakaoReq;
import com.example.demo.src.user.model.PostUserRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@RestController
@RequestMapping("/oauth")
public class OAuthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OAuthService OAuthService;

    public OAuthController(OAuthService OAuthService){
        this.OAuthService = OAuthService;
    }

    /**
     * 카카오 소셜 로그인 api (CALLBACK)
     * [POST] /oauth/kakao
     *
     * @return BaseResponse<PostUserRes>
     */

    @PostMapping("/kakao")
    @Transactional
    @ResponseBody
    public BaseResponse<PostUserRes> kakaoLogin(@RequestBody PostKakaoReq postKakaoReq) {
        try{
            PostUserRes postUserRes = OAuthService.saveUser(postKakaoReq.getAccess_token());

            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
