package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.src.search.model.DeleteSearchHistoryReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DELETE_SEARCH_HISTORY_FAIL;

@Service
public class SearchService {

    private final SearchDao searchDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SearchService(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

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

}
