package com.example.demo.src.menu;


import com.example.demo.config.BaseException;
import com.example.demo.src.menu.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;

@Repository
public class MenuDao {

    private JdbcTemplate jdbcTemplate;
    private final JwtService jwtService = new JwtService();

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetPopularRes> getPopularRes(){
        String getPopularQuery = "SELECT * from Board order by post_recommend DESC limit 4 " ;

        return this.jdbcTemplate.query(getPopularQuery,
                (rs,rowNum) -> new GetPopularRes(
                        rs.getInt("post_idx"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_view"),
                        rs.getInt("post_comment"),
                        rs.getString("post_createAt"),
                        rs.getString("post_updateAt"),
                        rs.getString("post_status"))
                );
    }

    public List<GetTotalRes> getTotalRes(){
        String getTotalQuery = "SELECT total_idx, Total.scholarship_idx, fund_idx, total_createAt, " +
                "total_updateAt, total_status " +
                "from badjangDB.Total " +
                "left join Scholarship on Scholarship.scholarship_idx = Total.scholarship_idx " +
                "left join Support on fund_idx = Support.support_idx " ;

        return this.jdbcTemplate.query(getTotalQuery,
                (rs,rowNum) -> new GetTotalRes(
                        rs.getInt("total_idx"),
                        rs.getInt("scholarship_idx"),
                        rs.getInt("fund_idx"),
                        rs.getString("total_createAt"),
                        rs.getString("total_updateAt"),
                        rs.getString("total_status"))
        );
    }

    public List<GetSchoolRes> getSchoolRes(int userIdx) {

        String getSchoolResQuery = "SELECT scholarship_idx, User.user_idx, scholarship_createAt, scholarship_updateAt, scholarship_status " +
                "from badjangDB.Scholarship " +
                "join User where User.user_univ = scholarship_univ and scholarship_status = 'Y' " +
                "and user_idx = ? " ;

                int getUserUnivParams = userIdx;

                return this.jdbcTemplate.query(getSchoolResQuery,
                        (rs, rowNum) -> new GetSchoolRes(
                                rs.getInt("scholarship_idx"),
                                rs.getInt("user_idx"),
                                rs.getString("scholarship_createAt"),
                                rs.getString("scholarship_updateAt"),
                                rs.getString("scholarship_status")),
                        getUserUnivParams);
    }

}
