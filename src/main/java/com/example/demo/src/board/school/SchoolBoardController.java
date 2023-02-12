package com.example.demo.src.board.school;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.school.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
public class SchoolBoardController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SchoolBoardProvider schoolBoardProvider;

    @Autowired
    private final SchoolBoardService schoolBoardService;


    private final JwtService jwtService;

    public SchoolBoardController(SchoolBoardProvider schoolBoardProvider, SchoolBoardService schoolBoardService, JwtService jwtService) {
        this.schoolBoardProvider = schoolBoardProvider;
        this.schoolBoardService = schoolBoardService;
        this.jwtService = jwtService;
    }

    /**
     * 각 학교 게시판 전체 조회
     * [GET] /board/school/:schoolNameIdx
     */

    @ResponseBody
    @GetMapping("/board/school/{schoolNameIdx}")
    public BaseResponse<List<GetSchoolBoardRes>> getSchoolBoardAll(@PathVariable("schoolNameIdx") int schoolNameIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            List<GetSchoolBoardRes> getSchoolBoardRes = schoolBoardProvider.getSchoolBoard(userIdx, schoolNameIdx);

            return new BaseResponse<>(getSchoolBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 학교 게시판 하나씩 조회 + 조회수 증가
     * [GET] /board/school/:schoolNameIdx/:postIdx
     */

    @ResponseBody
    @GetMapping("/board/school/{schoolNameIdx}/{postIdx}")
    public BaseResponse<GetSchoolBoardDetailRes> getOneOfSchoolBoard(@PathVariable("schoolNameIdx") int schoolNameIdx, @PathVariable("postIdx") int postIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            GetSchoolBoardDetailRes getSchoolBoardDetailRes = schoolBoardProvider.getSchoolBoardDetail(userIdx, postIdx);

            return new BaseResponse<>(getSchoolBoardDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 학교별 게시판 추가
     * [POST] /board/school/:schoolNameIdx/add
     */

    @ResponseBody
    @PostMapping("/board/school/{schoolNameIdx}/add")
    public BaseResponse<String> postSchoolBoard(@PathVariable("schoolNameIdx") int schoolNameIdx, @RequestBody PostSchoolBoardReq postSchoolBoardReq) {
        try {
            int userIdx = jwtService.getUserIdx();

            if (postSchoolBoardReq.getPost_name() == null || postSchoolBoardReq.getPost_name().equals("")) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_TITLE_NULL);
            }
            if (postSchoolBoardReq.getPost_name().length() > 50) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_TITLE_INVALID);
            }
            if (postSchoolBoardReq.getPost_content() == null || postSchoolBoardReq.getPost_content().equals("")) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_CONTENT_NULL);
            }
            if (postSchoolBoardReq.getPost_content().length() > 500) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_CONTENT_INVALID);
            }
            if (postSchoolBoardReq.getPost_anonymity() == null || postSchoolBoardReq.getPost_anonymity().equals("")) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_ANONYMITY_NULL);
            }

            schoolBoardService.postSchoolBoard(userIdx, schoolNameIdx, postSchoolBoardReq);

            return new BaseResponse<>(POST_SCHOOL_BOARD_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 학교게시판 수정
     * [PATCH] /board/school/:schoolNameIdx/modify/:postIdx
     */

    @ResponseBody
    @PatchMapping("/board/school/{schoolNameIdx}/modify/{postIdx}")
    public BaseResponse<String> patchSchoolBoard(@PathVariable("schoolNameIdx") int schoolNameIdx, @PathVariable("postIdx") int postIdx, @RequestBody PatchSchoolBoardReq patchSchoolBoardReq) {
        try {
            int userIdx = jwtService.getUserIdx();

            if (patchSchoolBoardReq.getPost_name() != null) {
                if (patchSchoolBoardReq.getPost_name().length() > 50) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_TITLE_INVALID);
                }
                if (patchSchoolBoardReq.getPost_name().equals("")) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_TITLE_NULL);
                }
            }

            if (patchSchoolBoardReq.getPost_content() != null) {
                if (patchSchoolBoardReq.getPost_content().equals("")) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_CONTENT_NULL);
                }
                if (patchSchoolBoardReq.getPost_content().length() > 500) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_CONTENT_INVALID);
                }
            }

            if (patchSchoolBoardReq.getPost_anonymity() != null) {
                if (patchSchoolBoardReq.getPost_anonymity().equals("")) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_ANONYMITY_NULL);
                }
            }


            schoolBoardService.patchSchoolBoard(userIdx, postIdx, patchSchoolBoardReq);

            return new BaseResponse<>(PATCH_SCHOOL_BOARD_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 학교게시판 삭제
     * [DELETE] /board/school/:schoolNameIdx/delete/:postIdx
     */

    @ResponseBody
    @DeleteMapping("/board/school/{schoolNameIdx}/delete/{postIdx}")
    public BaseResponse<String> deleteSchoolBoard(@PathVariable("schoolNameIdx") int schoolNameIdx, @PathVariable("postIdx") int postIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            schoolBoardService.deleteSchoolBoard(userIdx, postIdx);

            return new BaseResponse<>(DELETE_SCHOOL_BOARD_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 게시판 댓글 추가
     * [POST] /board/school/:schoolNameIdx/comment/add/:postIdx
     */

    @ResponseBody
    @PostMapping("/board/school/{schoolNameIdx}/comment/add/{postIdx}")
    public BaseResponse<String> postSchoolBoardComment(@PathVariable("schoolNameIdx") int schoolNameIdx, @PathVariable("postIdx") int postIdx, @RequestBody PostSchoolBoardCommentReq postSchoolBoardCommentReq) {
        try {
            int userIdx = jwtService.getUserIdx();

            if (postSchoolBoardCommentReq.getComment_content() == null || postSchoolBoardCommentReq.getComment_content().equals("")) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_NULL);
            }
            if (postSchoolBoardCommentReq.getComment_content().length() > 100) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_INVALID);
            }
            if (postSchoolBoardCommentReq.getComment_anonymity() == null || postSchoolBoardCommentReq.getComment_anonymity().equals("")) {
                return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_ANONYMITY_NULL);
            }

            schoolBoardService.postSchoolBoardComment(userIdx, postIdx, postSchoolBoardCommentReq);

            return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 학교게시판 댓글 수정
     * [PATCH] /board/school/:schoolNameIdx/comment/modify/:postIdx/:commentIdx
     */

    @ResponseBody
    @PatchMapping("/board/school/{schoolNameIdx}/comment/modify/{postIdx}/{commentIdx}")
    public BaseResponse<String> patchSchoolBoardComment(@PathVariable("commentIdx") int commentIdx, @RequestBody PatchSchoolBoardCommentReq patchSchoolBoardCommentReq) {
        try {
            int userIdx = jwtService.getUserIdx();

            if (patchSchoolBoardCommentReq.getComment_content() != null) {
                if (patchSchoolBoardCommentReq.getComment_content().equals("")) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_NULL);
                }
                if (patchSchoolBoardCommentReq.getComment_content().length() > 100) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_INVALID);
                }
            }
            if (patchSchoolBoardCommentReq.getComment_anonymity() != null) {
                if (patchSchoolBoardCommentReq.getComment_anonymity().equals("")) {
                    return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_ANONYMITY_NULL);
                }
            }

            schoolBoardService.patchSchoolBoardComment(userIdx, commentIdx, patchSchoolBoardCommentReq);

            return new BaseResponse<>(PATCH_SCHOOL_BOARD_COMMENT_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 학교게시판 댓글 삭제
     * [DELETE] /board/school/:schoolNameIdx/comment/delete/:postIdx/:commentIdx
     */

    @ResponseBody
    @DeleteMapping("/board/school/{schoolNameIdx}/comment/delete/{postIdx}/{commentIdx}")
    public BaseResponse<String> deleteSchoolBoardComment(@PathVariable("commentIdx") int commentIdx, @PathVariable("postIdx") int postIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            schoolBoardService.deleteSchoolBoardComment(userIdx, commentIdx);

            return new BaseResponse<>(DELETE_SCHOOL_BOARD_COMMENT_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 게시판 추천수 올리기
     * [POST] /board/school/:schoolNameIdx/recommend/:postIdx
     */

    @ResponseBody
    @PostMapping("/board/school/{schoolNameIdx}/recommend/{postIdx}")
    public BaseResponse<String> postSchoolBoardRecommend(@PathVariable("schoolNameIdx") int schoolNameIdx, @PathVariable("postIdx") int postIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            String postSchoolBoardRecommendRes = schoolBoardService.postSchoolBoardRecommend(userIdx, postIdx);

            if (postSchoolBoardRecommendRes == "추가") {
                return new BaseResponse<>(POST_SCHOOL_BOARD_RECOMMEND_SUCCESS);
            } else if (postSchoolBoardRecommendRes == "삭제") {
                return new BaseResponse<>(DELETE_SCHOOL_BOARD_RECOMMEND_SUCCESS);
            } else {
                return new BaseResponse<>(POST_SCHOOL_BOARD_RECOMMEND_FAIL);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 댓글 추천수 올리기
     * [POST] /board/school/:schoolNameIdx/comment/recommend/:postIdx/:commentIdx
     */

    @ResponseBody
    @PostMapping("/board/school/{schoolNameIdx}/comment/recommend/{postIdx}/{commentIdx}")
    public BaseResponse<String> postSchoolBoardCommentRecommend(@PathVariable("commentIdx") int commentIdx, @PathVariable("postIdx") int postIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            String postSchoolBoardCommentRecommendRes = schoolBoardService.postSchoolBoardCommentRecommend(userIdx, commentIdx);

            if (postSchoolBoardCommentRecommendRes == "추가") {
                return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_RECOMMEND_SUCCESS);
            } else if (postSchoolBoardCommentRecommendRes == "삭제") {
                return new BaseResponse<>(DELETE_SCHOOL_BOARD_COMMENT_RECOMMEND_SUCCESS);
            } else {
                return new BaseResponse<>(POST_SCHOOL_BOARD_COMMENT_RECOMMEND_FAIL);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
