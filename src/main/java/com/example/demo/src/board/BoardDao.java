package com.example.demo.src.board;

import com.example.demo.src.board.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Repository
public class BoardDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //게시글 작성
    public List<PostBoardRes> postBoard(PostBoardReq postBoardReq){
        String createBoardQuery = "insert into Board (user_idx, post_category, post_name, post_content, post_image, post_view, " +
                "                   post_recommend, post_comment, post_createAt, post_updateAt, post_status, post_anonymity) " +
                "VALUES (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?,?)" ;

        Object[] createBoardParams = new Object[]{
                postBoardReq.getUser_idx(), postBoardReq.getPost_category(), postBoardReq.getPost_name(), postBoardReq.getPost_content(),
                postBoardReq.getPost_image(), postBoardReq.getPost_view(), postBoardReq.getPost_recommend(),
                postBoardReq.getPost_comment(), postBoardReq.getPost_status(), postBoardReq.getPost_anonymity()
        };
        this.jdbcTemplate.update(createBoardQuery, createBoardParams);

        String lastInsertIdQuery = "select post_idx, user_idx, post_category, post_content, post_createAt " +
                "from badjangDB.Board";

        return this.jdbcTemplate.query(lastInsertIdQuery,(rs, rowNum) -> new PostBoardRes(
                rs.getInt("post_idx"),
                rs.getString("post_category"),
                rs.getInt("user_idx"),
                rs.getString("post_content"),
                rs.getString("post_createAt")
        ));
    }

    //게시글 전체 조회
    public List<GetBoardRes> getBoard(){
        String getBoardResQuery = "SELECT post_idx, Board.user_idx, post_category, post_name, post_content, post_image, post_view, post_recommend, post_comment, " +
                "post_createAt, post_updateAt, post_status, post_anonymity " +
                "from badjangDB.Board " +
                "join User on User.user_idx = Board.user_idx " ;

        return this.jdbcTemplate.query(getBoardResQuery,
                (rs, rowNum) -> new GetBoardRes(
                        rs.getInt("post_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("post_category"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_createAt"),
                        rs.getString("post_updateAt"),
                        rs.getString("post_status"),
                        rs.getString("post_anonymity")));
    }

    //게시글 수정
    public List<PatchBoardRes> patchBoard(PatchBoardReq patchBoardReq){
        String createBoardQuery = "UPDATE Board set post_category = ? , post_name = ? , post_content = ? , post_image = ? , " +
                "post_updateAt = CURRENT_TIMESTAMP where post_idx = ? " ;

        Object[] createParams = new Object[]{
                patchBoardReq.getPost_category(), patchBoardReq.getPost_name(), patchBoardReq.getPost_content(),
                patchBoardReq.getPost_image(), patchBoardReq.getPost_idx()
        };

        this.jdbcTemplate.update(createBoardQuery, createParams);
        return null;
    }

    //게시글 삭제
    public List<DeleteBoardRes> deleteBoard(DeleteBoardReq deleteBoardReq){
        String deleteBoardQuery = "DELETE from Board where post_idx = ?" ;

        Object[] deleteParam = new Object[]{
                deleteBoardReq.getPost_idx()
        };
        this.jdbcTemplate.update(deleteBoardQuery, deleteParam);
        return null;
    }

    //게시글 상세 조회 (조회수 증감)
    public List<GetBoardRes> getBoardDetail(int post_idx){
        String getBoardQuery = "UPDATE Board set post_view = post_view + 1 " +
                "where post_idx = ? " ;

        int updateView = post_idx;
        this.jdbcTemplate.update(getBoardQuery, updateView);
        return null;
    }

    //게시글 상세조회(댓글수 증감)
    public List<GetBoardRes> updateCommentCount(int post_idx){
        String updateCommentQuery = "UPDATE Board set post_comment = (select count(Comment.post_idx) " +
                "from Comment where Comment.post_idx = Board.post_idx) " +
                "where Board.post_idx = ? " ;

        int updateCommentCount = post_idx;
        this.jdbcTemplate.update(updateCommentQuery, updateCommentCount);

        return null;
    }

    //게시글 상세조회(추천수 증감)

    /**
     * 댓글 관련 시작부분
     */
//게시글 댓글 조회
    public List<GetCommentRes> getComment(int post_idx){
        String getCommentQuery = "SELECT comment_idx, User.user_idx, Board.post_idx, comment_content, comment_recommend, " +
                "comment_anonymity, comment_createAt, comment_updatedAt, comment_status " +
                "from badjangDB.Comment " +
                "left join User on User.user_idx = Comment.user_idx " +
                "left join Board on Board.post_idx = Comment.post_idx " +
                "where Board.post_idx = ? " ;

        int getPostIdx = post_idx;

        return this.jdbcTemplate.query(getCommentQuery,
                (rs, rowNum) -> new GetCommentRes(
                        rs.getInt("comment_idx"),
                        rs.getInt("user_idx"),
                        rs.getInt("post_idx"),
                        rs.getString("comment_content"),
                        rs.getString("comment_recommend"),
                        rs.getString("comment_anonymity"),
                        rs.getString("comment_createAt"),
                        rs.getString("comment_updatedAt"),
                        rs.getString("comment_status")
                ),getPostIdx);
    }

}
