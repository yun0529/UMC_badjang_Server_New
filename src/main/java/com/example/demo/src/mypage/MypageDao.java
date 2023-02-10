package com.example.demo.src.mypage;

import com.example.demo.src.mypage.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MypageDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetMypageRes getMypage(int user_idx) {
        String getUserQuery = "select user_name, user_profileimage_url from User where user_idx = ?"; // 해당 user_id를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = user_idx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetMypageRes(
                        rs.getString("user_name"),
                        rs.getString("user_profileimage_url")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public int modifyUserProfile(int user_idx, PatchUserReq patchUserReq) {
        String modifyUserProfileQuery = "update User set user_name = ?, user_profileimage_url = ? where user_idx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserProfileParams = new Object[]{patchUserReq.getUser_name(), patchUserReq.getUser_profileimage_url(), user_idx}; // 주입될 값들(nickname, userIdx) 순
        //user_idx를 object에 주입해야하나??, x-access token 사용한이유가 현재 사용자 정보를 얻기 위해서 인가?왜 uri에 user_idx를 바로 사용하지 않는가?
        return this.jdbcTemplate.update(modifyUserProfileQuery, modifyUserProfileParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    //내가 작성한 게시글 조회
    public List<GetBoardRes> getBoard(int user_idx){
        String getBoardResQuery = "select Board.*, User.* from Board join User on Board.user_idx = User.user_idx where Board.user_idx = ? order by post_view desc";
        int getBoardResParams = user_idx;
        return this.jdbcTemplate.query(getBoardResQuery,
                (rs, rowNum) -> new GetBoardRes(
                        rs.getString("user_name"),
                        rs.getString("user_profileimage_url"),
                        rs.getString("post_createAt"),
                        rs.getInt("post_idx"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_category"),
                        rs.getString("post_anonymity")),
                getBoardResParams);
    }

    public List<GetCommentRes> getComment(int user_idx){
        String getCommentResQuery = "select Comment.*, Board.* from Comment join Board on Comment.post_idx = Board.post_idx where Comment.user_idx = ?";
        int getCommentResParams = user_idx;
        return this.jdbcTemplate.query(getCommentResQuery,
                (rs, rowNum) -> new GetCommentRes(
                        rs.getInt("comment_idx"),
                        rs.getInt("post_idx"),
                        rs.getString("post_name"),
                        rs.getString("post_category"),
                        rs.getString("comment_content")),
                getCommentResParams);
    }

    //학교 및 지역 변경
    public int saveUserUnivInfo(int user_idx, PatchInfoReq patchInfoReq) {
        String saveUserUnivInfoQuery = "update User set user_univ = ?, " +
                "user_college = ?, user_department = ?, user_grade = ?," +
                " user_semester = ?, user_province = ?, user_city = ?" +
                " Where user_idx = ? ";
        Object[] saveUserUnivInfoParams = new Object[]{patchInfoReq.getUser_univ(),
                patchInfoReq.getUser_college(), patchInfoReq.getUser_department(), patchInfoReq.getUser_grade(),
                patchInfoReq.getUser_semester(), patchInfoReq.getUser_province(), patchInfoReq.getUser_city(),
                user_idx};
        return this.jdbcTemplate.update(saveUserUnivInfoQuery, saveUserUnivInfoParams);
    }

    public List<GetNoticeRes> getNotice(){
        String getNoticeResQuery = "select * from Notice";
        //int getNoticeResParams = user_idx;
        return this.jdbcTemplate.query(getNoticeResQuery,
                (rs, rowNum) -> new GetNoticeRes(
                        rs.getInt("notice_idx"),
                        rs.getString("notice_title"),
                        rs.getString("notice_content"),
                        rs.getString("notice_image"),
                        rs.getString("notice_createAt"),
                        rs.getString("notice_updateAt"))
        );
    }

    public GetNoticeRes getNoticeDetail(int notice_idx){
        String getNoticeResQuery = "select * from Notice where notice_idx = ?";
        int getNoticeResParams = notice_idx;
        return this.jdbcTemplate.queryForObject(getNoticeResQuery,
                (rs, rowNum) -> new GetNoticeRes(
                        rs.getInt("notice_idx"),
                        rs.getString("notice_title"),
                        rs.getString("notice_content"),
                        rs.getString("notice_image"),
                        rs.getString("notice_createAt"),
                        rs.getString("notice_updateAt")),
                getNoticeResParams);
    }



}
