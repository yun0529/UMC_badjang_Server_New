package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.oauth.model.KakaoOauthToken;
import com.example.demo.src.user.model.PostUserRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/oauth")
public class OAuthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OAuthService OAuthService;

    public OAuthController(OAuthService OAuthService){
        this.OAuthService = OAuthService;
    }

    @GetMapping("/kakao")
    public BaseResponse<PostUserRes> kakaoLogin(@RequestParam("code") String code) throws BaseException {
        KakaoOauthToken kakaoOauthToken = OAuthService.getAccessToken(code);
        PostUserRes postUserRes = OAuthService.saveUser(kakaoOauthToken.getAccess_token(), kakaoOauthToken.getRefresh_token());

        return new BaseResponse<>(postUserRes);
    }
}
