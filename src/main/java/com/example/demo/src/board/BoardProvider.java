package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
@Service
public class BoardProvider {

    private final BoardDao boardDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BoardProvider(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<GetBoardRes> getBoard(int user_idx) throws BaseException {
        try{
            List<GetBoardRes> getBoardRes = boardDao.getBoard(user_idx);
            return getBoardRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> getBoardTotal() throws BaseException{
        try{
            List<GetBoardRes> getBoardTotalRes = boardDao.getBoardTotal();
            return getBoardTotalRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> getBoardDetail(int user_idx, int post_idx) throws BaseException{
        try{
            List<GetBoardRes> getBoardDetailRes = boardDao.getBoardDetail(user_idx, post_idx);
            return getBoardDetailRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBoardRes updateViewCount(int post_idx) throws BaseException {
        try{
            GetBoardRes updateCommentCount = boardDao.updateViewCount(post_idx);
            boardDao.updateAt(post_idx);
            return updateCommentCount;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBoardRes updateCommentCount(int post_idx) throws BaseException {
        try{
            GetBoardRes updateCommentCount = boardDao.updateCommentCount(post_idx);
            boardDao.updateAt(post_idx);
            return updateCommentCount;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBoardRes updateRecommendCount(int post_idx) throws BaseException {
        try{
            GetBoardRes updateRecommendCount = boardDao.updateRecommendCount(post_idx);
            boardDao.updateAt(post_idx);
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
    public void deleteRecommend(PostRecommendReq postRecommendReq) throws BaseException {
        try {
            boardDao.deleteRecommend(postRecommendReq);
        }catch (Exception exception){
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

    public GetCommentRes updateCommentRecommendCount(int comment_idx) throws BaseException {
        try{
            GetCommentRes updateCommentRecommendCount = boardDao.updateCommentRecommendCount(comment_idx);
            return updateCommentRecommendCount;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostCommentRecommendRes postCommentRecommend(PostCommentRecommendReq postCommentRecommendReq) throws BaseException {
        try {
            PostCommentRecommendRes postCommentRecommendRes = boardDao.postCommentRecommend(postCommentRecommendReq);
            return postCommentRecommendRes;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteCommentRecommend(PostCommentRecommendReq postCommentRecommendReq) throws BaseException {
        try {
            boardDao.deleteCommentRecommend(postCommentRecommendReq);
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
