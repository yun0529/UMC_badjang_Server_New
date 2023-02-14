package com.example.demo.src.board;

import com.example.demo.src.board.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class BoardDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 전체게시판 조회
     */

    public List<GetBoardTotal> getBoardTotal(){
        String getBoardResQuery = "SELECT DISTINCT post_category, school_name_idx FROM Board ";

        return this.jdbcTemplate.query(getBoardResQuery,
                (rs, rowNum) -> new GetBoardTotal(
                        rs.getString("post_category"),
                        rs.getInt("school_name_idx")));
    }

    /**게시글 작성
     *
     */
    public List<PostBoardRes> postBoard(PostBoardReq postBoardReq){
        String createBoardQuery = "INSERT INTO Board (user_idx, post_category, post_name, post_content, post_image, post_anonymity, user_name, user_profileimage_url) " +
            "VALUES (?,?,?,?,?,?,(select user_name FROM User where User.user_idx = ?),(select user_profileimage_url FROM User where User.user_idx = ?)) " ;
        Object[] createBoardParams = new Object[]{
                postBoardReq.getUser_idx(), postBoardReq.getPost_category(), postBoardReq.getPost_name(), postBoardReq.getPost_content(),
                postBoardReq.getPost_image(), postBoardReq.getPost_anonymity(), postBoardReq.getUser_idx(), postBoardReq.getUser_idx()
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

    /**게시글 전체 조회
     *
     */
    public List<GetBoardRes> getBoard(int user_idx){
        String getBoardResQuery = "SELECT post_idx, U.user_idx, post_name, post_content, post_image, post_view, post_recommend, post_comment, post_createAt, " +
                "post_updateAt, post_status, post_anonymity, " +
                "post_category, school_name_idx, user_profileimage_url,IF(post_anonymity = 'Y', user_name = null, user_name) as user_name, " +
                "(select exists(select post_idx, user_idx from Bookmark where post_idx = Board.post_idx and U.user_idx = ?)) " +
                "as post_bookmark, " +
                "(select exists(select post_idx, user_idx from Board_Recommend where post_idx = Board.post_idx and U.user_idx = ?)) " +
                "as recommend_status FROM Board " +
                "left join User U on U.user_name= user_name and U.user_profileimage_url = user_profileimage_url " +
                "and U.user_idx = Board.user_idx where post_category = '자유게시판' " ;

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
                        rs.getString("post_anonymity"),
                        rs.getInt("school_name_idx"),
                        rs.getInt("post_bookmark"),
                        rs.getInt("recommend_status"),
                        rs.getString("user_name"),
                        rs.getString("user_profileimage_url")), user_idx, user_idx);
    }

    /**게시글 수정
     *
     */
    public List<GetBoardRes> patchBoard(PatchBoardReq patchBoardReq){
        String createBoardQuery = "UPDATE Board set post_category = ? , post_name = ? , post_content = ? , post_image = ? , " +
                "post_updateAt = CURRENT_TIMESTAMP , post_anonymity = ? where post_idx = ? and user_idx = ?" ;

        Object[] createParams = new Object[]{
                patchBoardReq.getPost_category(), patchBoardReq.getPost_name(), patchBoardReq.getPost_content(),
                patchBoardReq.getPost_image(), patchBoardReq.getPost_idx(), patchBoardReq.getUser_idx(), patchBoardReq.getAnonymity()
        };

        this.jdbcTemplate.update(createBoardQuery, createParams);
        return null;
    }

    /**게시글 삭제
     *
     */
    public List<GetBoardRes> deleteBoard(DeleteBoardReq deleteBoardReq){
        String deleteBoardQuery = "DELETE from Board where post_idx = ? and user_idx = ?" ;

        Object[] deleteParam = new Object[]{
                deleteBoardReq.getPost_idx(), deleteBoardReq.getUser_idx()
        };
        this.jdbcTemplate.update(deleteBoardQuery, deleteParam);
        return null;
    }

    /**게시글 상세 조회 (조회수 증감)
     *
     */
    public GetBoardRes updateViewCount(int post_idx){
        String updateBoardQuery = "UPDATE Board set post_view = post_view + 1 " +
                "where post_idx = ? " ;

        int updateView = post_idx;
        this.jdbcTemplate.update(updateBoardQuery, updateView);
        return null;
    }

    public List<GetBoardRes> getBoardDetail(int user_idx, int post_idx){
        String getBoardQuery = "SELECT post_idx, U.user_idx, post_name, post_content, post_image, post_view, post_recommend, post_comment, post_createAt, " +
                "post_updateAt, post_status, post_anonymity, " +
                "post_category, school_name_idx, user_profileimage_url,IF(post_anonymity = 'Y', user_name = null, user_name) as user_name, " +
                "(select exists(select post_idx, user_idx from Bookmark where post_idx = Board.post_idx and U.user_idx = ?)) " +
                "as post_bookmark, " +
                "(select exists(select post_idx, user_idx from Board_Recommend where post_idx = Board.post_idx and U.user_idx = ?)) " +
                "as recommend_status FROM Board " +
                "left join User U on U.user_name= user_name and U.user_profileimage_url = user_profileimage_url " +
                "and U.user_idx = Board.user_idx where post_category = '자유게시판' " ;


        return this.jdbcTemplate.query(getBoardQuery, (rs, rowNum) -> new GetBoardRes(
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
                rs.getString("post_anonymity"),
                rs.getInt("school_name_idx"),
                rs.getInt("post_bookmark"),
                rs.getInt("recommend_status"),
                rs.getString("user_name"),
                rs.getString("user_profileimage_url")),user_idx, post_idx);
    }

    /**게시글 상세조회(댓글수 증감)
     *
     */
    public GetBoardRes updateCommentCount(int post_idx){
        String updateCommentQuery = "UPDATE Board set post_comment = (select count(Comment.post_idx) " +
                "from Comment where Comment.post_idx = Board.post_idx) " +
                "where Board.post_idx = ? " ;

        int updateCommentCount = post_idx;
        this.jdbcTemplate.update(updateCommentQuery, updateCommentCount);
        return null;
    }

    public GetBoardRes updateAt (int post_idx){
        String updateCommentQuery = "UPDATE Board set post_updateAt = CURRENT_TIMESTAMP " +
                "where post_idx = ? " ;

        int updateCommentCount = post_idx;
        this.jdbcTemplate.update(updateCommentQuery, updateCommentCount);
        return null;
    }

    /**게시글 상세조회(추천 증감)
     *
     */
    public GetBoardRes updateRecommendCount(int post_idx){
        String updateCommentQuery = "UPDATE Board set post_recommend = (select count(Board_Recommend.post_idx) " +
                "from Board_Recommend where Board_Recommend.post_idx = Board.post_idx) " +
                "where Board.post_idx = ? " ;

        int updateRecommendCount = post_idx;
        this.jdbcTemplate.update(updateCommentQuery, updateRecommendCount);
        return null;
    }

    /**게시글 상세조회(추천 등록)
     *
     */
    public PostRecommendRes postRecommend(PostRecommendReq postRecommendReq){
        String createRecommendQuery = "INSERT INTO Board_Recommend (user_idx, post_idx)" +
                "VALUES (?, ?) " ;

        Object[] createParams = new Object[]{
                postRecommendReq.getUser_idx(), postRecommendReq.getPost_idx()
        };

        this.jdbcTemplate.update(createRecommendQuery, createParams);
        return null;
    }

    /**추천 취소
     *
     */
    public void deleteRecommend(PostRecommendReq postRecommendReq) {
        String deleteRecommend = "DELETE FROM Board_Recommend where post_idx = ? and user_idx = ? " ;

        Object[] deleteParams = new Object[]{
                postRecommendReq.getPost_idx(), postRecommendReq.getUser_idx()
        };
        this.jdbcTemplate.update(deleteRecommend, deleteParams);
    }

    /**추천 수 중복 여부 체크
     *
     */
    public int checkRecommend (PostRecommendReq postRecommendReq){
        String checkRecommend = "SELECT COUNT(*) FROM Board_Recommend WHERE post_idx = ? AND user_idx = ?" ;

        return this.jdbcTemplate.queryForObject(checkRecommend, int.class,
                postRecommendReq.getPost_idx(), postRecommendReq.getUser_idx());
    }

    /**게시글 댓글 조회
     *
     */
    public List<GetCommentRes> getComment(int post_idx, int user_idx){
        String getCommentQuery = "SELECT comment_idx, User.user_idx, Board.post_idx, comment_content, comment_recommend, " +
                "comment_anonymity, comment_createAt, comment_updatedAt, comment_status, " +
                "IF(comment_anonymity = 'Y', user_name = null, user_name) as user_name, user_profileimage_url, " +
                "(select exists(select comment_idx, user_idx from Comment_Recommend where comment_idx = Comment.comment_idx and User.user_idx = ?)) " +
                "as recommend_status FROM badjangDB.Comment " +
                "left join User on User.user_idx = Comment.user_idx and User.user_profileimage_url = user_profileimage_url " +
                "left join Board on Board.post_idx = Comment.post_idx " +
                "where Board.post_idx = ? and Board.post_category = '자유게시판' " ;

        return this.jdbcTemplate.query(getCommentQuery,
                (rs, rowNum) -> new GetCommentRes(
                        rs.getInt("comment_idx"),
                        rs.getInt("user_idx"),
                        rs.getInt("post_idx"),
                        rs.getString("comment_content"),
                        rs.getInt("comment_recommend"),
                        rs.getString("comment_anonymity"),
                        rs.getString("comment_createAt"),
                        rs.getString("comment_updatedAt"),
                        rs.getString("comment_status"),
                        rs.getString("user_name"),
                        rs.getString("user_profileimage_url"),
                        rs.getInt("recommend_status")
                ),user_idx, post_idx);
    }

    /**게시글 댓글 작성
     *
     */
    public PostCommentRes postComment(PostCommentReq postCommentReq){
        String createCommentQuery = "INSERT INTO Comment (post_idx ,user_idx, comment_content, comment_anonymity, user_name, user_profileimage_url) " +
                "VALUES (?, ?, ?, ?,(select user_name FROM User where User.user_idx = ?),(select user_profileimage_url FROM User where User.user_idx = ?)) " ;

        Object[] createCommentParams = new Object[]{
                postCommentReq.getPost_idx(), postCommentReq.getUser_idx(), postCommentReq.getComment_content(),
                postCommentReq.getComment_anonymity(), postCommentReq.getUser_idx(), postCommentReq.getUser_idx()
        };

        this.jdbcTemplate.update(createCommentQuery,createCommentParams);

        String lastInsertIdQuery = "select comment_idx, post_idx, comment_content, comment_createAt " +
                "from badjangDB.Comment order by comment_idx desc limit 1 ";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,(rs, rowNum) -> new PostCommentRes(
                rs.getInt("comment_idx"),
                rs.getInt("post_idx"),
                rs.getString("comment_content"),
                rs.getString("comment_createAt")
        ));
    }

    /**댓글 수정
     *
     */
    public GetCommentRes patchComment(PatchCommentReq patchCommentReq){
        String updateQuery = "UPDATE Comment set comment_content = ? , " +
                "comment_updatedAt = CURRENT_TIMESTAMP , anonymity = ? where comment_idx = ? " ;

        Object[] updateParams = new Object[]{
                patchCommentReq.getComment_content(), patchCommentReq.getComment_idx(), patchCommentReq.getAnonymity()
        };

        this.jdbcTemplate.update(updateQuery, updateParams);
        String updateResultQuery = "select comment_idx, comment_content, comment_updatedAt , comment_anonymity" +
                "from badjangDB.Comment where comment_idx = ? " ;

        Object[] resultParams = new Object[]{
                patchCommentReq.getComment_idx()
        };

        return this.jdbcTemplate.queryForObject(updateResultQuery, (rs, rowNum) -> new GetCommentRes(
                rs.getInt("comment_idx"),
                rs.getString("comment_content"),
                rs.getString("comment_updatedAt")
        ),resultParams);
    }

    /**댓글 삭제
     *
     */
    public GetCommentRes deleteComment(DeleteCommentReq deleteCommentReq){
        String deleteQuery = "DELETE from Comment where comment_idx = ? ";

        Object[] deleteResult = new Object[]{
                deleteCommentReq.getComment_idx()
        };
        this.jdbcTemplate.update(deleteQuery,deleteResult);
        return null;
    }

    /**댓글 추천
     *
     */
    public PostCommentRecommendRes postCommentRecommend(PostCommentRecommendReq postCommentRecommendReq) {
        String createRecommendQuery = "INSERT INTO Comment_Recommend (comment_idx, user_idx)" +
                "VALUES (?, ?) " ;

        Object[] createParams = new Object[]{
                postCommentRecommendReq.getComment_idx(), postCommentRecommendReq.getUser_idx()
        };

        this.jdbcTemplate.update(createRecommendQuery, createParams);
        return null;
    }

    /**댓글 추천수
     */
    public GetCommentRes updateCommentRecommendCount(int comment_idx) {
        String updateCommentQuery = "UPDATE Comment set comment_updatedAt = CURRENT_TIMESTAMP, " +
                "comment_recommend = (select count(Comment_Recommend.comment_idx) " +
                "from Comment_Recommend where Comment_Recommend.comment_idx = Comment.comment_idx) " +
                "where Comment.comment_idx = ? " ;

        int updateRecommendCount = comment_idx;
        this.jdbcTemplate.update(updateCommentQuery, updateRecommendCount);
        return null;
    }

    /**댓글 추천 취소
     *
     */
    public void deleteCommentRecommend(PostCommentRecommendReq postCommentRecommendReq) {
        String deleteRecommend = "DELETE FROM Comment_Recommend where comment_idx = ? and user_idx = ? " ;

        Object[] deleteParams = new Object[]{
                postCommentRecommendReq.getComment_idx(), postCommentRecommendReq.getUser_idx()
        };
        this.jdbcTemplate.update(deleteRecommend, deleteParams);
    }

    /**댓글 추천 수 중복 여부 체크
     *
     */
    public int checkCommentRecommend (PostCommentRecommendReq postCommentRecommendReq){
        String checkRecommend = "SELECT COUNT(*) FROM Comment_Recommend WHERE comment_idx = ? AND user_idx = ?" ;

        return this.jdbcTemplate.queryForObject(checkRecommend, int.class,
                postCommentRecommendReq.getComment_idx(), postCommentRecommendReq.getUser_idx());
    }
}
