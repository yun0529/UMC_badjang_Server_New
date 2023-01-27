package com.example.demo.src.scholarship_comment;

import com.example.demo.src.scholarship_comment.model.DeleteScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.GetScholarshipCommentRes;
import com.example.demo.src.scholarship_comment.model.PatchScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ScholarshipCommentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    /**
     * 댓글 작성 API
     */
    public Integer createScholarshipComment(PostScholarshipCommentReq postScholarshipCommentReq) {
        String createScholarshipCommentQuery = "insert into Scholarship_Comment(scholarship_idx, user_idx, scholarship_comment_content) VALUES (?,?,?)"; // 실행될 동적 쿼리문
        Object[] createScholarshipCommentParams = new Object[]{postScholarshipCommentReq.getScholarship_idx(), postScholarshipCommentReq.getUser_idx(), postScholarshipCommentReq.getScholarship_comment_content()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createScholarshipCommentQuery, createScholarshipCommentParams);

        String scholarshipCommentCountUpQuery = "update Scholarship set scholarship_comment = scholarship_comment + 1 where scholarship_idx = ?";
        int scholarshipCommentCountUpParams = postScholarshipCommentReq.getScholarship_idx();
        this.jdbcTemplate.update(scholarshipCommentCountUpQuery,scholarshipCommentCountUpParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 BoardIdx번호를 반환한다.
    }

    /**
     * 댓글 조회 API
     */
    public List<GetScholarshipCommentRes> getScholarshipComment(Integer scholarship_idx) {
        String getScholarshipCommentQuery = "select * from Scholarship_Comment where scholarship_idx = ? and scholarship_comment_status = 'Y'";
        Integer getScholarshipCommentParams = scholarship_idx;
        return this.jdbcTemplate.query(getScholarshipCommentQuery,
                (rs, rowNum) -> new GetScholarshipCommentRes(
                        rs.getInt("scholarship_comment_idx"),
                        rs.getInt("scholarship_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("scholarship_comment_content"),
                        rs.getString("scholarship_comment_updateAt")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getScholarshipCommentParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }


    /**
     * 댓글 수정 API
     */
    public int modifyScholarshipComment(PatchScholarshipCommentReq patchScholarshipCommentReq) {
        String modifyScholarshipCommentQuery = "update Scholarship_Comment set scholarship_comment_content = ? where scholarship_comment_idx = ? ";
        Object[] modifyScholarshipCommentParams = new Object[]{patchScholarshipCommentReq.getScholarship_comment_content(),patchScholarshipCommentReq.getScholarship_comment_idx()};

        return this.jdbcTemplate.update(modifyScholarshipCommentQuery, modifyScholarshipCommentParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    /**
     * 댓글 삭제 API
     */
    public int deleteScholarshipComment(Integer scholarship_idx, Integer scholarship_comment_idx) {

        String scholarshipCommentCountDownQuery = "update Scholarship set scholarship_comment = scholarship_comment - 1 where scholarship_idx = ?";
        this.jdbcTemplate.update(scholarshipCommentCountDownQuery,scholarship_idx);

        String deleteScholarshipCommentQuery = "update Scholarship_Comment set scholarship_comment_status = 'N' where scholarship_comment_idx = ?";
        Object[] deleteScholarshipCommentParams = new Object[]{scholarship_comment_idx};

        return this.jdbcTemplate.update(deleteScholarshipCommentQuery,deleteScholarshipCommentParams);

    }

}
