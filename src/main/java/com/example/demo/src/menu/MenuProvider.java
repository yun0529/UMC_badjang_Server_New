package com.example.demo.src.menu;

import com.example.demo.config.BaseException;

import com.example.demo.src.menu.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class MenuProvider {

    private final MenuDao menuDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MenuProvider(MenuDao menuDao, JwtService jwtService) {
        this.menuDao = menuDao;
        this.jwtService = jwtService;
    }

    public List<GetPopularRes> getPopular() throws BaseException{
        try{
            List<GetPopularRes> getPopularRes = menuDao.getPopularRes();
            return getPopularRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetTotalRes> getTotal() throws BaseException{
        try{
            List<GetTotalRes> getTotalRes = menuDao.getTotalRes();
            return getTotalRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSchoolRes> getSchool(int user_idx) throws BaseException{
        try{
            List<GetSchoolRes> getSchoolRes = menuDao.getSchoolRes(user_idx);
            return getSchoolRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*public List<PostSchoolRes> postSchool(int user_idx) throws BaseException{
        try{
            List<PostSchoolRes> postSchoolRes = menuDao.postSchoolRes(user_idx);
            return postSchoolRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }*/
}
