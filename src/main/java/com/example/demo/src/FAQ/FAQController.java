package com.example.demo.src.FAQ;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.FAQ.model.GetFAQRes;
import com.example.demo.src.FAQ.model.PostFAQReq;
import com.example.demo.src.FAQ.model.PostFAQRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/FAQs")

public class FAQController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기

    @Autowired
    private final FAQProvider FAQProvider;

    @Autowired
    private final FAQService FAQService;

    public FAQController(FAQProvider FAQProvider, FAQService FAQService) {

        this.FAQProvider = FAQProvider;
        this.FAQService = FAQService;
    }

    /**
     * 자주묻는 질문 전체 조회 API
     */
    @ResponseBody
    @GetMapping("")

    public BaseResponse<List<GetFAQRes>> getFAQs(){

        try {
            List<GetFAQRes> getFAQsRes = FAQProvider.getFAQs();
            return new BaseResponse<>(getFAQsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 자주묻는 질문 1개 조회 API
     * [GET] /FAQs/:FAQIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{FAQIdx}")
    public BaseResponse<GetFAQRes> getFAQ(@PathVariable("FAQIdx") long FAQIdx) {
        try {
            if (FAQProvider.checkFAQIdx(FAQIdx) == 0) {
                throw new BaseException(FAQ_EMPTY_FAQ_IDX);
            }
            GetFAQRes getFAQRes = FAQProvider.getFAQ(FAQIdx);
            return new BaseResponse<>(getFAQRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * FAQ 작성 API
     * [POST] /FAQs/new-FAQ
     */
    @ResponseBody
    @PostMapping("/new-FAQ")
    public BaseResponse<PostFAQRes> createFAQ(@RequestBody PostFAQReq postFAQReq) {
        //FAQ 제목을 입력하지 않으면 에러메시지
        if (postFAQReq.getFAQ_title() == null) {
            return new BaseResponse<>(EMPTY_TITLE);
        }

        //FAQ 내용을 입력하지 않으면 에러메시지
        if (postFAQReq.getFAQ_content() == null) {
            return new BaseResponse<>(EMPTY_CONTENT);
        }
        try {
            PostFAQRes postFAQRes = FAQService.createFAQ(postFAQReq);
            return new BaseResponse<>(postFAQRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
