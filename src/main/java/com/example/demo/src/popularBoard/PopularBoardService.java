package com.example.demo.src.popularBoard;


import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class PopularBoardService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PopularBoardDao popularBoardDao;
    private final PopularBoardProvider popularBoardProvider;
    private final JwtService jwtService;


    @Autowired
    public PopularBoardService(PopularBoardDao popularBoardDao, PopularBoardProvider popularBoardProvider, JwtService jwtService) {
        this.popularBoardDao = popularBoardDao;
        this.popularBoardProvider = popularBoardProvider;
        this.jwtService = jwtService;

    }

}
