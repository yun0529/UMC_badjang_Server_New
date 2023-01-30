package com.example.demo.src.user;


import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostExtraReq;
import com.example.demo.src.user.model.PostInfoReq;
import com.example.demo.src.user.model.PostUserReq;

import com.example.demo.src.user.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "SELECT user_idx, user_email, user_name, user_birth, " +
                "user_phone, user_reg " +
                "FROM User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_idx"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getString("user_birth"),
                        rs.getString("user_phone"),
                        rs.getDate("user_reg"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email) {
        String getUsersByEmailQuery = "SELECT user_idx, user_email, user_name, user_birth, " +
                "user_phone, user_reg FROM User WHERE user_email =?"; // 해당 이메일을 만족하는 유저를 조회하는 쿼리문
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_idx"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getString("user_birth"),
                        rs.getString("user_phone"),
                        rs.getDate("user_reg")),
                getUsersByEmailParams);
    }

    public int checkEmail(String user_email) {
        String checkEmailQuery = "select exists(select user_email from User where user_email = ?)";
        String checkEmailParams = user_email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "INSERT INTO User (user_email, user_name, user_type, " +
                "user_password, user_birth, user_phone, user_push_yn) " +
                "VALUES (?,?,?,?,?,?,?)";

        Object[] createUserParams = new Object[]{postUserReq.getUser_email(), postUserReq.getUser_name(),
                postUserReq.getUser_type(), postUserReq.getUser_password(), postUserReq.getUser_birth(),
                postUserReq.getUser_phone(), postUserReq.getUser_push_yn()};

        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public void saveUserUnivInfo(PostInfoReq postInfoReq) {

        String saveUserUnivInfoQuery = "UPDATE User SET user_univ = ";

        if(postInfoReq.getUser_univ() != null)
            saveUserUnivInfoQuery += "'" + postInfoReq.getUser_univ() + "'";
        else
            saveUserUnivInfoQuery += "null";

        saveUserUnivInfoQuery += ", user_college = ";
        if(postInfoReq.getUser_college() != null)
            saveUserUnivInfoQuery += "'" + postInfoReq.getUser_college() + "'";
        else
            saveUserUnivInfoQuery += "null";

        saveUserUnivInfoQuery += ", user_department = ";
        if(postInfoReq.getUser_department() != null)
            saveUserUnivInfoQuery += "'" + postInfoReq.getUser_department() + "'";
        else
            saveUserUnivInfoQuery += "null";

        saveUserUnivInfoQuery += ", user_grade = ";
        if(postInfoReq.getUser_grade() != null)
            saveUserUnivInfoQuery += "'" + postInfoReq.getUser_grade() + "'";
        else
            saveUserUnivInfoQuery += "null";

        saveUserUnivInfoQuery += ", user_semester = ";
        if(postInfoReq.getUser_semester() != null)
            saveUserUnivInfoQuery += "'" + postInfoReq.getUser_semester() + "'";
        else
            saveUserUnivInfoQuery += "null";

        saveUserUnivInfoQuery += ", user_province = ";
        if(postInfoReq.getUser_province() != null)
            saveUserUnivInfoQuery += "'" + postInfoReq.getUser_province() + "'";
        else
            saveUserUnivInfoQuery += "null";

        saveUserUnivInfoQuery += ", user_city = ";
        if(postInfoReq.getUser_city() != null)
            saveUserUnivInfoQuery += "'" + postInfoReq.getUser_city() + "'";
        else
            saveUserUnivInfoQuery += "null";

        saveUserUnivInfoQuery += " WHERE user_idx = '" + postInfoReq.getUser_idx() + "'";

        System.out.println(saveUserUnivInfoQuery);
        this.jdbcTemplate.update(saveUserUnivInfoQuery);
    }


    public void saveUserExtraInfo(PostExtraReq postExtraReq) {
        String saveUserExtraInfoQuery = "UPDATE User " +
                "SET user_name = ?, user_birth = ?, user_phone = ?, user_push_yn = ? " +
                "WHERE user_idx = ?";
        Object[] saveUserExtraInfoParams = new Object[]{postExtraReq.getUser_name(), postExtraReq.getUser_birth(),
                postExtraReq.getUser_phone(), postExtraReq.getUser_push_yn(), postExtraReq.getUser_idx()};

        this.jdbcTemplate.update(saveUserExtraInfoQuery, saveUserExtraInfoParams);
    }



    /*public int modifyUserStatusLogIn(int user_idx){
        String modifyUserNameQuery = "update User set user_status = ? where user_idx = ? ";
        Object[] modifyUserNameParams = new Object[]{"NORMAL", user_idx};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }*/

    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public User getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery = "select user_idx, user_password, user_email, user_status from User where user_email = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwdParams = postLoginReq.getUser_email(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("user_idx"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getString("user_status")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwdParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }


    // 해당 userIdx를 갖는 유저조회
    public GetUserRes1 getUser(String user_phone) {
        String getUserQuery = "select user_email from User where user_phone = ?"; // 해당 user_id를 만족하는 유저를 조회하는 쿼리문
        String getUserParams = user_phone;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes1(
                        rs.getString("user_email")),// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 회원정보 변경
    public int modifyUserPassword(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update User set user_password = ? where user_email = ? "; // 해당 user_email를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUser_password(), patchUserReq.getUser_email()}; // 주입될 값들(password, user_idx) 순

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    public String modifyUserInfo(PostModifyReq postModifyReq) {
        String modifyUserInfoQuery = "UPDATE User SET ";
        String resultString = "";

        if (postModifyReq.getUser_name() != null) {
            modifyUserInfoQuery += "user_name = " + "'" + postModifyReq.getUser_name() + "'";
            resultString = "이름이 변경되었습니다.";
        }
        else if (postModifyReq.getUser_phone() != null) {
            modifyUserInfoQuery += "user_phone = " + "'" + postModifyReq.getUser_phone() + "'";
            resultString = "전화번호가 변경되었습니다.";
        }

        modifyUserInfoQuery += " WHERE user_idx = ?";
        int modifyUserInfoParams = postModifyReq.getUser_idx();

        this.jdbcTemplate.update(modifyUserInfoQuery, modifyUserInfoParams);

        return resultString;
    }
}
