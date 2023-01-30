package com.example.demo.src.board.school;

import com.example.demo.src.board.school.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SchoolBoardDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetSchoolBoardRes> getSchoolBoard(int schoolNameIdx) {


        String getSchoolBoardResQuery = "select * " +
                "from Board " +
                "left join School_Board_Name " +
                "on School_Board_Name.school_name_idx = Board.school_name_idx " +
                "where School_Board_Name.school_name_idx = ? ";

        int getSchoolBoardParams = schoolNameIdx;

        return this.jdbcTemplate.query(getSchoolBoardResQuery,
                (rs, rowNum) -> new GetSchoolBoardRes(
                        rs.getInt("post_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_anonymity"),
                        rs.getString("post_category"),
                        rs.getString("post_school_name"),
                        rs.getString("post_createAt")
                ), getSchoolBoardParams
        );

    }

    public void updateView(int postIdx) {
        String updateViewQuery = "update Board " +
                "set post_view = post_view + 1 " +
                "where post_idx = ? ";

        int updateViewParams = postIdx;

        this.jdbcTemplate.update(updateViewQuery, updateViewParams);
    }

    public List<GetOneOfSchoolBoardRes> getOneOfSchoolBoardRes(int postIdx) {


        String getOneOfSchoolBoardResQuery = "select Board.user_idx, user_name, post_name, post_content, post_image, " +
                "post_view, post_recommend, post_comment, post_anonymity, post_category, post_school_name, post_createAt " +
                "from Board " +
                "join User " +
                "on Board.user_idx = User.user_idx " +
                "join School_Board_Name " +
                "on School_Board_Name.school_name_idx = Board.school_name_idx " +
                "where post_idx = ? ";

        int getOneOfSchoolBoardResParams = postIdx;

        return this.jdbcTemplate.query(getOneOfSchoolBoardResQuery,
                (rs, rowNum) -> new GetOneOfSchoolBoardRes(
                        rs.getInt("user_idx"),
                        rs.getString("user_name"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_anonymity"),
                        rs.getString("post_category"),
                        rs.getString("post_school_name")
                ), getOneOfSchoolBoardResParams
        );

    }


    public void postSchoolBoard(int userIdx, int schoolNameIdx, PostSchoolBoardReq postSchoolBoardReq) {

        String postSchoolBoardQuery = "insert into Board (" +
                "user_idx, " +
                "post_name, " +
                "post_content, " +
                "post_image, " +
                "post_category, " +
                "post_anonymity, " +
                "school_name_idx )" +
                "VALUES (?,?,?,?,?,?,?)";

        Object[] postSchoolBoardParams = new Object[]{
                userIdx,
                postSchoolBoardReq.getPost_name(),
                postSchoolBoardReq.getPost_content(),
                postSchoolBoardReq.getPost_image(),
                "학교게시판",
                postSchoolBoardReq.getPost_anonymity(),
                schoolNameIdx};

        this.jdbcTemplate.update(postSchoolBoardQuery, postSchoolBoardParams);


    }



    public int checkSchoolBoardWriter(int userIdx, int postIdx) {
        String checkSchoolBoardWriterQuery = "select Exists( " +
                "select post_idx " +
                "from Board " +
                "where user_idx = ? " +
                "and post_idx = ? " +
                ") as isChk ";

        Object[] checkSchoolBoardWriterParams = new Object[]{
                userIdx,
                postIdx};

        return this.jdbcTemplate.queryForObject(checkSchoolBoardWriterQuery, int.class, checkSchoolBoardWriterParams);


    }

    public void patchSchoolBoard(int postIdx, PatchSchoolBoardReq patchSchoolBoardReq) {


        String patchSchoolBoardQuery;


        if (patchSchoolBoardReq.getPost_name() != null) {
            patchSchoolBoardQuery = "update Board " +
                    "set post_name = ? " +
                    "where post_idx = ?";

            Object[] patchSchoolBoardParams = new Object[]{
                    patchSchoolBoardReq.getPost_name(),
                    postIdx
            };
            this.jdbcTemplate.update(patchSchoolBoardQuery, patchSchoolBoardParams);
        }

        if (patchSchoolBoardReq.getPost_content() != null) {
            patchSchoolBoardQuery = "update Board " +
                    "set post_content = ? " +
                    "where post_idx = ?";

            Object[] patchSchoolBoardParams = new Object[]{
                    patchSchoolBoardReq.getPost_content(),
                    postIdx
            };
            this.jdbcTemplate.update(patchSchoolBoardQuery, patchSchoolBoardParams);
        }

        if (patchSchoolBoardReq.getPost_image() != null) {
            patchSchoolBoardQuery = "update Board " +
                    "set post_image = ? " +
                    "where post_idx = ?";

            Object[] patchSchoolBoardParams = new Object[]{
                    patchSchoolBoardReq.getPost_image(),
                    postIdx
            };
            this.jdbcTemplate.update(patchSchoolBoardQuery, patchSchoolBoardParams);
        }

        if (patchSchoolBoardReq.getPost_anonymity() != null) {
            patchSchoolBoardQuery = "update Board " +
                    "set post_anonymity = ? " +
                    "where post_idx = ?";

            Object[] patchSchoolBoardParams = new Object[]{
                    patchSchoolBoardReq.getPost_anonymity(),
                    postIdx
            };
            this.jdbcTemplate.update(patchSchoolBoardQuery, patchSchoolBoardParams);
        }

    }

    public void deleteSchoolBoard(int userIdx, int postIdx) {

        String postSchoolBoardQuery = "delete from Board " +
                "where user_idx = ? " +
                "and post_idx = ? ";

        Object[] postSchoolBoardParams = new Object[]{
                userIdx,
                postIdx
        };

        this.jdbcTemplate.update(postSchoolBoardQuery, postSchoolBoardParams);


    }


    public int checkSchoolBoardRecommendDouble(int userIdx, int postIdx) {

        String checkSchoolBoardRecommendDoubleQuery = "select Exists( " +
                "select post_recommend_idx from Board_Recommend " +
                "where user_idx = ? " +
                "and post_idx = ? " +
                ") as isChk ";

        Object[] checkSchoolBoardRecommendDoubleParams = new Object[]{userIdx, postIdx};

        return this.jdbcTemplate.queryForObject(checkSchoolBoardRecommendDoubleQuery,
                int.class,
                checkSchoolBoardRecommendDoubleParams);

    }

    public void schoolBoardRecommendUpdate(int postIdx) {

        String schoolBoardRecommendUpQuery = "UPDATE Board " +
                "set post_recommend = " +
                "( select COUNT(post_recommend_idx) " +
                "from Board_Recommend " +
                "where post_idx = ? ) " +
                "where post_idx = ? ";

        int schoolBoardRecommendUpParams = postIdx;

        this.jdbcTemplate.update(schoolBoardRecommendUpQuery, schoolBoardRecommendUpParams, schoolBoardRecommendUpParams);

    }

    public String postSchoolBoardRecommend(int userIdx, int postIdx) {
        int checkSchoolBoardRecommendDouble = checkSchoolBoardRecommendDouble(userIdx, postIdx);

        String postSchoolBoardRecommendQuery;
        Object[] postSchoolBoardRecommendParams = new Object[]{
                userIdx,
                postIdx
        };

        if (checkSchoolBoardRecommendDouble == 1) {

            postSchoolBoardRecommendQuery = "delete from Board_Recommend " +
                    "where user_idx = ? " +
                    "and post_idx = ? ";

            this.jdbcTemplate.update(postSchoolBoardRecommendQuery, postSchoolBoardRecommendParams);
            return "삭제";

        } else {
            postSchoolBoardRecommendQuery = "insert into Board_Recommend (" +
                    "user_idx, " +
                    "post_idx ) " +
                    "VALUES (?,?)";

            this.jdbcTemplate.update(postSchoolBoardRecommendQuery, postSchoolBoardRecommendParams);

            return "추가";
        }

    }




}
