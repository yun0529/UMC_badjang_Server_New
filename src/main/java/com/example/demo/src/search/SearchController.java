package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.search.model.GetSearchBoardRes;
import com.example.demo.src.search.model.GetSearchScholarshipRes;
import com.example.demo.src.search.model.GetSearchSupportRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;

@RestController
public class SearchController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SearchProvider searchProvider;

    public SearchController(SearchProvider searchProvider) {
        this.searchProvider = searchProvider;
    }

//    @ResponseBody
//    @GetMapping("/search")
//    public BaseResponse<List<GetSearchAllRes>> searchAll(@RequestParam(value="query") String query) {
//        try {
//            if (query == null || query.equals("")) {
//                return new BaseResponse<>(REQUEST_ERROR);
//            }
//            List<GetSearchAllRes> getSearchAllRes = searchProvider.searchAll(query);
//            return new BaseResponse<>(getSearchAllRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


    @ResponseBody
    @GetMapping("/search/board")
    public BaseResponse<List<GetSearchBoardRes>> searchBoard(@RequestParam(value="query") String query) {
        try {
            if (query == null || query.equals("")) {
                return new BaseResponse<>(REQUEST_ERROR);
            }
            List<GetSearchBoardRes> getSearchBoardRes = searchProvider.searchBoard(query);
            return new BaseResponse<>(getSearchBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/search/scholarship")
    public BaseResponse<List<GetSearchScholarshipRes>> searchScholarship(@RequestParam(value="query") String query) {
        try {
            if (query == null || query.equals("")) {
                return new BaseResponse<>(REQUEST_ERROR);
            }
            List<GetSearchScholarshipRes> getSearchScholarshipRes = searchProvider.searchScholarship(query);
            return new BaseResponse<>(getSearchScholarshipRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/search/support")
    public BaseResponse<List<GetSearchSupportRes>> searchSupport(@RequestParam(value="query") String query) {
        try {
            if (query == null || query.equals("")) {
                return new BaseResponse<>(REQUEST_ERROR);
            }
            List<GetSearchSupportRes> getSearchSupportRes = searchProvider.searchSupport(query);
            return new BaseResponse<>(getSearchSupportRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
