package com.example.demo.src.popularBoard;


import com.example.demo.config.BaseException;
import com.example.demo.src.popularBoard.model.GetPopularRes;
import com.example.demo.src.popularBoard.model.PostPopularRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class PopularBoardProvider {

    private final PopularBoardDao popularBoardDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PopularBoardProvider(PopularBoardDao popularBoardDao) {
        this.popularBoardDao = popularBoardDao;
    }

    public List<GetPopularRes> getPopularAll() throws BaseException {
        try{
            List<GetPopularRes> getBoardRes = popularBoardDao.getPopularAll();
            return getBoardRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPopularRes> getPopular() throws BaseException {
        try{
            List<GetPopularRes> getBoardRes = popularBoardDao.getPopular();
            return getBoardRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostPopularRes postPopular() throws BaseException {
        try{
            PostPopularRes postBoardRes = popularBoardDao.postPopular();
            return postBoardRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPopularRes removeDup() throws BaseException {
        try{
            GetPopularRes postBoardRes = popularBoardDao.removeDup();
            return postBoardRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
