package com.example.demo.src.popularBoard;

import com.example.demo.config.BaseException;
import com.example.demo.src.popularBoard.model.GetPopularRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RestController
@RequestMapping("")
public class PopularBoardController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PopularBoardProvider popularBoardProvider;

    public PopularBoardController(PopularBoardProvider popularBoardProvider){
        this.popularBoardProvider = popularBoardProvider;
    }


    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/popularBoardAll")
    public BaseResponse<List<GetPopularRes>> getPopularBoardAll() {
        try{
            List<GetPopularRes> getPopularRes = popularBoardProvider.getPopularAll();
            return new BaseResponse<>(getPopularRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/popularBoard")
    public BaseResponse<List<GetPopularRes>> getPopularBoard() {
        try{
            List<GetPopularRes> getPopularRes = popularBoardProvider.getPopular();
            return new BaseResponse<>(getPopularRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/popularBoard/add")
    public BaseResponse<String> postPopularBoard() {
        try{
            popularBoardProvider.removeDup();
            popularBoardProvider.postPopular();
            String result = "추가를 완료하였습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
