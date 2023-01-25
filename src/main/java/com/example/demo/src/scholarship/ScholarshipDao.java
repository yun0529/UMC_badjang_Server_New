package com.example.demo.src.scholarship;

import com.example.demo.src.scholarship.model.GetScholarshipMyfilter;
import com.example.demo.src.scholarship.model.GetScholarshipRes;
import com.example.demo.src.scholarship.model.PostScholarshipReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.List;


@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]

/**
 * DAO란?
 * 데이터베이스 관련 작업을 전담하는 클래스
 * 데이터베이스에 연결하여, 입력 , 수정, 삭제, 조회 등의 작업을 수행
 */
public class ScholarshipDao {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    // 장학금 조회수 1증가
    public int increaseScholarshipView(long scholarshipIdx) {
        String increaseScholarshipViewQuery = "update Scholarship set scholarship_view = scholarship_view + 1 where scholarship_idx = ? "; // 해당 scholarshipIdx를 만족하는 Scholarship을 1증가한다.
        Object[] increaseScholarshipViewQueryParams = new Object[]{scholarshipIdx}; // 주입될 값

        return this.jdbcTemplate.update(increaseScholarshipViewQuery, increaseScholarshipViewQueryParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 해당 filter에 맞는 장학금의 정보 조회
    public List<GetScholarshipRes> getScholarshipsByFilter(@RequestParam(required = false)Integer category, @RequestParam(required = false)Integer filter, @RequestParam(required = false)Integer order) {

        String getScholarshipsByFilterQuery="select * from Scholarship where scholarship_status = 'Y'";

        String categoryCondition="";
        String filterCondition="";
        String orderCondition="";

        if(category==null){
            categoryCondition = "";
        }
        else if(category==1){
            categoryCondition = " and scholarship_category = '교내 재학생 장학금'";
        }
        else if(category==2){
            categoryCondition = " and scholarship_category = '교내 신입생 입학성적 우수장학금'";
        }

        if(filter==null){
            filterCondition = " order by scholarship_view";
        }
        else if(filter==1){
            filterCondition = " order by scholarship_createAt";
        }
        else if(filter==2){
            filterCondition = " order by scholarship_view";
        }
        else if(filter==3){
            filterCondition = " order by scholarship_comment";
        }

        if(order==null){
            orderCondition = " desc";
        }
        else if(order==1){
            orderCondition = " asc";
        }

        getScholarshipsByFilterQuery = getScholarshipsByFilterQuery + categoryCondition +filterCondition + orderCondition;


        return this.jdbcTemplate.query(getScholarshipsByFilterQuery,

                (rs, rowNum) -> new GetScholarshipRes(
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
                        rs.getString("scholarship_category")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                );
    }

    // 해당 scholarshipIdx를 갖는 유저조회
    public GetScholarshipRes getScholarship(long scholarshipIdx) {
        String getScholarshipQuery = "select * from Scholarship where scholarship_status = 'Y' and scholarship_Idx = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        long getScholarshipParams = scholarshipIdx;
        return this.jdbcTemplate.queryForObject(getScholarshipQuery,
                (rs, rowNum) -> new GetScholarshipRes(
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
                        rs.getString("scholarship_category")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getScholarshipParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 장학금 idx가 존재하는지 확인
    public int checkScholarshipIdx(long scholarshipidx) {
        String checkScholarshipIdxQuery = "select exists(select scholarship_idx from Scholarship where scholarship_idx = ? and scholarship_status = 'Y')"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        long checkScholarshipIdxParams = scholarshipidx; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkScholarshipIdxQuery,
                int.class,
                checkScholarshipIdxParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 장학금 추가
    public long createScholarship(PostScholarshipReq postScholarshipReq) {
        String createScholarshipQuery = "insert into Scholarship(" +
                "scholarship_name, " +
                "scholarship_institution, " +
                "scholarship_content, " +
                "scholarship_image, " +
                "scholarship_homepage, " +
                "scholarship_scale, " +
                "scholarship_term, " +
                "scholarship_presentation, " +
                "scholarship_univ, " +
                "scholarship_college, " +
                "scholarship_department, " +
                "scholarship_grade, " +
                "scholarship_semester, " +
                "scholarship_province, " +
                "scholarship_city, " +
                "scholarship_category) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createScholarshipParams = new Object[]{
                postScholarshipReq.getScholarship_name(),
                postScholarshipReq.getScholarship_institution(),
                postScholarshipReq.getScholarship_content(),
                postScholarshipReq.getScholarship_image(),
                postScholarshipReq.getScholarship_homepage(),
                postScholarshipReq.getScholarship_scale(),
                postScholarshipReq.getScholarship_term(),
                postScholarshipReq.getScholarship_presentation(),
                postScholarshipReq.getScholarship_univ(),
                postScholarshipReq.getScholarship_college(),
                postScholarshipReq.getScholarship_department(),
                postScholarshipReq.getScholarship_grade(),
                postScholarshipReq.getScholarship_semester(),
                postScholarshipReq.getScholarship_province(),
                postScholarshipReq.getScholarship_city(),
                postScholarshipReq.getScholarship_category()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createScholarshipQuery, createScholarshipParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 장학금의 Idx번호를 반환한다.
    }

    public List<GetScholarshipRes> getScholarshipMyfilter(GetScholarshipMyfilter getScholarshipMyfilter) {

        String MyfilterQuery = "select * from Scholarship where scholarship_status = 'Y'";

        String university = getScholarshipMyfilter.getScholarship_univ();
        String college = getScholarshipMyfilter.getScholarship_college();
        String department = getScholarshipMyfilter.getScholarship_department();
        Integer grade = getScholarshipMyfilter.getScholarship_grade();
        Integer semester = getScholarshipMyfilter.getScholarship_semester();
        String province = getScholarshipMyfilter.getScholarship_province();
        String city = getScholarshipMyfilter.getScholarship_city();

        String universityQuery = "";
        String collegeQuery= "";
        String departmentQuery= "";
        String gradeQuery= "";
        String semesterQuery= "";
        String provinceQuery= "";
        String cityQuery= "";

        if(university == null) {
            universityQuery = " and 1 = ?";
            university = "1";
        } else {
            universityQuery = " and scholarship_univ = ?";
        }

        if(college == null) {
            college = "1";
            collegeQuery = " and 1 = ?";
        } else {
            collegeQuery = " and scholarship_college = ?";
        }

        if(department == null) {
            departmentQuery = " and 1 = ?";
            department= "1";
        } else {
            departmentQuery = " and scholarship_department = ?";
        }

        if(grade == null) {
            gradeQuery = " and 1 = ?";
            grade = 1;
        } else {
            gradeQuery = " and scholarship_grade = ?";
        }

        if(semester == null) {
            semester = 1;
            semesterQuery = " and 1 = ?";
        } else {
            semesterQuery = " and scholarship_semester = ?";
        }

        if(province == null) {
            provinceQuery = " and 1 = ?";
            province = "1";
        } else {
            provinceQuery = " and scholarship_province = ?";
        }

        if(city == null) {
            cityQuery = " and 1 = ?";
            city = "1";
        } else {
            cityQuery = " and scholarship_city = ?";
        }

        System.out.println(university+college+department+grade+semester+province+city);

        MyfilterQuery = MyfilterQuery + universityQuery + collegeQuery + departmentQuery
                + gradeQuery + semesterQuery + provinceQuery + cityQuery;

        return this.jdbcTemplate.query(MyfilterQuery,
                (rs, rowNum) -> new GetScholarshipRes(
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
                        rs.getString("scholarship_category")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                university,college,department,grade,semester,province,city); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }
}
