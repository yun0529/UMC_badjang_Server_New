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

    //즐겨찾기(게시판) 조회
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
                        rs.getInt("bookmark_idx"),
                        rs.getInt("post_idx"),
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

    //즐겨찾기(장학금) 조회
    public List<GetBookmarkScholarshipRes> getBookmarkScholarship(int userIdx) {
        long userIdxParams = userIdx;

        String getBookmarkScholarshipQuery = "select Bookmark.bookmark_idx, Bookmark.scholarship_idx, Scholarship.scholarship_name, " +
                "scholarship_institution, scholarship_content, scholarship_image, scholarship_homepage, scholarship_view, " +
                "scholarship_comment, scholarship_scale, scholarship_term, scholarship_presentation " +
                "FROM Bookmark " +
                "inner JOIN Scholarship " +
                "ON Bookmark.scholarship_idx = Scholarship.scholarship_idx " +
                "where Bookmark.user_idx = ? " +
                "union " +
                "select Bookmark.bookmark_idx, Scholarship.scholarship_idx, Scholarship.scholarship_name, " +
                "scholarship_institution, scholarship_content, scholarship_image, scholarship_homepage, scholarship_view, " +
                "scholarship_comment, scholarship_scale, scholarship_term, scholarship_presentation " +
                "FROM Bookmark " +
                "inner JOIN School " +
                "on Bookmark.school_idx = School.school_idx " +
                "left JOIN Scholarship " +
                "ON School.scholarship_idx = Scholarship.scholarship_idx " +
                "where Bookmark.user_idx = ? " +
                "union " +
                "select Bookmark.bookmark_idx, Scholarship.scholarship_idx, Scholarship.scholarship_name, " +
                "scholarship_institution, scholarship_content, scholarship_image, scholarship_homepage, scholarship_view, " +
                "scholarship_comment, scholarship_scale, scholarship_term, scholarship_presentation " +
                "FROM Bookmark " +
                "inner JOIN Total " +
                "on Total.total_idx = Bookmark.total_idx " +
                "left JOIN Scholarship " +
                "ON Total.scholarship_idx = Scholarship.scholarship_idx " +
                "where Bookmark.user_idx = ? ";


        return this.jdbcTemplate.query(getBookmarkScholarshipQuery,
                (rs, rowNum) -> new GetBookmarkScholarshipRes(
                        rs.getInt("bookmark_idx"),
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
                        rs.getString("scholarship_presentation")
                ),
                userIdxParams, userIdxParams, userIdxParams
        );
    }

    //즐겨찾기(지원금) 조회
    public List<GetBookmarkSupportRes> getBookmarkSupport(int userIdx) {
        long userIdxParams = userIdx;

        String getBookmarkSupportQuery = "select bookmark_idx, Bookmark.support_idx, support_policy, support_name, support_institution, " +
                "support_content, support_image, support_homepage, support_view, " +
                "support_comment, support_scale, support_term, support_presentation " +
                "FROM Bookmark " +
                "inner JOIN Support " +
                "ON Bookmark.support_idx = Support.support_idx " +
                "where Bookmark.user_idx = ? " +
                "union " +
                "select bookmark_idx, Total.fund_idx, support_policy, support_name, support_institution, " +
                "support_content, support_image, support_homepage, support_view, " +
                "support_comment, support_scale, support_term, support_presentation " +
                "FROM Bookmark " +
                "inner JOIN Total " +
                "on Total.total_idx = Bookmark.total_idx " +
                "left JOIN Support " +
                "ON Total.fund_idx = Support.support_idx " +
                "where Bookmark.user_idx = ? ";

        return this.jdbcTemplate.query(getBookmarkSupportQuery,
                (rs, rowNum) -> new GetBookmarkSupportRes(
                        rs.getInt("bookmark_idx"),
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
                        rs.getString("support_presentation")
                ),
                userIdxParams, userIdxParams
        );
    }

    //북마크에 저장되어있는 장학금인지 확인
    public int bookmarkScholarshipNullCheck(PostBookmarkScholarshipReq postBookmarkScholarshipReq) {

        String bookmarkScholarshipNullCheckQuery = "select Exists( " +
                "select bookmark_idx " +
                "from Bookmark " +
                "where user_idx = ? " +
                "and scholarship_idx = ? " +
                ") as isChk ";

        Object[] bookmarkScholarshipNullCheckParams = new Object[]{postBookmarkScholarshipReq.getUser_idx(), postBookmarkScholarshipReq.getScholarship_idx()};

        return this.jdbcTemplate.queryForObject(bookmarkScholarshipNullCheckQuery,
                int.class,
                bookmarkScholarshipNullCheckParams);

    }

    public int nullCheckSchoolForScholarship(PostBookmarkScholarshipReq postBookmarkScholarshipReq) {

        String nullCheckSchoolForScholarshipQuery = "select Exists( " +
                "select bookmark_idx from School " +
                "left join Bookmark " +
                "on Bookmark.School_idx = School.School_idx " +
                "where Bookmark.user_idx = ? " +
                "and School.scholarship_idx = ? " +
                ") as isChk ";

        Object[] nullCheckSchoolForScholarshipParams = new Object[]{postBookmarkScholarshipReq.getUser_idx(), postBookmarkScholarshipReq.getScholarship_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckSchoolForScholarshipQuery,
                int.class,
                nullCheckSchoolForScholarshipParams);

    }

    public int nullCheckTotalForScholarship(PostBookmarkScholarshipReq postBookmarkScholarshipReq) {

        String nullCheckTotalForScholarshipQuery = "select Exists( " +
                "select bookmark_idx from Total " +
                "left join Bookmark " +
                "on Bookmark.total_idx = Total.total_idx " +
                "where Bookmark.user_idx = ? " +
                "and Total.scholarship_idx = ? " +
                ") as isChk ";

        Object[] nullCheckTotalForScholarshipParams = new Object[]{postBookmarkScholarshipReq.getUser_idx(), postBookmarkScholarshipReq.getScholarship_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckTotalForScholarshipQuery,
                int.class,
                nullCheckTotalForScholarshipParams);

    }

    //북마크에 저장되어있다면 삭제하고, 저장되어있지 않다면 저장하기(즐겨찾기 버튼 클릭)
    public String postBookmarkScholarship(PostBookmarkScholarshipReq postBookmarkScholarshipReq) {
        int bookmarkScholarshipNullCheck = bookmarkScholarshipNullCheck(postBookmarkScholarshipReq);
        int nullCheckSchoolForScholarship = nullCheckSchoolForScholarship(postBookmarkScholarshipReq);
        int nullCheckTotalForScholarship = nullCheckTotalForScholarship(postBookmarkScholarshipReq);

        String postBookmarkScholarshipQuery;
        Object[] postBookmarkScholarshipParams = new Object[]{postBookmarkScholarshipReq.getUser_idx(), postBookmarkScholarshipReq.getScholarship_idx()};

        if (bookmarkScholarshipNullCheck == 1) {
            postBookmarkScholarshipQuery = "delete from Bookmark " +
                    "where user_idx = ? " +
                    "and scholarship_idx = ? ";
            this.jdbcTemplate.update(postBookmarkScholarshipQuery, postBookmarkScholarshipParams);
            return "삭제";

        } else if (nullCheckSchoolForScholarship == 1) {
            postBookmarkScholarshipQuery = "delete a " +
                    "from Bookmark a " +
                    "left join School b " +
                    "on a.School_idx = b.School_idx " +
                    "where a.user_idx = ? " +
                    "and b.scholarship_idx = ? ";
            this.jdbcTemplate.update(postBookmarkScholarshipQuery, postBookmarkScholarshipParams);
            return "삭제";

        } else if (nullCheckTotalForScholarship == 1) {
            postBookmarkScholarshipQuery = "delete a " +
                    "from Bookmark a " +
                    "left join Total b " +
                    "on a.total_idx = b.total_idx " +
                    "where a.user_idx = ? " +
                    "and b.scholarship_idx = ? ";
            this.jdbcTemplate.update(postBookmarkScholarshipQuery, postBookmarkScholarshipParams);
            return "삭제";

        } else {
            postBookmarkScholarshipQuery = "insert into Bookmark (user_idx, scholarship_idx) " +
                    "VALUES (?, ?) ";
            this.jdbcTemplate.update(postBookmarkScholarshipQuery, postBookmarkScholarshipParams);
            return "추가";
        }
    }

    //북마크에 저장되어있는 지원금인지 확인
    public int bookmarkSupportNullCheck(PostBookmarkSupportReq postBookmarkSupportReq) {

        String bookmarkSupportNullCheckQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "where user_idx = ? " +
                "and support_idx = ? " +
                ") as isChk ";

        Object[] bookmarkSupportNullCheckParams = new Object[]{postBookmarkSupportReq.getUser_idx(), postBookmarkSupportReq.getSupport_idx()};


        return this.jdbcTemplate.queryForObject(bookmarkSupportNullCheckQuery,
                int.class,
                bookmarkSupportNullCheckParams);

    }

    public int nullCheckTotalForSupport(PostBookmarkSupportReq postBookmarkSupportReq) {

        String nullCheckTotalForScholarshipQuery = "select Exists( " +
                "select bookmark_idx from Total " +
                "left join Bookmark " +
                "on Bookmark.total_idx = Total.total_idx " +
                "where Bookmark.user_idx = ? " +
                "and Total.fund_idx = ? " +
                ") as isChk ";

        Object[] nullCheckTotalForScholarshipParams = new Object[]{postBookmarkSupportReq.getUser_idx(), postBookmarkSupportReq.getSupport_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckTotalForScholarshipQuery,
                int.class,
                nullCheckTotalForScholarshipParams);

    }

    //북마크에 저장되어있다면 삭제하고, 저장되어있지 않다면 저장하기(즐겨찾기 버튼 클릭)
    public String postBookmarkSupport(PostBookmarkSupportReq postBookmarkSupportReq) {
        int bookmarkSupportNullCheck = bookmarkSupportNullCheck(postBookmarkSupportReq);
        int nullCheckTotalForSupport = nullCheckTotalForSupport(postBookmarkSupportReq);

        String postBookmarkSupportQuery;
        Object[] postBookmarkSupportParams = new Object[]{postBookmarkSupportReq.getUser_idx(), postBookmarkSupportReq.getSupport_idx()};

        if (bookmarkSupportNullCheck == 1) {

            postBookmarkSupportQuery = "delete from Bookmark " +
                    "where user_idx = ? " +
                    "and support_idx = ? ";
            this.jdbcTemplate.update(postBookmarkSupportQuery, postBookmarkSupportParams);
            return "삭제";

        } else if (nullCheckTotalForSupport == 1) {
            postBookmarkSupportQuery = "delete a " +
                    "from Bookmark a " +
                    "left join Total b " +
                    "on a.total_idx = b.total_idx " +
                    "where a.user_idx = ? " +
                    "and b.fund_idx = ? ";
            this.jdbcTemplate.update(postBookmarkSupportQuery, postBookmarkSupportParams);
            return "삭제";

        } else {
            postBookmarkSupportQuery = "insert into Bookmark (user_idx, support_idx) " +
                    "VALUES (?, ?) ";
            this.jdbcTemplate.update(postBookmarkSupportQuery, postBookmarkSupportParams);
            return "추가";
        }

    }

    //북마크에 저장되어있는 게시판인지 확인
    public int bookmarkBoardNullCheck(PostBookmarkBoardReq postBookmarkBoardReq) {

        String bookmarkBoardNullQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "where user_idx = ? " +
                "and post_idx = ? " +
                ") as isChk ";

        Object[] bookmarkBoardNullCheckParams = new Object[]{postBookmarkBoardReq.getUser_idx(), postBookmarkBoardReq.getPost_idx()};


        return this.jdbcTemplate.queryForObject(bookmarkBoardNullQuery,
                int.class,
                bookmarkBoardNullCheckParams);

    }

    //북마크에 저장되어있다면 삭제하고, 저장되어있지 않다면 저장하기(즐겨찾기 버튼 클릭)
    public String postBookmarkBoard(PostBookmarkBoardReq postBookmarkBoardReq) {
        int bookmarkBoardNullCheck = bookmarkBoardNullCheck(postBookmarkBoardReq);

        String postBookmarkBoardQuery;
        Object[] postBookmarkBoardParams = new Object[]{postBookmarkBoardReq.getUser_idx(), postBookmarkBoardReq.getPost_idx()};

        if (bookmarkBoardNullCheck == 1) {

            postBookmarkBoardQuery = "delete from Bookmark " +
                    "where user_idx = ? " +
                    "and post_idx = ? ";

            this.jdbcTemplate.update(postBookmarkBoardQuery, postBookmarkBoardParams);
            return "삭제";

        } else {
            postBookmarkBoardQuery = "insert into Bookmark (user_idx, post_idx) " +
                    "VALUES (?, ?) ";

            this.jdbcTemplate.update(postBookmarkBoardQuery, postBookmarkBoardParams);
            return "추가";
        }

    }

    //북마크에 저장되어있는 우리학교 장학금(장학금)인지 확인
    public int bookmarkSchoolNullCheck(PostBookmarkSchoolReq postBookmarkSchoolReq) {

        String bookmarkSchoolNullCheckQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "where user_idx = ? " +
                "and school_idx = ? " +
                ") as isChk ";

        Object[] bookmarkSchoolNullCheckParams = new Object[]{postBookmarkSchoolReq.getUser_idx(), postBookmarkSchoolReq.getSchool_idx()};


        return this.jdbcTemplate.queryForObject(bookmarkSchoolNullCheckQuery,
                int.class,
                bookmarkSchoolNullCheckParams);

    }

    public int nullCheckScholarshipForSchool(PostBookmarkSchoolReq postBookmarkSchoolReq) {

        String nullCheckSchoolForScholarshipQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "join Scholarship " +
                "on Bookmark.scholarship_idx = Scholarship.scholarship_idx " +
                "left join School " +
                "on School.scholarship_idx = Scholarship.scholarship_idx " +
                "where School.user_idx = ? " +
                "and School.school_idx = ? " +
                ") as isChk ";

        Object[] nullCheckSchoolForScholarshipParams = new Object[]{postBookmarkSchoolReq.getUser_idx(), postBookmarkSchoolReq.getSchool_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckSchoolForScholarshipQuery,
                int.class,
                nullCheckSchoolForScholarshipParams);

    }

    public int nullCheckTotalForSchool(PostBookmarkSchoolReq postBookmarkSchoolReq) {

        String nullCheckSchoolForScholarshipQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "join Total " +
                "on Total.total_idx = Bookmark.total_idx " +
                "left join School " +
                "on School.scholarship_idx = Total.scholarship_idx " +
                "where School.user_idx = ? " +
                "and School.school_idx = ? " +
                ") as isChk ";

        Object[] nullCheckSchoolForScholarshipParams = new Object[]{postBookmarkSchoolReq.getUser_idx(), postBookmarkSchoolReq.getSchool_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckSchoolForScholarshipQuery,
                int.class,
                nullCheckSchoolForScholarshipParams);

    }



    //북마크에 저장되어있다면 삭제하고, 저장되어있지 않다면 저장하기(즐겨찾기 버튼 클릭)
    public String postBookmarkSchool(PostBookmarkSchoolReq postBookmarkSchoolReq) {
        int bookmarkSchoolNullCheck = bookmarkSchoolNullCheck(postBookmarkSchoolReq);
        int nullCheckScholarshipForSchool = nullCheckScholarshipForSchool(postBookmarkSchoolReq);
        int nullCheckTotalForSchool = nullCheckTotalForSchool(postBookmarkSchoolReq);

        String postBookmarkSchoolQuery;
        Object[] postBookmarkSchoolParams = new Object[]{postBookmarkSchoolReq.getUser_idx(), postBookmarkSchoolReq.getSchool_idx()};

        if (bookmarkSchoolNullCheck == 1) {

            postBookmarkSchoolQuery = "delete from Bookmark " +
                    "where user_idx = ? " +
                    "and school_idx = ? ";

            this.jdbcTemplate.update(postBookmarkSchoolQuery, postBookmarkSchoolParams);
            return "삭제";

        } else if (nullCheckScholarshipForSchool == 1) {

            postBookmarkSchoolQuery = "delete from Bookmark where scholarship_idx = ( " +
                    "select * from ( " +
                    "select Scholarship.scholarship_idx from Bookmark " +
                    "join Scholarship " +
                    "on Bookmark.scholarship_idx = Scholarship.scholarship_idx " +
                    "left join School " +
                    "on School.scholarship_idx = Scholarship.scholarship_idx " +
                    "where School.user_idx = ? " +
                    "and School.school_idx = ? ) A ) ";

            this.jdbcTemplate.update(postBookmarkSchoolQuery, postBookmarkSchoolParams);
            return "삭제";

        } else if (nullCheckTotalForSchool == 1) {

            postBookmarkSchoolQuery = "delete from Bookmark where total_idx = ( " +
                    "select * from ( " +
                    "select Total.total_idx from Bookmark " +
                    "join Total " +
                    "on Total.total_idx = Bookmark.total_idx " +
                    "left join School " +
                    "on School.scholarship_idx = Total.scholarship_idx " +
                    "where School.user_idx = ? " +
                    "and School.school_idx = ? ) A ) ";

            this.jdbcTemplate.update(postBookmarkSchoolQuery, postBookmarkSchoolParams);
            return "삭제";

        }
        else {

            postBookmarkSchoolQuery = "insert into Bookmark (user_idx, school_idx) " +
                    "VALUES (?, ?) ";

            this.jdbcTemplate.update(postBookmarkSchoolQuery, postBookmarkSchoolParams);
            return "추가";
        }

    }

    //북마크에 저장되어있는 전국소식(장학금)인지 확인
    public int bookmarkTotalNullCheck(PostBookmarkTotalReq postBookmarkTotalReq) {

        String bookmarkSchoolNullCheckQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "where user_idx = ? " +
                "and total_idx = ? " +
                ") as isChk ";

        Object[] bookmarkSchoolNullCheckParams = new Object[]{postBookmarkTotalReq.getUser_idx(), postBookmarkTotalReq.getTotal_idx()};


        return this.jdbcTemplate.queryForObject(bookmarkSchoolNullCheckQuery,
                int.class,
                bookmarkSchoolNullCheckParams);

    }

    public int nullCheckScholarshipForTotal(PostBookmarkTotalReq postBookmarkTotalReq) {

        String nullCheckScholarshipForTotalQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "join Scholarship  " +
                "on Bookmark.scholarship_idx = Scholarship.scholarship_idx " +
                "left join Total " +
                "on Total.scholarship_idx = Scholarship.scholarship_idx " +
                "where Bookmark.user_idx = ? " +
                "and Total.total_idx = ? " +
                ") as isChk ";

        Object[] nullCheckScholarshipForTotalParams = new Object[]{postBookmarkTotalReq.getUser_idx(), postBookmarkTotalReq.getTotal_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckScholarshipForTotalQuery,
                int.class,
                nullCheckScholarshipForTotalParams);

    }

    public int nullCheckSupportForTotal(PostBookmarkTotalReq postBookmarkTotalReq) {

        String nullCheckSupportForTotalQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "join Support  " +
                "on Bookmark.support_idx = Support.support_idx " +
                "left join Total " +
                "on Total.fund_idx = Support.support_idx " +
                "where Bookmark.user_idx = ? " +
                "and Total.total_idx = ? " +
                ") as isChk ";

        Object[] nullCheckSupportForTotalParams = new Object[]{postBookmarkTotalReq.getUser_idx(), postBookmarkTotalReq.getTotal_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckSupportForTotalQuery,
                int.class,
                nullCheckSupportForTotalParams);

    }

    public int nullCheckSchoolForTotal(PostBookmarkTotalReq postBookmarkTotalReq) {

        String nullCheckSchoolForTotalQuery = "select Exists( " +
                "select bookmark_idx from Bookmark " +
                "join School " +
                "on School.school_idx = Bookmark.school_idx " +
                "left join Total " +
                "on Total.scholarship_idx = School.scholarship_idx " +
                "where School.user_idx = ? " +
                "and Total.total_idx = ? " +
                ") as isChk ";

        Object[] nullCheckSchoolForTotalParams = new Object[]{postBookmarkTotalReq.getUser_idx(), postBookmarkTotalReq.getTotal_idx()};

        return this.jdbcTemplate.queryForObject(nullCheckSchoolForTotalQuery,
                int.class,
                nullCheckSchoolForTotalParams);

    }

    //북마크에 저장되어있다면 삭제하고, 저장되어있지 않다면 저장하기(즐겨찾기 버튼 클릭)
    public String postBookmarkTotal(PostBookmarkTotalReq postBookmarkTotalReq) {
        int bookmarkTotalNullCheck = bookmarkTotalNullCheck(postBookmarkTotalReq);
        int nullCheckScholarshipForTotal = nullCheckScholarshipForTotal(postBookmarkTotalReq);
        int nullCheckSupportForTotal = nullCheckSupportForTotal(postBookmarkTotalReq);
        int nullCheckSchoolForTotal = nullCheckSchoolForTotal(postBookmarkTotalReq);


        String postBookmarkTotalQuery;
        Object[] postBookmarkTotalParams = new Object[]{postBookmarkTotalReq.getUser_idx(), postBookmarkTotalReq.getTotal_idx()};

        if (bookmarkTotalNullCheck == 1) {

            postBookmarkTotalQuery = "delete from Bookmark " +
                    "where user_idx = ? " +
                    "and total_idx = ? ";

            this.jdbcTemplate.update(postBookmarkTotalQuery, postBookmarkTotalParams);
            return "삭제";

        } else if (nullCheckScholarshipForTotal == 1) {

            postBookmarkTotalQuery = "delete from Bookmark where scholarship_idx = ( " +
                    "select * from ( " +
                    "select Scholarship.scholarship_idx from Bookmark " +
                    "join Scholarship " +
                    "on Bookmark.scholarship_idx = Scholarship.scholarship_idx " +
                    "left join Total " +
                    "on Total.scholarship_idx = Scholarship.scholarship_idx " +
                    "where Bookmark.user_idx = ? " +
                    "and Total.total_idx = ? ) A ) ";

            this.jdbcTemplate.update(postBookmarkTotalQuery, postBookmarkTotalParams);
            return "삭제";

        } else if (nullCheckSupportForTotal == 1) {

            postBookmarkTotalQuery = "delete from Bookmark where support_idx = ( " +
                    "select * from ( " +
                    "select Support.support_idx from Bookmark " +
                    "join Support " +
                    "on Bookmark.support_idx = Support.support_idx " +
                    "left join Total " +
                    "on Total.fund_idx = Support.support_idx " +
                    "where Bookmark.user_idx = ? " +
                    "and Total.total_idx = ? ) A ) ";

            this.jdbcTemplate.update(postBookmarkTotalQuery, postBookmarkTotalParams);
            return "삭제";

        } else if (nullCheckSchoolForTotal == 1) {

            postBookmarkTotalQuery = "delete from Bookmark where school_idx = ( " +
                    "select * from ( " +
                    "select School.school_idx from Bookmark " +
                    "join School " +
                    "on School.school_idx = Bookmark.school_idx " +
                    "left join Total " +
                    "on Total.scholarship_idx = School.scholarship_idx " +
                    "where School.user_idx = ? " +
                    "and Total.total_idx = ? ) A ) ";

            this.jdbcTemplate.update(postBookmarkTotalQuery, postBookmarkTotalParams);
            return "삭제";

        } else {
            postBookmarkTotalQuery = "insert into Bookmark (user_idx, total_idx) " +
                    "VALUES (?, ?) ";

            this.jdbcTemplate.update(postBookmarkTotalQuery, postBookmarkTotalParams);
            return "추가";
        }

    }

}
