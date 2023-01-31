package com.example.demo.src.mypage;

import com.example.demo.src.mypage.model.GetMypageRes;
import com.example.demo.src.mypage.model.PatchUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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

}
