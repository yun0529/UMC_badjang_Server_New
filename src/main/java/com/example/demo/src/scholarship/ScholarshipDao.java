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
    public List<GetScholarshipRes> getScholarshipsByFilter(@RequestParam(required = false)String category, @RequestParam(required = false)String filter, @RequestParam(required = false)String order) {

        String getScholarshipsByFilterQuery="select * from Scholarship where scholarship_status = 'Y'";

        String categoryCondition="";
        String filterCondition="";
        String orderCondition="";

        if(category.equals("")){
            categoryCondition = "";
        }
        else if(category.equals("국가장학")){
            categoryCondition = " and (scholarship_category = '국가장학' or scholarship_category = '국가장학금')";
        }
        else if(category.equals("KRA와 함께하는 농어촌 희망재단 장학금")){
            categoryCondition = " and (scholarship_category = 'KRA와 함께하는 농어촌 희망재단 장학' or scholarship_category = 'KRA와 함께하는 농어촌 희망재단 장학금')";
        }
        else if(category.equals("교내 신입생 입학성적 우수장학금")){
            categoryCondition = " and (scholarship_category = '교내 신입생 입학성적 우수장학' or scholarship_category = '교내 신입생 입학성적 우수장학금')";
        }
        else if(category.equals("교내 재학생 장학금")){
            categoryCondition = " and (scholarship_category = '교내 재학생 장학' or scholarship_category = '교내 재학생 장학금')";
        }
        else if(category.equals("교외장학")){
            categoryCondition = " and (scholarship_category = '교외장학' or scholarship_category = '교외장학금')";
        }
        else if(category.equals("교내장학")){
            categoryCondition = " and (scholarship_category = '교내장학' or scholarship_category = '교내장학금')";
        }
        else if(category.equals("학비대출")){
            categoryCondition = " and scholarship_category = '학비대출'";
        }
        else if(category.equals("기타")){
            categoryCondition = " and scholarship_category = '기타'";
        }
        else if(category.equals("국가근로")){
            categoryCondition = " and scholarship_category = '국가근로'";
        }
        else if(category.equals("성적우수장학금")){
            categoryCondition = " and (scholarship_category = '성적우수장학' or scholarship_category = '성적우수장학금')";
        }
        else if(category.equals("특별감면장학금")){
            categoryCondition = " and (scholarship_category = '특별감면장학' or scholarship_category = '특별감면장학금')";
        }
        else if(category.equals("가계곤란자 장학금")){
            categoryCondition = " and (scholarship_category = '가계곤란자 장학' or scholarship_category = '가계곤란자 장학금')";
        }
        else if(category.equals("근로 장학금")){
            categoryCondition = " and (scholarship_category = '근로 장학' or scholarship_category = '근로 장학금')";
        }

        if(filter.equals("") || filter.equals("인기순")){
            filterCondition = " order by scholarship_view";
        }
        else if(filter.equals("날짜순")){
            filterCondition = " order by scholarship_createAt";
        }
        else if(filter.equals("댓글순")){
            filterCondition = " order by scholarship_comment";
        }

        if(order.equals("") || order.equals("desc")){
            orderCondition = " desc";
        }
        else if(order.equals("asc")){
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
                        rs.getString("scholarship_category"))
                );
    }

    // 해당 scholarshipIdx를 갖는 장학금조회
    public GetScholarshipRes getScholarship(long scholarshipIdx) {
        String getScholarshipQuery = "select * from Scholarship where scholarship_status = 'Y' and scholarship_Idx = ?";
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
                        rs.getString("scholarship_category")),
                getScholarshipParams);
    }

    // 장학금 idx가 존재하는지 확인
    public int checkScholarshipIdx(long scholarshipidx) {
        String checkScholarshipIdxQuery = "select exists(select scholarship_idx from Scholarship where scholarship_idx = ? and scholarship_status = 'Y')"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        long checkScholarshipIdxParams = scholarshipidx; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkScholarshipIdxQuery,
                int.class,
                checkScholarshipIdxParams);
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
                "scholarship_createAt, " +
                "scholarship_univ, " +
                "scholarship_college, " +
                "scholarship_department, " +
                "scholarship_grade, " +
                "scholarship_semester, " +
                "scholarship_province, " +
                "scholarship_city, " +
                "scholarship_category) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createScholarshipParams = new Object[]{
                postScholarshipReq.getScholarship_name(),
                postScholarshipReq.getScholarship_institution(),
                postScholarshipReq.getScholarship_content(),
                postScholarshipReq.getScholarship_image(),
                postScholarshipReq.getScholarship_homepage(),
                postScholarshipReq.getScholarship_scale(),
                postScholarshipReq.getScholarship_term(),
                postScholarshipReq.getScholarship_presentation(),
                postScholarshipReq.getScholarship_createAt(),
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
        String semester = getScholarshipMyfilter.getScholarship_semester();
        String province = getScholarshipMyfilter.getScholarship_province();
        String city = getScholarshipMyfilter.getScholarship_city();

        String universityQuery = "";
        String collegeQuery= "";
        String departmentQuery= "";
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
            department = "%"+department+"%";
            departmentQuery = " and scholarship_department like ?";
        }


        if(semester == null && grade == null) {
            semester = "1";
            semesterQuery = " and 1 = ?";
        }
        else if(grade != null && semester == null) {
            if(grade == 1) {
                semester = "%2%";
                semesterQuery = " and (scholarship_semester like ? or scholarship_semester like '%1%')";
            }
            else if(grade == 2) {
                semester = "%4%";
                semesterQuery = " and (scholarship_semester like ? or scholarship_semester like '%3%')";
            }
            else if(grade == 3) {
                semester = "%6%";
                semesterQuery = " and (scholarship_semester like ? or scholarship_semester like '%5%')";
            }
            else {
                semester = "%8%";
                semesterQuery = " and (scholarship_semester like ? or scholarship_semester like '%7%')";
            }
        }
        else if(grade == null && semester != null) {
            if(semester == "1") {
                semester = "%1%";
                semesterQuery = " and (scholarship_semester like ? or scholarship_semester like '%3%' or scholarship_semester like '%5%' or scholarship_semester like '%7%')";
            }
            else{
                semester = "%2%";
                semesterQuery = " and (scholarship_semester like ? or scholarship_semester like '%4%' or scholarship_semester like '%6%' or scholarship_semester like '%8%')";
            }

        }
        else {
            semester = "%" + Integer.toString((grade - 1) * 2 + Integer.valueOf(semester)) + "%";
            semesterQuery = " and scholarship_semester like ?";
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

        MyfilterQuery = MyfilterQuery + universityQuery + collegeQuery + departmentQuery
                + semesterQuery + provinceQuery + cityQuery;

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
                university,college,department,semester,province,city); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }
}
