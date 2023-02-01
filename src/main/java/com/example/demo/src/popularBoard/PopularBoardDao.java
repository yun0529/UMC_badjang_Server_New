package com.example.demo.src.popularBoard;

import com.example.demo.src.popularBoard.model.GetPopularRes;
import com.example.demo.src.popularBoard.model.PostPopularReq;
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
        String getPopularQuery = "SELECT popular_idx, post_idx, user_idx, school_name_idx, " +
                "popular_createAt, popular_updateAt, popular_status FROM Popular_Board order by count DESC " ;

        return this.jdbcTemplate.query(getPopularQuery, (rs, rowNum) -> new GetPopularRes(
                rs.getInt("popular_idx"),
                rs.getInt("post_idx"),
                rs.getInt("user_idx"),
                rs.getInt("school_name_idx"),
                rs.getString("popular_createAt"),
                rs.getString("popular_updateAt"),
                rs.getString("popular_status")
                ));
    }

    public List<GetPopularRes> getPopular(){
        String getPopularQuery = "SELECT popular_idx, post_idx, user_idx, school_name_idx, " +
                "popular_createAt, popular_updateAt, popular_status FROM Popular_Board order by count DESC LIMIT 2" ;

        return this.jdbcTemplate.query(getPopularQuery, (rs, rowNum) -> new GetPopularRes(
                rs.getInt("popular_idx"),
                rs.getInt("post_idx"),
                rs.getInt("user_idx"),
                rs.getInt("school_name_idx"),
                rs.getString("popular_createAt"),
                rs.getString("popular_updateAt"),
                rs.getString("popular_status")
        ));
    }

    public PostPopularRes postPopular(){
        String postPopularQuery = "INSERT INTO Popular_Board(post_idx, user_idx, school_name_idx, popular_createAt, popular_updateAt, popular_status, count) " +
                "(select Board.post_idx, Board.user_idx, Board.school_name_idx, Board.post_createAt, Board.post_updateAt, Board.post_status, " +
                "Board.post_recommend + Board.post_view from Board ) " ;

        this.jdbcTemplate.update(postPopularQuery);
        return null;
    }

    public GetPopularRes removeDup(){
        String deleteQuery = "DELETE a FROM Popular_Board a, Popular_Board b " +
                "WHERE a.popular_idx > b.popular_idx AND a.post_idx = b.post_idx " ;

        this.jdbcTemplate.update(deleteQuery);
        return null;
    }
}
