package com.example.demo.src.menu;

import com.example.demo.config.BaseException;
import com.example.demo.src.menu.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public GetListRes getTotal(int user_idx) throws BaseException{
        try{
            GetListRes list = new GetListRes (menuDao.getTotalScholarshipRes(user_idx),
                    menuDao.getTotalSupportRes(user_idx));
            return list;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSchoolRes> getSchool(int user_idx) throws BaseException{
        try{
            List<GetSchoolRes> getSchoolRes = menuDao.getScholarship(user_idx);
            return getSchoolRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
