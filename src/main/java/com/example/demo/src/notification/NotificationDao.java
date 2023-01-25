package com.example.demo.src.notification;

import com.example.demo.src.notification.model.GetNotificationCommentRes;
import com.example.demo.src.notification.model.GetNotificationScholarshipRes;
import com.example.demo.src.notification.model.GetNotificationSupportRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NotificationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetNotificationScholarshipRes> getNotificationScholarshipRes(int userIdx) {
        String getNotificationScholarshipResQuery = "select Scholarship.scholarship_idx, Scholarship.scholarship_name, Scholarship.scholarship_institution, Scholarship.scholarship_univ " +
                "from User, Scholarship " +
                "where User.user_univ = Scholarship.scholarship_univ " +
                "and User.user_idx = ? " +
                "and Scholarship.scholarship_createAt > User.user_reg " +
                "order by Scholarship.scholarship_createAt desc " +
                "LIMIT 5 ";
        int getNotificationScholarshipResParams = userIdx;

        return this.jdbcTemplate.query(getNotificationScholarshipResQuery,
                (rs, rowNum) -> new GetNotificationScholarshipRes(
                        rs.getInt("scholarship_idx"),
                        rs.getString("scholarship_name"),
                        rs.getString("scholarship_institution"),
                        rs.getString("scholarship_univ")
                ),
                getNotificationScholarshipResParams
        );
    }

    public List<GetNotificationSupportRes> getNotificationSupportRes(int userIdx) {
        String getNotificationSupportResQuery = "select support_idx, support_name, support_institution " +
                "from Support, User  " +
                "where Support.support_createAt > User.user_reg " +
                "and User.user_idx = ? " +
                "order by support_createAt desc " +
                "LIMIT 5 ";
        int getNotificationSupportResParams = userIdx;

        return this.jdbcTemplate.query(getNotificationSupportResQuery,
                (rs, rowNum) -> new GetNotificationSupportRes(
                        rs.getInt("support_idx"),
                        rs.getString("support_name"),
                        rs.getString("support_institution")
                ), getNotificationSupportResParams
        );
    }

    public List<GetNotificationCommentRes> getNotificationCommentRes(int userIdx) {
        String getNotificationCommentResQuery = "select Board.post_idx, Comment.comment_idx, Board.post_name, Comment.comment_content from Comment " +
                "left join Board  " +
                "on Comment.post_idx = Board.post_idx " +
                "where Board.user_idx = ? " +
                "and Comment.user_idx != ? " +
                "order by Comment.comment_createAt desc " +
                "LIMIT 5 ";
        int getNotificationCommentResQueryParams = userIdx;

        return this.jdbcTemplate.query(getNotificationCommentResQuery,
                (rs, rowNum) -> new GetNotificationCommentRes(
                        rs.getInt("post_idx"),
                        rs.getInt("comment_idx"),
                        rs.getString("post_name"),
                        rs.getString("comment_content")
                ), getNotificationCommentResQueryParams, getNotificationCommentResQueryParams
        );
    }


}
