package com.example.demo.src.inquiry;

import com.example.demo.src.inquiry.model.GetInquiryRes;
import com.example.demo.src.inquiry.model.PatchInquiryReq;
import com.example.demo.src.inquiry.model.PostInquiryReq;
import com.example.demo.src.inquiry.model.PostInquiryRes;
import com.example.demo.src.popularBoard.model.GetPopularRes;
import com.example.demo.src.support_comment.model.PatchSupportCommentReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class InquiryDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 문의 목록 전체 조회
     */
    public List<GetInquiryRes> getInquiries(){
        String getInquiriesQuery = "SELECT inquiry_idx, user_idx, inquiry_title, inquiry_content, " +
                "inquiry_image, inquiry_createAt, inquiry_updateAt FROM Inquiry where inquiry_status = 'Y' order by inquiry_createAt DESC " ;

        return this.jdbcTemplate.query(getInquiriesQuery, (rs, rowNum) -> new GetInquiryRes(
                rs.getInt("inquiry_idx"),
                rs.getInt("user_idx"),
                rs.getString("inquiry_title"),
                rs.getString("inquiry_content"),
                rs.getString("inquiry_image"),
                rs.getString("inquiry_createAt"),
                rs.getString("inquiry_updateAt")
        ));
    }

    /**idx로 문의 하나 조회*/
    public GetInquiryRes getInquiryByIdx(Integer inquiryIdx) {
        String getInquiryByIdxQuery = "SELECT inquiry_idx, user_idx, inquiry_title, inquiry_content, inquiry_image, inquiry_createAt, inquiry_updateAt" +
                " FROM Inquiry WHERE inquiry_status = 'Y' and inquiry_idx = ?";

        return this.jdbcTemplate.queryForObject(getInquiryByIdxQuery, (rs, rowNum) -> new GetInquiryRes(
                rs.getInt("inquiry_idx"),
                rs.getInt("user_idx"),
                rs.getString("inquiry_title"),
                rs.getString("inquiry_content"),
                rs.getString("inquiry_image"),
                rs.getString("inquiry_createAt"),
                rs.getString("inquiry_updateAt")),
                inquiryIdx);
    }

    /**문의 생성*
     */
    public Integer createInquiry(PostInquiryReq postInquiryReq) {
        String createInquiryQuery = "INSERT INTO Inquiry(user_idx, inquiry_title, inquiry_content, inquiry_image) VALUES(?, ?, ?, ?)";
        Object[] createInquiryQueryParams = new Object[]{postInquiryReq.getUser_idx(), postInquiryReq.getInquiry_title(), postInquiryReq.getInquiry_content(), postInquiryReq.getInquiry_image()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createInquiryQuery, createInquiryQueryParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 BoardIdx번호를 반환한다.
    }

    /**
     * 댓글 수정 API
     */
    public int modifyInquiry(PatchInquiryReq patchInquiryReq) {
        String modifyInquiryQuery = "update Inquiry set inquiry_title = ?, inquiry_content = ?, inquiry_image = ? where inquiry_idx = ? ";
        Object[] modifyInquiryParams = new Object[]{patchInquiryReq.getInquiry_title(),patchInquiryReq.getInquiry_content(), patchInquiryReq.getInquiry_image(), patchInquiryReq.getInquiry_idx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyInquiryQuery, modifyInquiryParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    /**
     * 문의 삭제 API
     */
    public int deleteInquiry(Integer inquiry_idx) {
        String deleteInquiryQuery = "update Inquiry set inquiry_status = 'N' where inquiry_idx = ?";
        Object[] deleteInquiryParams = new Object[]{inquiry_idx};

        return this.jdbcTemplate.update(deleteInquiryQuery,deleteInquiryParams);

    }
}
