package com.example.demo.src.board_registration;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.school.SchoolBoardProvider;
import com.example.demo.src.board.school.SchoolBoardService;
import com.example.demo.src.board.school.model.*;
import com.example.demo.src.board_registration.model.PostBoardRegistrationReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
public class BoardRegistrationController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private final BoardRegistrationService boardRegistrationService;


    private final JwtService jwtService;

    public BoardRegistrationController(BoardRegistrationService boardRegistrationService, JwtService jwtService) {
        this.boardRegistrationService = boardRegistrationService;
        this.jwtService = jwtService;
    }


    /**
     * 게시판 만들기 요청
     * [POST] /board-registration
     */

    @ResponseBody
    @PostMapping("/board-registration")
    public BaseResponse<String> postBoardRegistration(@RequestBody PostBoardRegistrationReq postBoardRegistrationReq) {
        try {
            int userIdx = jwtService.getUserIdx();
            boardRegistrationService.postBoardRegistration(postBoardRegistrationReq);

            return new BaseResponse<>(POST_BOARD_REGISTRATION_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
