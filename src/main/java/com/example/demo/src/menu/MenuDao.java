package com.example.demo.src.menu;

import com.example.demo.src.menu.model.GetPopularRes;
import com.example.demo.src.menu.model.GetSchoolRes;
import com.example.demo.src.menu.model.GetTotalRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MenuDao {

    private JdbcTemplate jdbcTemplate;

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

    public List<GetSchoolRes> getSchoolRes(){
        String getTotalQuery = "SELECT school_idx, Scholarship.scholarship_idx, User.user_idx, school_createAt, school_updateAt, school_status " +
                "from badjangDB.School " +
                "left join Scholarship on School.scholarship_idx = Scholarship.scholarship_idx " +
                "left join User on School.user_idx = User.user_idx where school_status = 'Y' " ;

        return this.jdbcTemplate.query(getTotalQuery,
                (rs,rowNum) -> new GetSchoolRes(
                        rs.getInt("school_idx"),
                        rs.getInt("scholarship_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("school_createAt"),
                        rs.getString("school_updateAt"),
                        rs.getString("school_status"))
        );
    }
}
