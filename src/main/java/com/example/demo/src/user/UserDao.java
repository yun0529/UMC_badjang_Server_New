package com.example.demo.src.user;

import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostInfoReq;
import com.example.demo.src.user.model.PostUserReq;
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
}
