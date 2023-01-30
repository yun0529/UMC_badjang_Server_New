package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.bookmark.model.*;
import com.example.demo.src.search.model.GetSearchBoardRes;
import com.example.demo.src.search.model.GetSearchScholarshipRes;
import com.example.demo.src.search.model.GetSearchSupportRes;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
public class BookmarkController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BookmarkProvider bookmarkProvider;

    @Autowired
    private final BookmarkService bookmarkService;

    private final JwtService jwtService;

    public BookmarkController(BookmarkProvider bookmarkProvider, JwtService jwtService, BookmarkService bookmarkService) {
        this.bookmarkProvider = bookmarkProvider;
        this.jwtService = jwtService;
        this.bookmarkService = bookmarkService;
    }

    /**
     * 즐겨찾기 조회
     * [GET] /bookmark
     */
    @ResponseBody
    @GetMapping("/bookmark")
    public BaseResponse<GetBookmarkAllRes> getBookmarkAll() {
        try {
            int userIdx = jwtService.getUserIdx();
            GetBookmarkAllRes getBookmarkAllRes = bookmarkProvider.getBookmarkAll(userIdx);

            return new BaseResponse<>(getBookmarkAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즐겨찾기 조회(게시판만)
     * [GET] /bookmark/board
     */
    @ResponseBody
    @GetMapping("/bookmark/board")
    public BaseResponse<List<GetBookmarkBoardRes>> getBookmarkBoard() {
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetBookmarkBoardRes> getBookmarkBoardRes = bookmarkProvider.getBookmarkBoard(userIdx);
            return new BaseResponse<>(getBookmarkBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즐겨찾기 조회(장학금만)
     * [GET] /bookmark/scholarship
     */
    @ResponseBody
    @GetMapping("/bookmark/scholarship")
    public BaseResponse<List<GetBookmarkScholarshipRes>> getBookmarkScholarship() {
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetBookmarkScholarshipRes> getBookmarkScholarshipRes = bookmarkProvider.getBookmarkScholarship(userIdx);
            return new BaseResponse<>(getBookmarkScholarshipRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즐겨찾기 조회(지원금만)
     * [GET] /bookmark/support
     */
    @ResponseBody
    @GetMapping("/bookmark/support")
    public BaseResponse<List<GetBookmarkSupportRes>> getBookmarkSupport() {
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetBookmarkSupportRes> getBookmarkSupportRes = bookmarkProvider.getBookmarkSupport(userIdx);
            return new BaseResponse<>(getBookmarkSupportRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장학금 페이지 즐겨찾기 추가 및 취소
     * [POST] /scholarships/:scholarshipIdx/bookmark
     */
    @PostMapping("/scholarships/{scholarshipIdx}/bookmark")
    public BaseResponse<String> postBookmarkScholarship(@PathVariable("scholarshipIdx") int scholarshipIdx) throws BaseException {
        try {
            int userIdx = jwtService.getUserIdx();
            PostBookmarkScholarshipReq postBookmarkScholarshipReq = new PostBookmarkScholarshipReq(userIdx, scholarshipIdx);
            String postBookmarkScholarshipRes = bookmarkService.postBookmarkScholarship(postBookmarkScholarshipReq);

            if (postBookmarkScholarshipRes == "삭제") {
                return new BaseResponse<>(DELETE_BOOKMARK_SUCCESS);
            } else if (postBookmarkScholarshipRes == "추가") {
                return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
            } else {
                return new BaseResponse<>(POST_BOOKMARK_FAIL);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 지원금 페이지 즐겨찾기 추가 및 취소
     * [POST] /supports/:supportIdx/bookmark
     */
    @PostMapping("/supports/{supportIdx}/bookmark")
    public BaseResponse<String> postBookmarkSupport(@PathVariable("supportIdx") int supportIdx) throws BaseException {
        try {
            int userIdx = jwtService.getUserIdx();
            PostBookmarkSupportReq postBookmarkSupportReq = new PostBookmarkSupportReq(userIdx, supportIdx);
            String postBookmarkSupportRes = bookmarkService.postBookmarkSupport(postBookmarkSupportReq);

            if (postBookmarkSupportRes == "삭제") {
                return new BaseResponse<>(DELETE_BOOKMARK_SUCCESS);
            } else if (postBookmarkSupportRes == "추가") {
                return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
            } else {
                return new BaseResponse<>(POST_BOOKMARK_FAIL);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 자유게시판 즐겨찾기 추가 및 취소
     * [POST] /board/detail/:post_idx/bookmark
     */
    @PostMapping("/board/detail/{post_idx}/bookmark")
    public BaseResponse<String> postBookmarkBoard(@PathVariable("post_idx") int postIdx) throws BaseException {
        try {
            int userIdx = jwtService.getUserIdx();
            PostBookmarkBoardReq postBookmarkBoardReq = new PostBookmarkBoardReq(userIdx, postIdx);
            String postBookmarkBoardRes = bookmarkService.postBookmarkBoard(postBookmarkBoardReq);

            if (postBookmarkBoardRes == "삭제") {
                return new BaseResponse<>(DELETE_BOOKMARK_SUCCESS);
            } else if (postBookmarkBoardRes == "추가") {
                return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
            } else {
                return new BaseResponse<>(POST_BOOKMARK_FAIL);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 학교게시판 즐겨찾기 추가 및 취소
     * [POST] /board/school/:schoolNameIdx/:postIdx/bookmark
     */
    @PostMapping("/board/school/{schoolNameIdx}/{postIdx}/bookmark")
    public BaseResponse<String> postBookmarkSchoolBoard(@PathVariable("postIdx") int postIdx) throws BaseException {
        try {
            int userIdx = jwtService.getUserIdx();
            PostBookmarkBoardReq postBookmarkBoardReq = new PostBookmarkBoardReq(userIdx, postIdx);
            String postBookmarkBoardRes = bookmarkService.postBookmarkBoard(postBookmarkBoardReq);

            if (postBookmarkBoardRes == "삭제") {
                return new BaseResponse<>(DELETE_BOOKMARK_SUCCESS);
            } else if (postBookmarkBoardRes == "추가") {
                return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
            } else {
                return new BaseResponse<>(POST_BOOKMARK_FAIL);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


//    /**
//     * 우리학교 장학금 페이지 즐겨찾기 추가 및 취소
//     * [POST] /menu/school/:schoolIdx/bookmark
//     */
//    @PostMapping("/menu/school/{schoolIdx}/bookmark")
//    public BaseResponse<String> postBookmarkSchool(@PathVariable("schoolIdx") int schoolIdx) throws BaseException {
//        try {
//            int userIdx = jwtService.getUserIdx();
//            PostBookmarkSchoolReq postBookmarkSchoolReq = new PostBookmarkSchoolReq(userIdx, schoolIdx);
//            String postBookmarkSchoolRes = bookmarkService.postBookmarkSchool(postBookmarkSchoolReq);
//
//            if (postBookmarkSchoolRes == "삭제") {
//                return new BaseResponse<>(DELETE_BOOKMARK_SUCCESS);
//            } else if (postBookmarkSchoolRes == "추가") {
//                return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
//            } else {
//                return new BaseResponse<>(POST_BOOKMARK_FAIL);
//            }
//
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 전국소식 페이지 즐겨찾기 추가 및 취소
//     * [POST] /menu/total/:totalIdx/bookmark
//     */
//    @PostMapping("/menu/total/{totalIdx}/bookmark")
//    public BaseResponse<String> postBookmarkTotal(@PathVariable("totalIdx") int totalIdx) throws BaseException {
//        try {
//            int userIdx = jwtService.getUserIdx();
//            PostBookmarkTotalReq postBookmarkTotalReq = new PostBookmarkTotalReq(userIdx, totalIdx);
//            String postBookmarkTotalRes = bookmarkService.postBookmarkTotal(postBookmarkTotalReq);
//
//            if (postBookmarkTotalRes == "삭제") {
//                return new BaseResponse<>(DELETE_BOOKMARK_SUCCESS);
//            } else if (postBookmarkTotalRes == "추가") {
//                return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
//            } else {
//                return new BaseResponse<>(POST_BOOKMARK_FAIL);
//            }
//
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

}
