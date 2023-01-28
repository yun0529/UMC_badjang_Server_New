package com.example.demo.src.oauth;

import com.example.demo.src.oauth.model.KakaoProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OAuthDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createUser(KakaoProfile profile, String refresh_token) {
        String createUserQuery = "INSERT INTO User (user_email, user_name, user_type, " +
                "user_birth, user_phone, user_push_yn) " +
                "VALUES (?,'temp','KAKAO','temp','temp','N')";

        Object[] createUserParams = new Object[]{
                profile.getKakao_account().getEmail()
        };

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        int user_idx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

        String createOAuthQuery = "INSERT INTO User_OAuth (user_idx, refresh_token) VALUES (?, ?)";
        Object[] createOAuthParams = new Object[] {
                user_idx, refresh_token
        };

        this.jdbcTemplate.update(createOAuthQuery, createOAuthParams);

        return user_idx;
    }


    public int checkEmail(String user_email) {
        String checkEmailQuery = "select exists(select user_email from User where user_email = ?)";
        String checkEmailParams = user_email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int checkSavedUser(String user_email) {
        String checkSavedUserQuery = "select exists(select user_idx from User u NATURAL JOIN User_OAuth o " +
                "WHERE u.user_email=? and u.user_type='KAKAO')";
        String checkSavedUserParams = user_email;

        return this.jdbcTemplate.queryForObject(checkSavedUserQuery,
                int.class,
                checkSavedUserParams);
    }

    public int findUserByEmail(KakaoProfile profile) {
        String findUserByEmailQuery = "SELECT user_idx FROM User WHERE user_email = ? and user_type = 'KAKAO'";
        String findUserByEmailParams = profile.getKakao_account().getEmail();

        return this.jdbcTemplate.queryForObject(findUserByEmailQuery, int.class, findUserByEmailParams);
    }
}
