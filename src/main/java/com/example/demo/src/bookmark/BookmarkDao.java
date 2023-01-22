package com.example.demo.src.bookmark;

import com.example.demo.src.bookmark.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BookmarkDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetBookmarkBoardRes> getBookmarkBoard(long userIdx) {
        long userIdxParams = userIdx;
        System.out.println(userIdxParams);

        String getBookmarkBoardQuery = "select * " +
                "FROM Bookmark " +
                "LEFT JOIN Board " +
                "ON Bookmark.post_idx = Board.post_idx " +
                "where Bookmark.user_idx = ? " +
                "and Bookmark.post_idx is not null ";

        return this.jdbcTemplate.query(getBookmarkBoardQuery,
                (rs, rowNum) -> new GetBookmarkBoardRes(
                        rs.getLong("bookmark_idx"),
                        rs.getLong("post_idx"),
                        rs.getString("post_name"),
                        rs.getString("post_content"),
                        rs.getString("post_image"),
                        rs.getInt("post_view"),
                        rs.getInt("post_recommend"),
                        rs.getInt("post_comment"),
                        rs.getString("post_anonymity")
                ),
                userIdxParams
        );
    }


    public List<GetBookmarkScholarshipRes> getBookmarkScholarship(long userIdx) {
        long userIdxParams = userIdx;

        String getBookmarkScholarshipQuery = "select * " +
                "FROM Bookmark " +
                "LEFT JOIN Scholarship " +
                "ON Bookmark.scholarship_idx = Scholarship.scholarship_idx " +
                "where Bookmark.user_idx = ? " +
                "and Bookmark.scholarship_idx is not null ";

        return this.jdbcTemplate.query(getBookmarkScholarshipQuery,
                (rs, rowNum) -> new GetBookmarkScholarshipRes(
                        rs.getLong("bookmark_idx"),
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
                        rs.getString("scholarship_presentation")
                ),
                userIdxParams
        );
    }

    public List<GetBookmarkSupportRes> getBookmarkSupport(long userIdx) {
        long userIdxParams = userIdx;

        String getAllBookmarksQuery = "select * " +
                "FROM Bookmark " +
                "LEFT JOIN Support " +
                "ON Bookmark.support_idx = Support.support_idx " +
                "where Bookmark.user_idx = ? " +
                "and Bookmark.support_idx is not null ";

        return this.jdbcTemplate.query(getAllBookmarksQuery,
                (rs, rowNum) -> new GetBookmarkSupportRes(
                        rs.getLong("bookmark_idx"),
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
                userIdxParams
        );
    }

    public int BookmarkScholarshipNull(PostBookmarkScholarshipReq postBookmarkScholarshipReq) {

        String bookmarkNullQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "where user_idx = ? " +
                "and scholarship_idx = ? " +
                ") as isChk ";

        Object[] bookmarkNullParams = new Object[]{postBookmarkScholarshipReq.getUser_idx(), postBookmarkScholarshipReq.getScholarship_idx()};

        return this.jdbcTemplate.queryForObject(bookmarkNullQuery,
                int.class,
                bookmarkNullParams);

    }

    public int postBookmarkScholarship(PostBookmarkScholarshipReq postBookmarkScholarshipReq) {
        int bookmarkScholarshipNull = BookmarkScholarshipNull(postBookmarkScholarshipReq);

        String postBookmarkScholarshipQuery;
        Object[] postBookmarkScholarshipParams = new Object[]{postBookmarkScholarshipReq.getUser_idx(), postBookmarkScholarshipReq.getScholarship_idx()};

        if (bookmarkScholarshipNull == 1) {
            postBookmarkScholarshipQuery = "delete from Bookmark " +
                    "where user_idx = ? " +
                    "and scholarship_idx = ? ";
        } else {
            postBookmarkScholarshipQuery = "insert into Bookmark (user_idx, scholarship_idx) " +
                    "VALUES (?, ?) ";
        }
        return this.jdbcTemplate.update(postBookmarkScholarshipQuery, postBookmarkScholarshipParams);
    }

    public int BookmarkSupportNull(PostBookmarkSupportReq postBookmarkSupportReq) {

        String bookmarkNullQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "where user_idx = ? " +
                "and support_idx = ? " +
                ") as isChk ";

        Object[] supportNullParams = new Object[]{postBookmarkSupportReq.getUser_idx(), postBookmarkSupportReq.getSupport_idx()};


        return this.jdbcTemplate.queryForObject(bookmarkNullQuery,
                int.class,
                supportNullParams);

    }

    public int postBookmarkSupport(PostBookmarkSupportReq postBookmarkSupportReq) {
        int BookmarkScholarshipNull = BookmarkSupportNull(postBookmarkSupportReq);

        String postBookmarkSupportQuery;
        Object[] postBookmarkSupportParams = new Object[]{postBookmarkSupportReq.getUser_idx(), postBookmarkSupportReq.getSupport_idx()};

        if (BookmarkScholarshipNull == 1) {

            postBookmarkSupportQuery = "delete from Bookmark " +
                    "where user_idx = ? " +
                    "and support_idx = ? ";

        } else {

            postBookmarkSupportQuery = "insert into Bookmark (user_idx, support_idx) " +
                    "VALUES (?, ?) ";

        }
        return this.jdbcTemplate.update(postBookmarkSupportQuery, postBookmarkSupportParams);
    }

    public int BookmarkBoardNull(long userIdx, long boardIdx) {

        String BookmarkNullQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "where user_idx = ? " +
                "and board_idx = ? " +
                ") as isChk ";

        long userIdxParams = userIdx;
        long boardIdxParams = boardIdx;

        return this.jdbcTemplate.queryForObject(BookmarkNullQuery,
                int.class,
                userIdxParams, boardIdxParams);

    }

}
