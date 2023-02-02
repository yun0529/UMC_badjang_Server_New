package com.example.demo.src.board_registration;

import com.example.demo.src.board.school.model.GetOneOfSchoolBoardRes;
import com.example.demo.src.board.school.model.GetSchoolBoardRes;
import com.example.demo.src.board.school.model.PatchSchoolBoardReq;
import com.example.demo.src.board.school.model.PostSchoolBoardReq;
import com.example.demo.src.board_registration.model.PostBoardRegistrationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BoardRegistrationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void postBoardRegistration(PostBoardRegistrationReq postBoardRegistrationReq) {

        String postBoardRegistrationQuery = "insert into Board_Registration (" +
                "requester_name, " +
                "requester_univ, " +
                "requester_job, " +
                "requester_phone, " +
                "board_name, " +
                "board_purpose, " +
                "board_rule )" +
                "VALUES (?,?,?,?,?,?,?)";

        Object[] postBoardRegistrationParams = new Object[]{
                postBoardRegistrationReq.getRequester_name(),
                postBoardRegistrationReq.getRequester_univ(),
                postBoardRegistrationReq.getRequester_job(),
                postBoardRegistrationReq.getRequester_phone(),
                postBoardRegistrationReq.getBoard_name(),
                postBoardRegistrationReq.getBoard_purpose(),
                postBoardRegistrationReq.getBoard_rule()
        };

        this.jdbcTemplate.update(postBoardRegistrationQuery, postBoardRegistrationParams);


    }


}
