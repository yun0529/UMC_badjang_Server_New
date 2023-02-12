package com.example.demo.src.popularBoard;

import com.example.demo.src.popularBoard.model.GetPopularRes;
import com.example.demo.src.popularBoard.model.PostPopularRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PopularBoardDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 인기글 게시판 전체 조회
     * @return
     */
    public List<GetPopularRes> getPopularAll(){
        String getPopularQuery = "SELECT popular_idx, post_idx, user_idx, school_name_idx, popular_content, popular_createAt, " +
                "popular_updateAt, popular_status, count, " +
                "IF(post_ananymity = 'Y', Popular_Board.user_name = null, Popular_Board.user_name) as user_name, " +
                "board_category, post_anonymity, user_profileimage_url  " +
                "FROM Popular_Board order by count DESC ";

        return this.jdbcTemplate.query(getPopularQuery, (rs, rowNum) -> new GetPopularRes(
                rs.getInt("popular_idx"),
                rs.getInt("post_idx"),
                rs.getInt("user_idx"),
                rs.getInt("school_name_idx"),
                rs.getString("popular_content"),
                rs.getString("popular_createAt"),
                rs.getString("popular_updateAt"),
                rs.getString("popular_status"),
                rs.getInt("count"),
                rs.getString("user_name"),
                rs.getString("board_category"),
                rs.getString("post_anonymity"),
                rs.getString("user_profileimage_url")
                ));
    }

    public List<GetPopularRes> getPopular(){
        String getPopularQuery = "SELECT popular_idx, post_idx, user_idx, school_name_idx, popular_content, popular_createAt, " +
                "popular_updateAt, popular_status, count, " +
                "IF(post_ananymity = 'Y', Popular_Board.user_name = null, Popular_Board.user_name) as user_name, " +
                "board_category, post_anonymity, user_profileimage_url  " +
                "FROM Popular_Board order by count DESC LIMIT 2" ;

        return this.jdbcTemplate.query(getPopularQuery, (rs, rowNum) -> new GetPopularRes(
                rs.getInt("popular_idx"),
                rs.getInt("post_idx"),
                rs.getInt("user_idx"),
                rs.getInt("school_name_idx"),
                rs.getString("popular_content"),
                rs.getString("popular_createAt"),
                rs.getString("popular_updateAt"),
                rs.getString("popular_status"),
                rs.getInt("count"),
                rs.getString("user_name"),
                rs.getString("board_category"),
                rs.getString("post_anonymity"),
                rs.getString("user_profileimage_url")
        ));
    }

    public PostPopularRes postPopular(){
        String postPopularQuery = "INSERT INTO Popular_Board(post_idx, user_idx, school_name_idx, popular_createAt, popular_updateAt, popular_status, count, user_name, board_category, user_profileimage_url, post_ananymity) " +
                "(select Board.post_idx, Board.user_idx, Board.school_name_idx, Board.post_createAt, Board.post_updateAt, Board.post_status, " +
                "Board.post_recommend + Board.post_view, Board.user_name, Board.post_category, Board.user_profileimage_url, Board.post_anonymity from Board) " ;

        this.jdbcTemplate.update(postPopularQuery);
        return null;
    }

    public GetPopularRes removeDup(){
        String deleteQuery = "TRUNCATE Popular_Board";

        this.jdbcTemplate.update(deleteQuery);
        return null;
    }
}
