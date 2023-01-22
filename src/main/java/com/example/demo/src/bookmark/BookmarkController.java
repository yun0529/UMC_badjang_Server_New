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

import static com.example.demo.config.BaseResponseStatus.POST_BOOKMARK_SUCCESS;
import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;

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


    @ResponseBody
    @GetMapping("/bookmark/board")
    public BaseResponse<List<GetBookmarkBoardRes>> getBookmarkBoard() {
        try {
            long userIdx = jwtService.getUserIdx();

            List<GetBookmarkBoardRes> getBookmarkBoardRes = bookmarkProvider.getBookmarkBoard(userIdx);
            return new BaseResponse<>(getBookmarkBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/bookmark/scholarship")
    public BaseResponse<List<GetBookmarkScholarshipRes>> getBookmarkScholarship() {
        try {
            long userIdx = jwtService.getUserIdx();

            List<GetBookmarkScholarshipRes> getBookmarkScholarshipRes = bookmarkProvider.getBookmarkScholarship(userIdx);
            return new BaseResponse<>(getBookmarkScholarshipRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/bookmark/support")
    public BaseResponse<List<GetBookmarkSupportRes>> getBookmarkSupport() {
        try {
            long userIdx = jwtService.getUserIdx();

            List<GetBookmarkSupportRes> getBookmarkSupportRes = bookmarkProvider.getBookmarkSupport(userIdx);
            return new BaseResponse<>(getBookmarkSupportRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/scholarships/{scholarshipIdx}/bookmark")
    public BaseResponse<String> postBookmarkScholarship(@PathVariable("scholarshipIdx") long scholarshipIdx) throws BaseException {
        try {
            long userIdx = jwtService.getUserIdx();
            PostBookmarkScholarshipReq postBookmarkScholarshipReq = new PostBookmarkScholarshipReq(userIdx, scholarshipIdx);
            bookmarkService.postBookmarkScholarship(postBookmarkScholarshipReq);

            return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/supports/{supportIdx}/bookmark")
    public BaseResponse<String> postBookmarkSupport(@PathVariable("supportIdx") long supportIdx) throws BaseException {
        try {
            long userIdx = jwtService.getUserIdx();
            PostBookmarkSupportReq postBookmarkSupportReq = new PostBookmarkSupportReq(userIdx, supportIdx);

            bookmarkService.postBookmarkSupport(postBookmarkSupportReq);

            return new BaseResponse<>(POST_BOOKMARK_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
