package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.board.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RestController
@RequestMapping("")
public class BoardController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BoardProvider boardProvider;
    @Autowired
    private final BoardDao boardDao;
    @Autowired
    private final JwtService jwtService;

    public BoardController(BoardProvider boardProvider, BoardDao boardDao, JwtService jwtService){
        this.boardProvider = boardProvider;
        this.boardDao = boardDao;
        this.jwtService = jwtService;
    }

    /**
     * 게시판 목록 조회 API
     */

    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/board/totalBoard") // (GET) 127.0.0.1:9000/board
    public BaseResponse<List<GetBoardTotal>> getBoardTotal() {
        try{
            List<GetBoardTotal> getBoardRes = boardProvider.getBoardTotal();
            return new BaseResponse<>(getBoardRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 자유게시판 전체 글 조회 API
     */
    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/board/{user_idx}") // (GET) 127.0.0.1:9000/board
    public BaseResponse<List<GetBoardRes>> getBoard(@PathVariable("user_idx")int user_idx) {
        try{
            List<GetBoardRes> getBoardRes = boardProvider.getBoard(user_idx);
            return new BaseResponse<>(getBoardRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 자유게시판 게시글 조회(조회 수 증가)
     * [GET/PATCH] /board/detail/{post_idx}
     * @return BaseResponse<GetBoardRes>
     */
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/board/detail/{user_idx}/{post_idx}")
    public BaseResponse<List<GetBoardRes>> getBoardDetail(@PathVariable("user_idx")int user_idx,
                                                          @PathVariable("post_idx")int post_idx){
        try{
            List<GetBoardRes> getBoardDetailRes = boardProvider.getBoardDetail(user_idx, post_idx);
            boardProvider.updateViewCount(post_idx);
            boardProvider.updateCommentCount(post_idx);
            boardProvider.updateCommentRecommendCount(post_idx);

            return new BaseResponse<>(getBoardDetailRes);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 자유게시판 게시글 작성
     * [POST] /board/add
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/board/add/{user_idx}")
    public BaseResponse<List<PostBoardRes>> postBoard(@PathVariable("user_idx")int user_idx,
                                                @RequestBody PostBoardReq postBoardReq) {
        try {
            int idx = jwtService.getUserIdx();
            if (idx != user_idx) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            } else if (user_idx == 0) {
                return new BaseResponse<>(BaseResponseStatus.USERS_EMPTY_USER_IDX);
            } else if (postBoardReq.getPost_name() == null || postBoardReq.getPost_name() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_BOARD_NAME);
            } else if (postBoardReq.getPost_content() == null || postBoardReq.getPost_content() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_BOARD_CONTENT);
            } else if (postBoardReq.getPost_category() == null || postBoardReq.getPost_category() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_CATEGORY_IDX);
            } else if (postBoardReq.getPost_name().length() > 50){
                return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_TITLE_INVALID);
            } else if (postBoardReq.getPost_content().length() > 500) {
                return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_CONTENT_INVALID);
            } else if(postBoardReq.getPost_anonymity() == null || postBoardReq.getPost_anonymity().equals("")) {
                return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_ANONYMITY_NULL);
            }
            List<PostBoardRes> postBoardRes = boardProvider.postBoard(postBoardReq);
            return new BaseResponse<>(postBoardRes);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //게시글 수정
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PatchMapping("/board/modify/{post_idx}/{user_idx}")
    public BaseResponse<String> patchBoard(@PathVariable("post_idx") int post_idx, @PathVariable("user_idx") int user_idx,
                                                       @RequestBody PatchBoardReq patchBoardReq) {
            try{
                int idx = jwtService.getUserIdx();
                if(post_idx != patchBoardReq.getPost_idx()){
                    return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
                } else if (idx != user_idx) {
                    return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
                } else if(post_idx== 0){
                    return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
                } else if (patchBoardReq.getPost_name() == "") {
                    return new BaseResponse<>(BaseResponseStatus.EMPTY_BOARD_NAME);
                } else if (patchBoardReq.getPost_content() == "") {
                    return new BaseResponse<>(BaseResponseStatus.EMPTY_BOARD_CONTENT);
                } else if (patchBoardReq.getPost_category() == "") {
                    return new BaseResponse<>(BaseResponseStatus.EMPTY_CATEGORY_IDX);
                } else if (patchBoardReq.getPost_name().length() > 50){
                    return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_TITLE_INVALID);
                } else if (patchBoardReq.getPost_content().length() > 500) {
                    return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_CONTENT_INVALID);
                } else if (patchBoardReq.getAnonymity() == "") {
                    return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_ANONYMITY_NULL);
                }
                boardProvider.patchBoard(patchBoardReq);
                String result = "게시글이 수정되었습니다";
                return new BaseResponse<>(result);

            } catch(BaseException exception){
                System.out.println(exception);
                return new BaseResponse<>((exception.getStatus()));
            }
    }
    //게시물 삭제
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @DeleteMapping("/board/delete/{post_idx}/{user_idx}")
    public BaseResponse<String> deleteBoard(@PathVariable("post_idx") int post_idx,@PathVariable("user_idx") int user_idx,
                                                          @RequestBody DeleteBoardReq deleteBoardReq){
        try{
            int idx = jwtService.getUserIdx();
            if(idx != user_idx){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            else if(post_idx != deleteBoardReq.getPost_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
            }
            else if(post_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
            }
            boardProvider.deleteBoard(deleteBoardReq);

            String result = "삭제에 성공하였습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //게시물 추천 및 취소
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/board/recommend/{post_idx}")
    public BaseResponse<String> postRecommend(@PathVariable("post_idx")int post_idx,
                                                    @RequestBody PostRecommendReq postRecommendReq){
        try {
            int checkRecommend = boardDao.checkRecommend(postRecommendReq);
            if(post_idx != postRecommendReq.getPost_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
            }
            else if(postRecommendReq.getUser_idx() == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_IDX);
            }
            else if(postRecommendReq.getPost_idx() == 0) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
            }
            else if(checkRecommend == 1){
                boardProvider.deleteRecommend(postRecommendReq);
                boardProvider.updateRecommendCount(postRecommendReq.getPost_idx());
                String result = "추천을 취소하였습니다." ;
                return new BaseResponse<>(result);
            }

            boardProvider.postRecommend(postRecommendReq);
            boardProvider.updateRecommendCount(postRecommendReq.getPost_idx());
            String result = "본 게시물을 추천하였습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //[GET] 댓글 조회 (게시글 인덱스 사용)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/board/comment/{post_idx}")
    public BaseResponse<List<GetCommentRes>> getComment(@PathVariable("post_idx")int post_idx){
        try{
            List<GetCommentRes> getCommentRes = boardProvider.getComment(post_idx);
            return new BaseResponse<>(getCommentRes);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //[POST] 댓글 작성 (게시글 인덱스 사용)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/board/comment/add/{post_idx}/{user_idx}")
    public BaseResponse<PostCommentRes> postComment(@PathVariable("post_idx") int post_idx,
                                                    @PathVariable("user_idx") int user_idx,
                                                    @RequestBody PostCommentReq postCommentReq){
        try{
            int idx = jwtService.getUserIdx();
            if(post_idx != postCommentReq.getPost_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
            }
            else if(post_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
            }
            else if (postCommentReq.getComment_content() == null || postCommentReq.getComment_content() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_CONTENT);
            }
            else if (postCommentReq.getComment_anonymity() == null || postCommentReq.getComment_anonymity() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_ANONYMITY);
            }
            else if (postCommentReq.getComment_status() == null || postCommentReq.getComment_status() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_STATUS);
            }
            else if(idx != user_idx){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            else if (postCommentReq.getComment_content().length() > 100) {
                return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_COMMENT_INVALID);
            }
            else if (postCommentReq.getComment_anonymity() == null || postCommentReq.getComment_anonymity().equals("")) {
                return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_COMMENT_ANONYMITY_NULL);
            }
                PostCommentRes postCommentRes = boardProvider.postComment(postCommentReq);
                boardProvider.updateCommentCount(post_idx);
                return new BaseResponse<>(postCommentRes);
        }catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //[PATCH] 댓글 수정 (댓글 인덱스 사용)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PatchMapping("/board/comment/modify/{comment_idx}/{user_idx}")
    public BaseResponse<String> patchBoard(@PathVariable("comment_idx") int comment_idx,
                                                  @PathVariable("user_idx") int user_idx,
                                                        @RequestBody PatchCommentReq patchCommentReq) {
        try{
            int idx = jwtService.getUserIdx();
            if(comment_idx != patchCommentReq.getComment_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_COMMENT_IDX);
            }
            else if(comment_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_IDX);
            }
            else if(idx != user_idx){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            else if (patchCommentReq.getComment_content() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_CONTENT);
            }
            else if (patchCommentReq.getComment_content().length() > 100){
                return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_COMMENT_INVALID);
            }
            else if (patchCommentReq.getAnonymity() == ""){
                return new BaseResponse<>(BaseResponseStatus.POST_SCHOOL_BOARD_COMMENT_ANONYMITY_NULL);
            }
            boardProvider.patchComment(patchCommentReq);
            String result = "댓글을 수정하였습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //[DELETE] 댓글 삭제 (댓글 인덱스 사용)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @DeleteMapping("/board/comment/delete/{comment_idx}/{user_idx}")
    public BaseResponse<String> deleteComment(@PathVariable("comment_idx") int comment_idx,
                                                     @PathVariable("user_idx") int user_idx,
                                                          @RequestBody DeleteCommentReq deleteCommentReq){
        try{
            int idx = jwtService.getUserIdx();
            if(comment_idx != deleteCommentReq.getComment_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_COMMENT_IDX);
            }
            else if(comment_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_IDX);
            }
            else if(idx != user_idx){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            boardProvider.deleteComment(deleteCommentReq);
            boardProvider.updateCommentCount(deleteCommentReq.getPost_idx());
            String result = "댓글을 삭제했습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //[Post] 댓글 추천 증감(댓글 인덱스 사용)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/board/detail/comment/recommend/{comment_idx}")
    public BaseResponse<String> getUpdateCommentRecommendCount(@PathVariable("comment_idx")int comment_idx,
                                                                                @RequestBody PostCommentRecommendReq postCommentRecommendReq){
        try{
            int checkCommentRecommend = boardDao.checkCommentRecommend(postCommentRecommendReq);
            if(comment_idx != postCommentRecommendReq.getComment_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_COMMENT_IDX);
            }
            else if(comment_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_IDX);
            }
            else if(checkCommentRecommend == 1){
                boardProvider.deleteCommentRecommend(postCommentRecommendReq);
                boardProvider.updateCommentRecommendCount(postCommentRecommendReq.getComment_idx());
                String result = "댓글을 추천을 취소했습니다.";
                return new BaseResponse<>(result);
            }
            else {
                boardProvider.postCommentRecommend(postCommentRecommendReq);
                boardProvider.updateCommentRecommendCount(postCommentRecommendReq.getComment_idx());
                String result = "댓글을 추천했습니다.";
                return new BaseResponse<>(result);
            }
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

