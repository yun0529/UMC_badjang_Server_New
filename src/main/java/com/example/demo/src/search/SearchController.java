package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.search.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
public class SearchController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SearchProvider searchProvider;

    @Autowired
    private final SearchService searchService;

    private final JwtService jwtService;

    public SearchController(SearchProvider searchProvider, SearchService searchService, JwtService jwtService) {
        this.searchProvider = searchProvider;
        this.searchService = searchService;
        this.jwtService = jwtService;
    }

    /**
     * 최근 검색어 API
     * [GET] /search
     */
    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<GetSearchHistoryRes>> searchHistory() {

        try {
            int userIdx = jwtService.getUserIdx();
            List<GetSearchHistoryRes> getSearchHistoryRes = searchProvider.searchHistory(userIdx);
            return new BaseResponse<>(getSearchHistoryRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 최근 검색어 삭제 API
     * [DELETE] /search/delete/:searchHistoryIdx
     */
    @ResponseBody
    @DeleteMapping("/search/delete/{searchHistoryIdx}")
    public BaseResponse<String> deleteSearchHistory(@PathVariable int searchHistoryIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            DeleteSearchHistoryReq deleteSearchHistoryReq = new DeleteSearchHistoryReq(userIdx, searchHistoryIdx);
            searchService.deleteSearchHistory(deleteSearchHistoryReq);

            return new BaseResponse<>(DELETE_SEARCH_HISTORY_SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 전체 검색
     * [GET] /search/all
     */
    @ResponseBody
    @GetMapping("/search/all")
    public BaseResponse<GetSearchAllRes> searchAll(@RequestParam(value="query") String query) {
        try {
            if (query == null || query.equals("")) {
                return new BaseResponse<>(GET_SEARCH_EMPTY_QUERY);
            }
            if (query.length() > 50) {
                return new BaseResponse<>(GET_SEARCH_INVALID_QUERY);
            }
            int userIdx = jwtService.getUserIdx();
            GetSearchAllRes getSearchAllRes = searchProvider.searchAll(query);
            searchService.saveQuery(userIdx, query);

            return new BaseResponse<>(getSearchAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 게시판 검색
     * [GET] /search/board
     */
    @ResponseBody
    @GetMapping("/search/board")
    public BaseResponse<List<GetSearchBoardRes>> searchBoard(@RequestParam(value="query") String query) {
        try {
            if (query == null || query.equals("")) {
                return new BaseResponse<>(GET_SEARCH_EMPTY_QUERY);
            }
            if (query.length() > 50) {
                return new BaseResponse<>(GET_SEARCH_INVALID_QUERY);
            }
            long userIdx = jwtService.getUserIdx();
            List<GetSearchBoardRes> getSearchBoardRes = searchProvider.searchBoard(query);
            searchService.saveQuery(userIdx, query);

            return new BaseResponse<>(getSearchBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장학금 검색
     * [GET] /search/scholarship
     */
    @ResponseBody
    @GetMapping("/search/scholarship")
    public BaseResponse<List<GetSearchScholarshipRes>> searchScholarship(@RequestParam(value="query") String query) {
        try {
            if (query == null || query.equals("")) {
                return new BaseResponse<>(GET_SEARCH_EMPTY_QUERY);
            }
            if (query.length() > 50) {
                return new BaseResponse<>(GET_SEARCH_INVALID_QUERY);
            }
            int userIdx = jwtService.getUserIdx();
            List<GetSearchScholarshipRes> getSearchScholarshipRes = searchProvider.searchScholarship(query);
            searchService.saveQuery(userIdx, query);

            return new BaseResponse<>(getSearchScholarshipRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 지원금 검색
     * [GET] /search/support
     */
    @ResponseBody
    @GetMapping("/search/support")
    public BaseResponse<List<GetSearchSupportRes>> searchSupport(@RequestParam(value="query") String query) {
        try {
            if (query == null || query.equals("")) {
                return new BaseResponse<>(GET_SEARCH_EMPTY_QUERY);
            }
            if (query.length() > 50) {
                return new BaseResponse<>(GET_SEARCH_INVALID_QUERY);
            }
            int userIdx = jwtService.getUserIdx();
            List<GetSearchSupportRes> getSearchSupportRes = searchProvider.searchSupport(query);
            searchService.saveQuery(userIdx, query);

            return new BaseResponse<>(getSearchSupportRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}