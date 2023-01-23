/*
package com.example.demo.src.Fcm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class FcmDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int checkUserPhone(String phone) {
        String checkUserPhoneQuery = "select exists(select user_phone from User where user_phone = ?)"; // User Table에 해당 폰넘버값을 갖는 유저 정보가 존재하는가?
        String checkUserPhoneParams = phone; // 해당(확인할) 폰넘버 값
        return this.jdbcTemplate.queryForObject(checkUserPhoneQuery,
                int.class,
                checkUserPhoneParams); //쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }
}
*/
