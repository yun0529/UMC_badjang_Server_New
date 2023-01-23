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

}
