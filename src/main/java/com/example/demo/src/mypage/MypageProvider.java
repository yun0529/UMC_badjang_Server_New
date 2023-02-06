package com.example.demo.src.mypage;

import com.example.demo.config.BaseException;
import com.example.demo.src.mypage.model.GetBoardRes;
import com.example.demo.src.mypage.model.GetCommentRes;
import com.example.demo.src.mypage.model.GetMypageRes;
import com.example.demo.src.mypage.model.GetNoticeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MypageProvider {
    private final MypageDao mypageDao;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public MypageProvider(MypageDao mypageDao, JwtService jwtService) {
        this.mypageDao = mypageDao;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }
    // 해당 userIdx를 갖는 User의 정보 조회
    public GetMypageRes getMypage(int user_idx) throws BaseException {
        try {
            GetMypageRes getmypageRes = mypageDao.getMypage(user_idx);
            return getmypageRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //내가작성한 게시글 조회
    public List<GetBoardRes> getBoard(int user_idx) throws BaseException {
        try{
            List<GetBoardRes> getBoardRes = mypageDao.getBoard(user_idx);
            return getBoardRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCommentRes> getComment(int user_idx) throws BaseException {
        try{
            List<GetCommentRes> getCommentRes = mypageDao.getComment(user_idx);
            return getCommentRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //공지사항 조회
    public List<GetNoticeRes> getNotice() throws BaseException {
        try{
            List<GetNoticeRes> getNoticeRes = mypageDao.getNotice();
            return getNoticeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetNoticeRes getNoticeDetail(int notice_idx) throws BaseException {
        try{
            GetNoticeRes getNoticeRes = mypageDao.getNoticeDetail(notice_idx);
            return getNoticeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
