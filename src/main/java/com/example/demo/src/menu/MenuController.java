package com.example.demo.src.menu;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.menu.model.GetPopularRes;
import com.example.demo.src.menu.model.GetSchoolRes;
import com.example.demo.src.menu.model.GetTotalRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RestController
@RequestMapping("")
public class MenuController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MenuProvider menuProvider;
    @Autowired
    private final MenuService menuService;




    public MenuController(MenuProvider menuProvider, MenuService menuService, JwtService jwtService){
        this.menuProvider = menuProvider;
        this.menuService = menuService;
    }

    /**
     * 전국소식 조회 API
     * [GET] /menu/total
     * @return BaseResponse<List<GetTotalRes>>
     */
    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/menu/total") // (GET) 127.0.0.1:9000/menu/total
    public BaseResponse<List<GetTotalRes>> getTotal() {
        try{

                List<GetTotalRes> getTotalRes = menuProvider.getTotal();
                return new BaseResponse<>(getTotalRes);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 인기글 조회 API
     * [GET] /menu/popular
     * @return BaseResponse<List<GetPopularRes>>
     */
    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/menu/popular") // (GET) 127.0.0.1:9000/menu/popular
    public BaseResponse<List<GetPopularRes>> getPopular() {
        try{

            List<GetPopularRes> getPopularRes = menuProvider.getPopular();
            return new BaseResponse<>(getPopularRes);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/menu/school") // (GET) 127.0.0.1:9000/menu/school
    public BaseResponse<List<GetSchoolRes>> getSchool() {
        try{

            List<GetSchoolRes> getSchoolRes = menuProvider.getSchool();
            return new BaseResponse<>(getSchoolRes);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
