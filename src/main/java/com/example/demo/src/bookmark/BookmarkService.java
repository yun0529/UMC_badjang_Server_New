package com.example.demo.src.bookmark;


import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.model.PostBookmarkScholarshipReq;
import com.example.demo.src.bookmark.model.PostBookmarkSupportReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;


@Service
public class BookmarkService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookmarkDao bookmarkDao;

    private final BookmarkProvider bookmarkProvider;

    @Autowired
    public BookmarkService(BookmarkProvider bookmarkProvider, BookmarkDao bookmarkDao) {
        this.bookmarkProvider = bookmarkProvider;
        this.bookmarkDao = bookmarkDao;

    }

    public int postBookmarkScholarship(PostBookmarkScholarshipReq postBookmarkScholarshipReq) throws BaseException {
        try {
            int postBookmarkScholarshipRes = bookmarkDao.postBookmarkScholarship(postBookmarkScholarshipReq);
            return postBookmarkScholarshipRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int postBookmarkSupport(PostBookmarkSupportReq postBookmarkSupportReq) throws BaseException {
        try {
            int postBookmarkSupportRes = bookmarkDao.postBookmarkSupport(postBookmarkSupportReq);
            return postBookmarkSupportRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



}



