package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.src.search.model.GetSearchBoardRes;
import com.example.demo.src.search.model.GetSearchScholarshipRes;
import com.example.demo.src.search.model.GetSearchSupportRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class SearchProvider {

    private final SearchDao searchDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SearchProvider(SearchDao searchDao) {

        this.searchDao = searchDao;
    }



    public List<GetSearchBoardRes> searchBoard(String query) throws BaseException {
        try {
            List<GetSearchBoardRes> getSearchBoardRes = searchDao.searchBoard(query);
            return getSearchBoardRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSearchScholarshipRes> searchScholarship(String query) throws BaseException {
        try {
            List<GetSearchScholarshipRes> getSearchScholarshipRes = searchDao.searchScholarship(query);
            return getSearchScholarshipRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSearchSupportRes> searchSupport(String query) throws BaseException {
        try {
            List<GetSearchSupportRes> getSearchSupportRes = searchDao.searchSupport(query);
            return getSearchSupportRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    public List<GetSearchAllRes> searchAll(String query) throws BaseException {
//        try {
//            List<GetSearchBoardRes> getSearchBoardRes = searchDao.searchBoard(query);
//            List<GetSearchScholarshipRes> getSearchScholarshipRes = searchDao.searchScholarship(query);
//            List<GetSearchSupportRes> getSearchSupportRes = searchDao.searchSupport(query);
//
//            List<GetSearchAllRes> getSearchAllRes = searchDao.searchAll(query);
//
//            return getSearchAllRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


}