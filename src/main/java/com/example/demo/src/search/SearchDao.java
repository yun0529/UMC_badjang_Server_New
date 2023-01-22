package com.example.demo.src.search;

import com.example.demo.src.search.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SearchDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetSearchBoardRes> searchBoard(String query) {
        String searchQuery = "select * " +
                "from Board " +
                "where post_name like ? " +
                "or post_content like ? " +
                "order by post_view desc";
        String wrappedKeyword = "%" + query + "%";

        return this.jdbcTemplate.query(searchQuery,
                (rs, rowNum) -> new GetSearchBoardRes(
                        rs.getLong("post_idx"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_anonymity")
                ),
                wrappedKeyword, wrappedKeyword
        );
    }

    public List<GetSearchScholarshipRes> searchScholarship(String query) {
        String searchQuery = "select * " +
                "from Scholarship " +
                "where Scholarship_name like ? " +
                "or Scholarship_institution like ? " +
                "or Scholarship_content like ? " +
                "order by Scholarship_view desc";
        String wrappedKeyword = "%" + query + "%";

        return this.jdbcTemplate.query(searchQuery,
                (rs, rowNum) -> new GetSearchScholarshipRes(
                        rs.getLong("scholarship_idx"),
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
                        rs.getString("scholarship_category")
                ),
                wrappedKeyword, wrappedKeyword, wrappedKeyword
        );
    }

    public List<GetSearchSupportRes> searchSupport(String query) {
        String searchQuery = "select * " +
                "from Support " +
                "where Support_name like ? " +
                "or Support_institution like ? " +
                "or Support_content like ? " +
                "order by Support_view desc";
        String wrappedKeyword = "%" + query + "%";

        return this.jdbcTemplate.query(searchQuery,
                (rs, rowNum) -> new GetSearchSupportRes(
                        rs.getLong("support_idx"),
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
                        rs.getString("support_presentation")
                ),
                wrappedKeyword, wrappedKeyword, wrappedKeyword
        );
    }

    public int saveCountQuery(long userIdx) {
        String saveCountQuery = "select count(search_history_query) FROM Search_History where user_idx =  ?";
        long saveCountParams = userIdx;

        return this.jdbcTemplate.queryForObject(saveCountQuery, int.class, saveCountParams);
    }

    public void saveQuery (long userIdx, String query) {

        String saveQuery = "INSERT INTO Search_History (user_idx, search_history_query) " +
        "SELECT * FROM (SELECT ?, ?) AS tmp " +
        "WHERE NOT EXISTS ( " +
                "SELECT search_history_query FROM Search_History WHERE user_idx = ? and search_history_query = ? " +
        ") LIMIT 1 ";

        long saveQueryParams1 = userIdx;
        String saveQueryParams2 = query;

        this.jdbcTemplate.update(saveQuery, saveQueryParams1, saveQueryParams2, saveQueryParams1, saveQueryParams2);
    }

    public void deleteQuery (long userIdx) {

        String deleteQuery = "DELETE FROM Search_History " +
            "where user_idx = ? " +
            "ORDER BY search_history_createAt " +
            "LIMIT 1 ";
        long deleteQueryParams = userIdx;

        this.jdbcTemplate.update(deleteQuery, deleteQueryParams);
    }

    public void postSearchHistory(long userIdx, String query) {
        if (saveCountQuery(userIdx) < 5) {
            saveQuery(userIdx, query);
        } else {
            deleteQuery(userIdx);
            saveQuery(userIdx, query);
        }
    }

    public List<GetSearchHistoryRes> searchHistory(long userIdx) {
        String searchHistoryQuery = "select * " +
                "from Search_History " +
                "where user_idx = ? ";
        long searchHistoryParams = userIdx;

        return this.jdbcTemplate.query(searchHistoryQuery,
                (rs, rowNum) -> new GetSearchHistoryRes(
                        rs.getLong("search_history_idx"),
                        rs.getLong("user_idx"),
                        rs.getString("search_history_query")
                ),
                searchHistoryParams
        );
    }

    public int deleteHistoryQuery(DeleteSearchHistoryReq deleteSearchHistoryReq) {
        String deleteQuery = "DELETE FROM Search_History " +
                "where user_idx = ? " +
                "and search_history_idx = ? ";

        Object[] createSupportCommentParams = new Object[]{deleteSearchHistoryReq.getUser_idx(), deleteSearchHistoryReq.getSupport_history_idx()};

        return this.jdbcTemplate.update(deleteQuery, createSupportCommentParams);
    }


}
