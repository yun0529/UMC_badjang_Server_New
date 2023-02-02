package com.example.demo.src.menu;

import com.example.demo.src.menu.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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

    public List<GetScholarshipRes> getTotalScholarshipRes(int user_idx){
        String getTotalScholarshipQuery = "SELECT * ,(select exists(select scholarship_idx, user_idx from Bookmark " +
                "where scholarship_idx = Scholarship.scholarship_idx " +
                "and user_idx = ?)) " +
                "as bookmark_post FROM Scholarship " ;

        return this.jdbcTemplate.query(getTotalScholarshipQuery,
                (rs,rowNum) -> new GetScholarshipRes(
                        rs.getInt("scholarship_idx"),
                        rs.getString("scholarship_name"),
                        rs.getString("scholarship_institution"),
                        rs.getString("scholarship_content"),
                        rs.getString("scholarship_image"),
                        rs.getString("scholarship_homepage"),
                        rs.getInt("scholarship_view"),
                        rs.getInt("scholarship_comment"),
                        rs.getString("scholarship_scale"),
                        rs.getString("scholarship_term"),
                        rs.getString("scholarship_presentation"),
                        rs.getString("scholarship_createAt"),
                        rs.getString("scholarship_updateAt"),
                        rs.getString("scholarship_status"),
                        rs.getString("scholarship_univ"),
                        rs.getString("scholarship_college"),
                        rs.getString("scholarship_department"),
                        rs.getString("scholarship_grade"),
                        rs.getString("scholarship_semester"),
                        rs.getString("scholarship_province"),
                        rs.getString("scholarship_city"),
                        rs.getString("scholarship_category"),
                        rs.getInt("bookmark_post"))
                ,user_idx);
    }

    public List<GetSupportRes> getTotalSupportRes(int user_idx){
        String getTotalSupportQuery = "SELECT * ,(select exists(select support_idx, user_idx from Bookmark " +
                "where support_idx = Support.support_idx " +
                "and user_idx = ?)) " +
                "as bookmark_post FROM Support " ;

        return this.jdbcTemplate.query(getTotalSupportQuery,
                (rs,rowNum) -> new GetSupportRes(
                        rs.getInt("support_idx"),
                        rs.getString("support_policy"),
                        rs.getString("support_name"),
                        rs.getString("support_institution"),
                        rs.getString("support_content"),
                        rs.getString("support_image"),
                        rs.getString("support_homepage"),
                        rs.getInt("support_view"),
                        rs.getInt("support_comment"),
                        rs.getString("support_scale"),
                        rs.getString("support_term"),
                        rs.getString("support_presentation"),
                        rs.getString("support_createAt"),
                        rs.getString("support_updateAt"),
                        rs.getString("support_status"),
                        rs.getString("support_province"),
                        rs.getString("support_city"),
                        rs.getString("support_univ"),
                        rs.getString("support_college"),
                        rs.getString("support_department"),
                        rs.getString("support_grade"),
                        rs.getString("support_semester"),
                        rs.getString("support_category"),
                        rs.getInt("bookmark_post")
                ),user_idx);
    }

    public List<GetSchoolRes> getScholarship(int user_idx) {

        String getSchoolResQuery = "SELECT scholarship_idx, user_idx, scholarship_createAt, scholarship_updateAt, scholarship_status " +
                "from badjangDB.Scholarship " +
                "left join User U on U.user_idx = user_idx " +
                "where user_idx = ? and U.user_univ = scholarship_univ " ;

                int param = user_idx;

                return this.jdbcTemplate.query(getSchoolResQuery,
                        (rs, rowNum) -> new GetSchoolRes(
                                rs.getInt("scholarship_idx"),
                                rs.getInt("user_idx"),
                                rs.getString("scholarship_createAt"),
                                rs.getString("scholarship_updateAt"),
                                rs.getString("scholarship_status")
                        ), param);
    }
}

