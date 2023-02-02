package com.example.demo.src.board.school;

import com.example.demo.src.board.school.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SchoolBoardCommentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetSchoolBoardCommentRes> getSchoolBoardComment(int postIdx) {


        String getSchoolBoardCommentQuery = "select Board.post_idx, Comment.user_idx, User.user_name, comment_content, " +
                "comment_recommend, comment_anonymity, comment_createAt " +
                "from Board " +
                "join Comment " +
                "on Board.post_idx = Comment.post_idx " +
                "join User " +
                "on User.user_idx = Comment.user_idx " +
                "where Board.post_idx = ? " +
                "order by Comment.comment_createAt desc ";

        int getSchoolBoardCommentParams = postIdx;

        return this.jdbcTemplate.query(getSchoolBoardCommentQuery,
                (rs, rowNum) -> new GetSchoolBoardCommentRes(
                        rs.getInt("post_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("user_name"),
                        rs.getString("comment_content"),
                        rs.getInt("comment_recommend"),
                        rs.getString("comment_anonymity"),
                        rs.getString("comment_createAt")
                ), getSchoolBoardCommentParams
        );

    }



    public void postSchoolBoardComment(int userIdx, int postIdx, PostSchoolBoardCommentReq postSchoolBoardCommentReq) {

        String postSchoolBoardCommentQuery = "insert into Comment (" +
                "user_idx, " +
                "post_idx, " +
                "comment_content, " +
                "comment_anonymity) " +
                "VALUES (?,?,?,?)";

        Object[] postSchoolBoardCommentParams = new Object[]{
                userIdx,
                postIdx,
                postSchoolBoardCommentReq.getComment_content(),
                postSchoolBoardCommentReq.getComment_anonymity()
        };

        this.jdbcTemplate.update(postSchoolBoardCommentQuery, postSchoolBoardCommentParams);


    }

    public void schoolBoardCommentUpdate(int postIdx) {

        String schoolBoardRecommendUpQuery = "UPDATE Board " +
                "set post_comment = " +
                "( select COUNT(comment_idx) " +
                "from Comment " +
                "where post_idx = ? ) " +
                "where post_idx = ? ";

        int schoolBoardRecommendUpParams = postIdx;

        this.jdbcTemplate.update(schoolBoardRecommendUpQuery, schoolBoardRecommendUpParams, schoolBoardRecommendUpParams);

    }

    public int checkSchoolBoardCommentWriter(int userIdx, int commentIdx) {

        String checkSchoolBoardCommentWriterQuery = "select Exists( " +
                "select post_idx " +
                "from Comment " +
                "where user_idx = ? " +
                "and comment_idx = ? " +
                ") as isChk ";

        Object[] checkSchoolBoardCommentWriterParams = new Object[]{
                userIdx,
                commentIdx};

        return this.jdbcTemplate.queryForObject(checkSchoolBoardCommentWriterQuery, int.class, checkSchoolBoardCommentWriterParams);


    }

    public void patchSchoolBoardComment(int commentIdx, PatchSchoolBoardCommentReq patchSchoolBoardCommentReq) {
        String patchSchoolBoardCommentQuery;
        Object[] patchSchoolBoardCommentParams;

        if (patchSchoolBoardCommentReq.getComment_content() != null) {
            patchSchoolBoardCommentQuery = "update Comment " +
                    "set comment_content = ? " +
                    "where comment_idx = ?";

            patchSchoolBoardCommentParams = new Object[]{
                    patchSchoolBoardCommentReq.getComment_content(),
                    commentIdx
            };

            this.jdbcTemplate.update(patchSchoolBoardCommentQuery, patchSchoolBoardCommentParams);
        }

        if (patchSchoolBoardCommentReq.getComment_anonymity() != null) {
            patchSchoolBoardCommentQuery = "update Comment " +
                    "set comment_anonymity = ? " +
                    "where comment_idx = ?";

            patchSchoolBoardCommentParams = new Object[]{
                    patchSchoolBoardCommentReq.getComment_anonymity(),
                    commentIdx
            };
            this.jdbcTemplate.update(patchSchoolBoardCommentQuery, patchSchoolBoardCommentParams);
        }




    }

    public void deleteSchoolBoardComment(int userIdx, int commentIdx) {

        String deleteSchoolBoardCommentQuery = "delete from Comment " +
                "where user_idx = ? " +
                "and comment_idx = ? ";

        Object[] deleteSchoolBoardCommentParams = new Object[]{
                userIdx,
                commentIdx
        };

        this.jdbcTemplate.update(deleteSchoolBoardCommentQuery, deleteSchoolBoardCommentParams);


    }


    public int checkSchoolBoardCommentRecommendDouble(int userIdx, int commentIdx) {

        String checkSchoolBoardCommentRecommendDoubleQuery = "select Exists( " +
                "select comment_recommend_idx from Comment_Recommend " +
                "where user_idx = ? " +
                "and comment_idx = ? " +
                ") as isChk ";

        Object[] checkSchoolBoardCommentRecommendDoubleParams = new Object[]{userIdx, commentIdx};

        return this.jdbcTemplate.queryForObject(checkSchoolBoardCommentRecommendDoubleQuery,
                int.class,
                checkSchoolBoardCommentRecommendDoubleParams);

    }

    public void schoolBoardCommentRecommendUpdate(int commentIdx) {

        String schoolBoardCommentRecommendUpdateQuery = "UPDATE Comment " +
                "set comment_recommend = " +
                "( select COUNT(comment_recommend_idx) " +
                "from Comment_Recommend " +
                "where comment_idx = ? ) " +
                "where comment_idx = ? ";

        int schoolBoardCommentRecommendUpdateParams = commentIdx;

        this.jdbcTemplate.update(schoolBoardCommentRecommendUpdateQuery, schoolBoardCommentRecommendUpdateParams, schoolBoardCommentRecommendUpdateParams);

    }

    public String postSchoolBoardCommentRecommend(int userIdx, int commentIdx) {
        int checkSchoolBoardCommentRecommendDouble = checkSchoolBoardCommentRecommendDouble(userIdx, commentIdx);

        String postSchoolBoardCommentRecommendQuery;
        Object[] postSchoolBoardCommentRecommendParams = new Object[]{
                userIdx,
                commentIdx
        };

        if (checkSchoolBoardCommentRecommendDouble == 1) {

            postSchoolBoardCommentRecommendQuery = "delete from Comment_Recommend " +
                    "where user_idx = ? " +
                    "and comment_idx = ? ";

            this.jdbcTemplate.update(postSchoolBoardCommentRecommendQuery, postSchoolBoardCommentRecommendParams);
            return "삭제";

        } else {
            postSchoolBoardCommentRecommendQuery = "insert into Comment_Recommend (" +
                    "user_idx, " +
                    "comment_idx ) " +
                    "VALUES (?,?)";

            this.jdbcTemplate.update(postSchoolBoardCommentRecommendQuery, postSchoolBoardCommentRecommendParams);

            return "추가";
        }

    }




}
