package com.example.demo.src.board_registration;


import com.example.demo.config.BaseException;
import com.example.demo.src.board.school.SchoolBoardCommentDao;
import com.example.demo.src.board.school.SchoolBoardDao;
import com.example.demo.src.board.school.model.PatchSchoolBoardCommentReq;
import com.example.demo.src.board.school.model.PatchSchoolBoardReq;
import com.example.demo.src.board.school.model.PostSchoolBoardCommentReq;
import com.example.demo.src.board.school.model.PostSchoolBoardReq;
import com.example.demo.src.board_registration.model.PostBoardRegistrationReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class BoardRegistrationService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BoardRegistrationDao boardRegistrationDao;


    @Autowired
    public BoardRegistrationService(BoardRegistrationDao boardRegistrationDao) {
        this.boardRegistrationDao = boardRegistrationDao;
    }



    public int postBoardRegistration(PostBoardRegistrationReq postBoardRegistrationReq) throws BaseException {
        try {
            boardRegistrationDao.postBoardRegistration(postBoardRegistrationReq);

            return 1;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}



