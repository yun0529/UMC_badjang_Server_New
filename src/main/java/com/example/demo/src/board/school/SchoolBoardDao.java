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

    //학교 게시판 종류 조회
    public List<GetSchoolBoardNameRes> getSchoolBoardName() {


        String getSchoolBoardNameQuery = "select * " +
                "from School_Board_Name ";


        return this.jdbcTemplate.query(getSchoolBoardNameQuery,
                (rs, rowNum) -> new GetSchoolBoardNameRes(
                        rs.getInt("school_name_idx"),
                        rs.getString("post_school_name")
                )
        );

    }

    //학교게시판에서의 전체 게시글 불러오기
    public List<GetSchoolBoardRes> getSchoolBoard(int userIdx, int schoolNameIdx) {


        String getSchoolBoardResQuery = "select post_idx, Board.user_idx, user_name, user_profileimage_url, post_name, post_content, post_image, " +
                "post_view, post_recommend, post_comment, post_anonymity, post_category, post_school_name, post_createAt, " +
                "Exists( select post_recommend_idx from Board_Recommend where user_idx = ? and Board_Recommend.post_idx = Board.post_idx ) as isRecommendChk " +
                "from Board " +
                "join User " +
                "on Board.user_idx = User.user_idx " +
                "join School_Board_Name " +
                "on School_Board_Name.school_name_idx = Board.school_name_idx " +
                "where School_Board_Name.school_name_idx = ? ";

        Object[] getSchoolBoardResParams = new Object[]{
                userIdx,
                schoolNameIdx};

        return this.jdbcTemplate.query(getSchoolBoardResQuery,
                (rs, rowNum) -> new GetSchoolBoardRes(
                        rs.getInt("post_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("user_name"),
                        rs.getString("user_profileimage_url"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_anonymity"),
                        rs.getString("post_category"),
                        rs.getString("post_school_name"),
                        rs.getString("post_createAt"),
                        rs.getInt("isRecommendChk")
                ), userIdx, schoolNameIdx
        );

    }

    //게시글 접근 시 자동으로 게시물의 조회수를 올리기 위한 쿼리
    public void updateView(int postIdx) {
        String updateViewQuery = "update Board " +
                "set post_view = post_view + 1 " +
                "where post_idx = ? ";

        int updateViewParams = postIdx;

        this.jdbcTemplate.update(updateViewQuery, updateViewParams);
    }

    //학교게시판에 있는 게시물 중 하나의 게시물 불러오기
    public List<GetOneOfSchoolBoardRes> getOneOfSchoolBoardRes(int userIdx, int postIdx) {


        String getOneOfSchoolBoardResQuery = "select Board.user_idx, user_name, user_profileimage_url, post_name, post_content, post_image, " +
                "post_view, post_recommend, post_comment, post_anonymity, post_category, post_school_name, post_createAt, " +
                "Exists( select bookmark_idx from Bookmark where user_idx = ? and post_idx = ? ) as isBookmarkChk, " +
                "Exists( select post_recommend_idx from Board_Recommend where user_idx = ? and post_idx = ? ) as isRecommendChk " +
                "from Board " +
                "join User " +
                "on Board.user_idx = User.user_idx " +
                "join School_Board_Name " +
                "on School_Board_Name.school_name_idx = Board.school_name_idx " +
                "where post_idx = ? ";

        Object[] getOneOfSchoolBoardResParams = new Object[]{
                userIdx, postIdx, userIdx, postIdx, postIdx};

        return this.jdbcTemplate.query(getOneOfSchoolBoardResQuery,
                (rs, rowNum) -> new GetOneOfSchoolBoardRes(
                        rs.getInt("user_idx"),
                        rs.getString("user_name"),
                        rs.getString("user_profileimage_url"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_anonymity"),
                        rs.getString("post_category"),
                        rs.getString("post_school_name"),
                        rs.getString("post_createAt"),
                        rs.getInt("isBookmarkChk"),
                        rs.getInt("isRecommendChk")
                ), getOneOfSchoolBoardResParams
        );

    }

    //학교게시판에 게시물 추가
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


    //게시글 작성자인지 확인하는 쿼리
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

    //게시물 수정
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

    //게시물 삭제
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

    //해당 게시물에 추천이 이미 되어있는지 확인
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

    //게시물의 추천수를 확인하기 위한 쿼리
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

        //이미 추천이 되어있는 경우에는 취소하고(DB 내역에서 삭제), 추천이 되어있지 않은 경우에는 추천(DB 내역에 추가)하는 쿼리
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
