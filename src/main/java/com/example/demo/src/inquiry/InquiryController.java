package com.example.demo.src.inquiry;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.inquiry.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/inquiries")
public class InquiryController {

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.
    @Autowired
    private final InquiryProvider inquiryProvider;
    @Autowired
    private final InquiryService inquiryService;

    @Autowired
    private final JwtService jwtService;

    public InquiryController(InquiryProvider inquiryProvider, InquiryService inquiryService, JwtService jwtService) {
        this.inquiryProvider = inquiryProvider;
        this.inquiryService = inquiryService;
        this.jwtService = jwtService;
    }

    /**
    * 문의 목록 전체 조회*/
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetInquiryRes>> getInquiries(){
        try {
            List<GetInquiryRes> getInquiryRes = inquiryProvider.getInquiries();
            return new BaseResponse<>(getInquiryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**idx로 문의 하나 조회*/
    @ResponseBody
    @GetMapping("/{inquiryIdx}")
    public BaseResponse<GetInquiryRes> getInquiryByIdx(@PathVariable("inquiryIdx") Integer inquiryIdx) {

        try {
            if(inquiryIdx == null) {
                throw new BaseException(GET_INQUIRY_EMPTY_IDX);
            }

            GetInquiryRes getInquiryRes = inquiryProvider.getInquiryByIdx(inquiryIdx);
            return new BaseResponse<>(getInquiryRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**문의 생성*/
    @ResponseBody
    @PostMapping("/new-inquiry")
    public BaseResponse<PostInquiryRes> createInquiry(@RequestBody PostInquiryReq postInquiryReq) {
        if (postInquiryReq.getInquiry_title() == null) {
            return new BaseResponse<>(POST_INQUIRY_EMPTY_TITLE);
        }
        if(postInquiryReq.getInquiry_content() == null) {
            return new BaseResponse<>(POST_INQUIRY_EMPTY_CONTENT);
        }
        try {
            Integer userIdxByJwt = jwtService.getUserIdx();
            Integer user_idx = postInquiryReq.getUser_idx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostInquiryRes postInquiryRes = inquiryService.createInquiry(postInquiryReq);
            return new BaseResponse<>(postInquiryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**문의 수정*/
    @ResponseBody
    @PatchMapping("/modify/{inquiry_idx}")
    public BaseResponse<String> modifyInquiry(@PathVariable("inquiry_idx") Integer inquiryIdx, @RequestBody PatchInquiryReq patchInquiryRes) {
        try {
            Integer userIdxByJwt = jwtService.getUserIdx(); // 토큰은 헤더에 있음
            Integer user_idx = patchInquiryRes.getUser_idx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //수정할 댓글이 비었을 경우
            if(patchInquiryRes.getInquiry_content() == null){
                return new BaseResponse<>(PATCH_INQUIRY_EMPTY_CONTENT);
            }

            inquiryService.modifyInquiry(patchInquiryRes);

            //PatchSupportCommentReq patchSupportCommentReq = new PatchSupportCommentReq(support_comment_idx, supportComment.getSupport_comment_content());
            //supportCommentService.modifySupportComment(patchSupportCommentReq);

            String result = "댓글이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 문의하기 삭제 API
     * [DELETE] /inquiries/delete/:inquiry_idx
     */
    @ResponseBody
    @PatchMapping("/delete/{inquiry_idx}")
    public BaseResponse<String> deleteInquiry(@PathVariable("inquiry_idx") Integer inquiry_idx, @RequestBody DeleteInquiryReq deleteInquiryReq) {
        try {
            Integer userIdxByJwt = jwtService.getUserIdx();
            Integer user_idx = deleteInquiryReq.getUser_idx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            //Integer support_idx = deleteSupportCommentReq.getSupport_idx();
            Integer inquiry_idx_ByReqBody = deleteInquiryReq.getInquiry_idx();
            // PathVariable로 들어온 댓글 인덱스와 RequestBody로 받은 댓글 인덱스가 같은지 확인
            if(inquiry_idx != inquiry_idx_ByReqBody) {
                return new BaseResponse<>(DELETE_WRONG_INQUIRY_INDEX);
            }

            inquiryService.deleteInquiry(inquiry_idx);

            String result = "문의가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}

