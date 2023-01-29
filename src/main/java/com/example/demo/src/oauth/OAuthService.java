package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.src.oauth.model.KakaoOauthToken;
import com.example.demo.src.oauth.model.KakaoProfile;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.secret.Secret.KAKAO_API_KEY;

@Service
public class OAuthService {

    private final OAuthDao oAuthDao;
    private final JwtService jwtService;
    private final OAuthProvider oAuthProvider;

    @Autowired
    public OAuthService(OAuthDao oAuthDao, JwtService jwtService, OAuthProvider oAuthProvider) {
        this.oAuthDao = oAuthDao;
        this.jwtService = jwtService;
        this.oAuthProvider = oAuthProvider;
    }


//    public KakaoOauthToken getAccessToken(String code) throws BaseException {
//
//        RestTemplate rt = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", KAKAO_API_KEY);
//        params.add("redirect_uri", "http://localhost:9000/oauth/kakao");
//        params.add("code", code);
//
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//
//        ResponseEntity<String> accessTokenResponse = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoOauthToken kakaoOauthToken = null;
//        try {
//            kakaoOauthToken = objectMapper.readValue(accessTokenResponse.getBody(), KakaoOauthToken.class);
//        } catch (Exception exception) {
//            throw new BaseException(KAKAO_CONNECTION_ERROR);
//        }
//
//        return kakaoOauthToken;
//    }

    public PostUserRes saveUser(String token) throws BaseException {
        KakaoProfile profile = findProfile(token);
        if(oAuthProvider.checkSavedUser(profile.getKakao_account().getEmail()) == 1) {
            return loginUser(profile);
        }

        if(oAuthProvider.checkEmail(profile.getKakao_account().getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        try {
            int user_idx = oAuthDao.createUser(profile);
            String jwt = jwtService.createJwt(user_idx);
            return new PostUserRes(user_idx, jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

//    public PostUserRes saveUser(String token, String refresh_token) throws BaseException {
//        KakaoProfile profile = findProfile(token);
//
//        if(oAuthProvider.checkSavedUser(profile.getKakao_account().getEmail()) == 1) {
//            return loginUser(profile);
//        }
//
//        if(oAuthProvider.checkEmail(profile.getKakao_account().getEmail()) == 1) {
//            throw new BaseException(POST_USERS_EXISTS_EMAIL);
//        }
//
//        try {
//            int user_idx = oAuthDao.createUser(profile, refresh_token);
//            String jwt = jwtService.createJwt(user_idx);
//            return new PostUserRes(user_idx, jwt);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public PostUserRes loginUser(KakaoProfile profile) throws BaseException {
        try {
            int user_idx = oAuthDao.findUserByEmail(profile);
            String jwt = jwtService.createJwt(user_idx);
            return new PostUserRes(user_idx, jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public KakaoProfile findProfile(String token) throws BaseException {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (Exception exception) {
            throw new BaseException(KAKAO_CONNECTION_ERROR);
        }

        return kakaoProfile;
    }

}
