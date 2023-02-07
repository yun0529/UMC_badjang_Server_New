package com.example.demo.src.FAQ;

import com.example.demo.src.FAQ.model.GetFAQRes;
import com.example.demo.src.FAQ.model.PostFAQReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.List;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]


public class FAQDao {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************


    // 자주묻는 질문 전체 조회
    public List<GetFAQRes> getFAQs() {

        String getFAQsQuery = "select * from FAQ where FAQ_status = 'Y'";

        return this.jdbcTemplate.query(getFAQsQuery,
                (rs, rowNum) -> new GetFAQRes(
                        rs.getLong("FAQ_idx"),
                        rs.getString("FAQ_title"),
                        rs.getString("FAQ_content"),
                        rs.getString("FAQ_image"),
                        rs.getString("FAQ_createAt"),
                        rs.getString("FAQ_updateAt"),
                        rs.getString("FAQ_status"))
        );
    }

    // 해당 FAQ Idx를 갖는 FAQ조회
    public GetFAQRes getFAQ(long FAQIdx) {
        String getFAQQuery = "select * from FAQ where FAQ_status = 'Y' and FAQ_Idx = ?";
        long getFAQParams = FAQIdx;
        return this.jdbcTemplate.queryForObject(getFAQQuery,
                (rs, rowNum) -> new GetFAQRes(
                        rs.getLong("FAQ_idx"),
                        rs.getString("FAQ_title"),
                        rs.getString("FAQ_content"),
                        rs.getString("FAQ_image"),
                        rs.getString("FAQ_createAt"),
                        rs.getString("FAQ_updateAt"),
                        rs.getString("FAQ_status")),
                getFAQParams);
    }

    // FAQ idx가 존재하는지 확인
    public int checkFAQIdx(long FAQidx) {
        String checkFAQIdxQuery = "select exists(select FAQ_idx from FAQ where FAQ_idx = ? and FAQ_status = 'Y')"; // FAQ Table에 해당 idx 값을 갖는 유저 정보가 존재하는가?
        long checkFAQIdxParams = FAQidx; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkFAQIdxQuery,
                int.class,
                checkFAQIdxParams);
    }

    // FAQ 작성
    public long createFAQ(PostFAQReq postFAQReq) {
        String createFAQQuery = "insert into FAQ(" +
                "FAQ_title, " +
                "FAQ_content, " +
                "FAQ_image) VALUES (?,?,?)"; // 실행될 동적 쿼리문
        Object[] createFAQParams = new Object[]{
                postFAQReq.getFAQ_title(),
                postFAQReq.getFAQ_content(),
                postFAQReq.getFAQ_image()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createFAQQuery, createFAQParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 장학금의 Idx번호를 반환한다.
    }
}
