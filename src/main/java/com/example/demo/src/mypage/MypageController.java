package com.example.demo.src.mypage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.mypage.model.*;
import com.example.demo.src.search.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

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

    /**
     * 내가 작성한 게시글 조회 API
     * [GET] /myboard
     */
    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/myboard") // (GET) 127.0.0.1:9000/board
    public BaseResponse<List<GetBoardRes>> getBoard() {
        try{
            int user_idx = jwtService.getUserIdx();
            List<GetBoardRes> getBoardRes = mypageProvider.getBoard(user_idx);
            return new BaseResponse<>(getBoardRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 내가 작성한 댓글 조회 API
     * [GET] /myboard
     */
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/mycomment") // (GET) 127.0.0.1:9000/board
    public BaseResponse<List<GetCommentRes>> getComment() {
        try{
            int user_idx = jwtService.getUserIdx();
            List<GetCommentRes> getCommentRes = mypageProvider.getComment(user_idx);
            return new BaseResponse<>(getCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 학교 및 지역 변경 API
     * [PATCH] /info
     */
    @ResponseBody
    @PatchMapping("/info")
    public BaseResponse<String> saveUserUnivInfo(@RequestBody PatchInfoReq patchInfoReq) {
        try {
            int user_idx = jwtService.getUserIdx();
            mypageService.saveUserUnivInfo(user_idx, patchInfoReq);
            return new BaseResponse<>("정보가 저장되었습니다.");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 공지사항 전체 조회(목록) API
     * [GET] /notice
     */
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/notice") // (GET) 127.0.0.1:9000/board
    public BaseResponse<List<GetNoticeRes>> getNotice() {
        try{
            //int user_idx = jwtService.getUserIdx();
            List<GetNoticeRes> getNoticeRes = mypageProvider.getNotice();
            return new BaseResponse<>(getNoticeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 공지사항 조회 API
     * [GET] /notice/:notice_idx
     */
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/notice/{notice_idx}") // (GET) 127.0.0.1:9000/board
    public BaseResponse<GetNoticeRes> getNoticeDetail(@PathVariable("notice_idx")int notice_idx) {
        try{
            //int user_idx = jwtService.getUserIdx();
            GetNoticeRes getNoticeRes = mypageProvider.getNoticeDetail(notice_idx);
            return new BaseResponse<>(getNoticeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}