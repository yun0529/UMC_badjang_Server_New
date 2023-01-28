package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
@Service
public class BoardProvider {

    private final BoardDao boardDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BoardProvider(BoardDao boardDao, JwtService jwtService) {
        this.boardDao = boardDao;
        this.jwtService = jwtService;
    }

    public List<GetBoardRes> getBoard() throws BaseException {
        try{
            List<GetBoardRes> getBoardRes = boardDao.getBoard();
            return getBoardRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> getBoardDetail(int post_idx) throws BaseException{
        try{
            List<GetBoardRes> getBoardDetailRes = boardDao.getBoardDetail(post_idx);
            return getBoardDetailRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> updateCommentCount(int post_idx) throws BaseException {
        try{
            List<GetBoardRes> updateCommentCount = boardDao.updateCommentCount(post_idx);
            return updateCommentCount;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> updateRecommendCount(int post_idx) throws BaseException {
        try{
            List<GetBoardRes> updateRecommendCount = boardDao.updateRecommendCount(post_idx);
            return updateRecommendCount;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<PostBoardRes> postBoard(PostBoardReq postBoardReq) throws BaseException{
        try{
            List<PostBoardRes> postBoardRes = boardDao.postBoard(postBoardReq);
            return postBoardRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> patchBoard(PatchBoardReq patchBoardReq) throws BaseException{
        try{
            List<GetBoardRes> patchBoardRes = boardDao.patchBoard(patchBoardReq);
            return patchBoardRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> deleteBoard(DeleteBoardReq deleteBoardReq) throws BaseException{
        try{
            List<GetBoardRes> deleteBoardRes = boardDao.deleteBoard(deleteBoardReq);
            return deleteBoardRes;
        }catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostRecommendRes postRecommend(PostRecommendReq postRecommendReq) throws BaseException {
        try {
            PostRecommendRes postRecommendRes = boardDao.postRecommend(postRecommendReq);
            return postRecommendRes;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 댓글 관련 부분 시작
     */

    public List<GetCommentRes> getComment(int post_idx) throws BaseException {
        try{
            List<GetCommentRes> getCommentRes = boardDao.getComment(post_idx);
            return getCommentRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostCommentRes postComment(PostCommentReq postCommentReq) throws BaseException {
        try {
            PostCommentRes postCommentRes = boardDao.postComment(postCommentReq);
            return new PostCommentRes(postCommentRes.getComment_idx(),postCommentRes.getPost_idx(),
                    postCommentRes.getComment_content(), postCommentRes.getComment_createAt());

        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCommentRes patchComment(PatchCommentReq patchCommentReq) throws BaseException {
        try{
            GetCommentRes patchCommentRes = boardDao.patchComment(patchCommentReq);
            return patchCommentRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCommentRes deleteComment(DeleteCommentReq deleteCommentReq) throws BaseException {
        try {
            GetCommentRes deleteCommentRes = boardDao.deleteComment(deleteCommentReq);
            return deleteCommentRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostCommentRecommendRes updateCommentRecommend (PostCommentRecommendReq postCommentRecommendReq) throws BaseException {
        try {
            PostCommentRecommendRes postCommentRecommendRes = boardDao.postCommentRecommend(postCommentRecommendReq);
            return new PostCommentRecommendRes(
                    postCommentRecommendRes.getComment_recommend_idx(),postCommentRecommendRes.getComment_idx());
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCommentRes> updateCommentRecommendCount(int comment_idx) throws BaseException {
        try{
            List<GetCommentRes> updateCommentRecommendCount = boardDao.updateCommentRecommendCount(comment_idx);
            return updateCommentRecommendCount;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
