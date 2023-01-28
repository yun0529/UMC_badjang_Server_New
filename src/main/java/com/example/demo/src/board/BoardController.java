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
    private final JwtService jwtService;




    public BoardController(BoardProvider boardProvider, JwtService jwtService){
        this.boardProvider = boardProvider;
        this.jwtService = jwtService;
    }

    /**
     * 자유게시판 전체 글 조회 API
     */
    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/board") // (GET) 127.0.0.1:9000/board
    public BaseResponse<List<GetBoardRes>> getBoard() {
        try{
            List<GetBoardRes> getBoardRes = boardProvider.getBoard();
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
    @GetMapping("/board/detail/{post_idx}")
    public BaseResponse<List<GetBoardRes>> getBoardDetail(@PathVariable("post_idx")int post_idx){
        try{
            List<GetBoardRes> getBoardDetailRes = boardProvider.getBoardDetail(post_idx);
            return new BaseResponse<>(getBoardDetailRes);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/board/detail/comment/{post_idx}")
    public BaseResponse<List<GetBoardRes>> getUpdateCommentCount(@PathVariable("post_idx")int post_idx){
        try{
            List<GetBoardRes> getBoardCommentRes = boardProvider.updateCommentCount(post_idx);
            return new BaseResponse<>(getBoardCommentRes);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/board/detail/recommend/{post_idx}")
    public BaseResponse<List<GetBoardRes>> getUpdateRecommendCount(@PathVariable("post_idx")int post_idx){
        try{
            List<GetBoardRes> getBoardCommentRes = boardProvider.updateRecommendCount(post_idx);
            return new BaseResponse<>(getBoardCommentRes);
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
            } else if (postBoardReq.getPost_name() == null) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_BOARD_NAME);
            } else if (postBoardReq.getPost_content() == null) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_BOARD_CONTENT);
            } else if (postBoardReq.getPost_category() == null) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_CATEGORY_IDX);
            }
            List<PostBoardRes> postBoardRes = boardProvider.postBoard(postBoardReq);
            return new BaseResponse<>(postBoardRes);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PatchMapping("/board/modify/{post_idx}")
    public BaseResponse<List<GetBoardRes>> patchBoard(@PathVariable("post_idx") int post_idx,
                                                       @RequestBody PatchBoardReq patchBoardReq) {
            try{
                if(post_idx != patchBoardReq.getPost_idx()){
                    return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
                }
                else if(post_idx == 0){
                    return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
                }
                List<GetBoardRes> patchBoardRes = boardProvider.patchBoard(patchBoardReq);
                return new BaseResponse<>(patchBoardRes);

            } catch(BaseException exception){
                System.out.println(exception);
                return new BaseResponse<>((exception.getStatus()));
            }
    }

    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @DeleteMapping("/board/delete/{post_idx}")
    public BaseResponse<List<GetBoardRes>> deleteBoard(@PathVariable("post_idx") int post_idx,
                                                          @RequestBody DeleteBoardReq deleteBoardReq){
        try{
            if(post_idx != deleteBoardReq.getPost_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
            }
            else if(post_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
            }
            List<GetBoardRes> deleteBoardRes = boardProvider.deleteBoard(deleteBoardReq);
            return new BaseResponse<>(deleteBoardRes);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/board/recommend/{post_idx}")
    public BaseResponse<PostRecommendRes> postRecommend(@PathVariable("post_idx")int post_idx,
                                                    @RequestBody PostRecommendReq postRecommendReq){
        try {
            if(post_idx != postRecommendReq.getPost_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
            }
            else if(postRecommendReq.getUser_idx() == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_IDX);
            }
            else if(postRecommendReq.getPost_idx() == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
            }
            PostRecommendRes postRecommendRes = boardProvider.postRecommend(postRecommendReq);
            return new BaseResponse<>(postRecommendRes);
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
    @PostMapping("/board/comment/add/{post_idx}")
    public BaseResponse<PostCommentRes> postComment(@PathVariable("post_idx") int post_idx,
                                                    @RequestBody PostCommentReq postCommentReq){
        try{
            if(post_idx != postCommentReq.getPost_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_POST_IDX);
            }
            else if(post_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_POST_IDX);
            }
            else if (postCommentReq.getComment_content() == null) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_CONTENT);
            }
            else if (postCommentReq.getComment_anonymity() == null) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_ANONYMITY);
            }
            else if (postCommentReq.getComment_status() == null) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_STATUS);
            }

            PostCommentRes postCommentRes = boardProvider.postComment(postCommentReq);
            return new BaseResponse<>(postCommentRes);
        }catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //[PATCH] 댓글 수정 (댓글 인덱스 사용)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PatchMapping("/board/comment/modify/{comment_idx}")
    public BaseResponse<GetCommentRes> patchBoard(@PathVariable("comment_idx") int comment_idx,
                                                        @RequestBody PatchCommentReq patchCommentReq) {
        try{
            if(comment_idx != patchCommentReq.getComment_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_COMMENT_IDX);
            }
            else if(comment_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_IDX);
            }
            GetCommentRes patchCommentRes = boardProvider.patchComment(patchCommentReq);
            return new BaseResponse<>(patchCommentRes);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //[DELETE] 댓글 삭제 (댓글 인덱스 사용)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @DeleteMapping("/board/comment/delete/{comment_idx}")
    public BaseResponse<GetCommentRes> deleteComment(@PathVariable("comment_idx") int comment_idx,
                                                          @RequestBody DeleteCommentReq deleteCommentReq){
        try{
            if(comment_idx != deleteCommentReq.getComment_idx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_COMMENT_IDX);
            }
            else if(comment_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMENT_IDX);
            }
            GetCommentRes deleteBoardRes = boardProvider.deleteComment(deleteCommentReq);
            return new BaseResponse<>(deleteBoardRes);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

