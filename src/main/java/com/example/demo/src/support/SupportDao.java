package com.example.demo.src.support;

import com.example.demo.src.support.model.GetSupportRes;
import com.example.demo.src.support.model.PostSupportReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.List;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]

public class SupportDao {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    // 지원금 조회수 1증가
    public int increaseSupportView(long supportIdx) {
        String increaseSupportViewQuery = "update Support set support_view = support_view + 1 where support_idx = ? "; // 해당 scholarshipIdx를 만족하는 Scholarship을 1증가한다.
        Object[] increaseSupportViewQueryParams = new Object[]{supportIdx}; // 주입될 값

        return this.jdbcTemplate.update(increaseSupportViewQuery, increaseSupportViewQueryParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 해당 filter에 맞는 지원금의 정보 조회
    public List<GetSupportRes> getSupportsByFilter(@RequestParam(required = false)Integer category, @RequestParam(required = false)Integer filter, @RequestParam(required = false)Integer order) {

        String getSupportsByFilterQuery="select * from Support where support_status = 'Y'";

        String categoryCondition="";
        String filterCondition="";
        String orderCondition="";

        if(category==null){
            categoryCondition = "";
        }
        else if(category==1){
            categoryCondition = " and support_category = '창업지원'";
        }
        else if(category==2){
            categoryCondition = " and support_category = '취업지원'";
        }
        else if(category==3){
            categoryCondition = " and support_category = '경기 포천시 창업 지원사업'";
        }

        if(filter==null){
            filterCondition = " order by support_view";
        }
        else if(filter==1){
            filterCondition = " order by support_createAt";
        }
        else if(filter==2){
            filterCondition = " order by support_view";
        }
        else if(filter==3){
            filterCondition = " order by support_comment";
        }

        if(order==null){
            orderCondition = " desc";
        }
        else if(order==1){
            orderCondition = " asc";
        }

        getSupportsByFilterQuery = getSupportsByFilterQuery + categoryCondition +filterCondition + orderCondition;


        return this.jdbcTemplate.query(getSupportsByFilterQuery,

                (rs, rowNum) -> new GetSupportRes(
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
                        rs.getString("support_category"))
        );
    }

    // 해당 supportIdx를 갖는 지원금조회
    public GetSupportRes getSupport(long supportIdx) {
        String getSupportQuery = "select * from Support where support_status = 'Y' and support_Idx = ?";
        long getSupportParams = supportIdx;
        return this.jdbcTemplate.queryForObject(getSupportQuery,
                (rs, rowNum) -> new GetSupportRes(
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
                        rs.getString("support_category")),
                getSupportParams);
    }

    // 지원금 idx가 존재하는지 확인
    public int checkSupportIdx(long supportidx) {
        String checkSupportIdxQuery = "select exists(select support_idx from Support where support_idx = ? and support_status = 'Y')";
        long checkSupportIdxParams = supportidx; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkSupportIdxQuery,
                int.class,
                checkSupportIdxParams);
    }

    // 지원금 추가
    public long createSupport(PostSupportReq postSupportReq) {
        String createSupportQuery = "insert into Support(" +
                "support_policy, " +
                "support_name, " +
                "support_institution, " +
                "support_content, " +
                "support_image, " +
                "support_homepage, " +
                "support_scale, " +
                "support_term, " +
                "support_presentation, " +
                "support_province, " +
                "support_city, " +
                "support_univ, " +
                "support_college, " +
                "support_department, " +
                "support_grade, " +
                "support_semester, " +
                "support_category) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createSupportParams = new Object[]{
                postSupportReq.getSupport_policy(),
                postSupportReq.getSupport_name(),
                postSupportReq.getSupport_institution(),
                postSupportReq.getSupport_content(),
                postSupportReq.getSupport_image(),
                postSupportReq.getSupport_homepage(),
                postSupportReq.getSupport_scale(),
                postSupportReq.getSupport_term(),
                postSupportReq.getSupport_presentation(),
                postSupportReq.getSupport_province(),
                postSupportReq.getSupport_city(),
                postSupportReq.getSupport_univ(),
                postSupportReq.getSupport_college(),
                postSupportReq.getSupport_department(),
                postSupportReq.getSupport_grade(),
                postSupportReq.getSupport_semester(),
                postSupportReq.getSupport_category()};
        this.jdbcTemplate.update(createSupportQuery, createSupportParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 지원금의 Idx번호를 반환한다.
    }
}
