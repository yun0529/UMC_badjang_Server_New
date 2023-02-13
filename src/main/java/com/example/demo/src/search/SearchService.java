package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.src.search.model.DeleteSearchHistoryReq;
import com.example.demo.src.search.model.GetSearchHistoryRes;
import com.example.demo.src.support_comment.SupportCommentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class SearchService {

    private final SearchDao searchDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SearchService(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    //최근 검색어에 있는 검색어 삭제
    public void deleteSearchHistory(DeleteSearchHistoryReq deleteSearchHistoryReq) throws BaseException {
        try {
            int result = searchDao.deleteHistoryQuery(deleteSearchHistoryReq);
            if (result == 0){
                throw new BaseException(DELETE_SEARCH_HISTORY_FAIL);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //검색 시에 검색어를 최근 검색어 내역에 저장
    public void saveQuery(long userIdx, String query) {
        searchDao.postSearchHistory(userIdx, query);
    }

}
