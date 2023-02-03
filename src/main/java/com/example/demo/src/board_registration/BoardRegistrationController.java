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
            jwtService.getUserIdx();
            if (postBoardRegistrationReq.getBoard_name() == null || postBoardRegistrationReq.getBoard_name().equals("") ||
                    postBoardRegistrationReq.getBoard_purpose() == null || postBoardRegistrationReq.getBoard_purpose().equals("") ||
                    postBoardRegistrationReq.getBoard_rule() == null || postBoardRegistrationReq.getBoard_rule().equals("") ||
                    postBoardRegistrationReq.getRequester_job() == null || postBoardRegistrationReq.getRequester_job().equals("") ||
                    postBoardRegistrationReq.getRequester_phone() == null || postBoardRegistrationReq.getRequester_phone().equals("") ||
                    postBoardRegistrationReq.getRequester_univ() == null || postBoardRegistrationReq.getRequester_univ().equals("") ||
                    postBoardRegistrationReq.getRequester_name() == null || postBoardRegistrationReq.getRequester_name().equals("") ) {
                return new BaseResponse<>(POST_BOARD_REGISTRATION_NULL);
            }
            if (postBoardRegistrationReq.getBoard_purpose().length() > 500) {
                return new BaseResponse<>(POST_BOARD_REGISTRATION_PURPOSE_INVALID);
            }
            if (postBoardRegistrationReq.getBoard_purpose().length() < 10) {
                return new BaseResponse<>(POST_BOARD_REGISTRATION_PURPOSE_INVALID);
            }
            if (postBoardRegistrationReq.getBoard_rule().length() > 500) {
                return new BaseResponse<>(POST_BOARD_REGISTRATION_RULE_INVALID);
            }
            if (postBoardRegistrationReq.getBoard_rule().length() < 10) {
                return new BaseResponse<>(POST_BOARD_REGISTRATION_RULE_INVALID);
            }


            boardRegistrationService.postBoardRegistration(postBoardRegistrationReq);

            return new BaseResponse<>(POST_BOARD_REGISTRATION_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
