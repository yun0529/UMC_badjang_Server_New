package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    DELETE_SEARCH_HISTORY_SUCCESS(true, 1250, "최근 검색어 삭제 요청을 성공하였습니다."),
    POST_BOOKMARK_SUCCESS(true, 1251, "즐겨찾기가 추가되었습니다."),
    DELETE_BOOKMARK_SUCCESS(true, 1252, "즐겨찾기가 취소되었습니다."),
    POST_SCHOOL_BOARD_SUCCESS(true, 1253, "게시판을 추가하였습니다."),
    PATCH_SCHOOL_BOARD_SUCCESS(true, 1254, "게시판을 수정하였습니다."),
    DELETE_SCHOOL_BOARD_SUCCESS(true, 1255, "게시판을 삭제하였습니다."),
    POST_SCHOOL_BOARD_COMMENT_SUCCESS(true, 1256, "댓글이 추가되었습니다."),

    PATCH_SCHOOL_BOARD_COMMENT_SUCCESS(true, 1257, "댓글이 수정되었습니다."),
    DELETE_SCHOOL_BOARD_COMMENT_SUCCESS(true, 1258, "댓글이 삭제되었습니다."),
    POST_SCHOOL_BOARD_RECOMMEND_SUCCESS(true, 1259, "게시판을 추천하였습니다."),
    DELETE_SCHOOL_BOARD_RECOMMEND_SUCCESS(true, 1260, "게시판 추천을 취소하였습니다."),
    POST_SCHOOL_BOARD_COMMENT_RECOMMEND_SUCCESS(true, 1261, "댓글을 추천하였습니다."),
    DELETE_SCHOOL_BOARD_COMMENT_RECOMMEND_SUCCESS(true, 1262, "댓글 추천을 취소하였습니다."),

    POST_BOARD_REGISTRATION_SUCCESS(true, 1263, "게시판 만들기를 신청하였습니다."),



    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    STOPPED_USER(false, 2011, "탈퇴한 계정입니다."),
    OFFLINE_USER(false, 2012, "로그아웃한 계정입니다."),
    USERS_EMPTY_USER_IDX(false, 2201, "유저 인덱스 값을 확인해주세요. "),


    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2020, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2021, "이메일 형식에 맞게 입력해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2022,"중복된 이메일입니다."),

    POST_USERS_EMPTY_PW(false, 2030, "비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PW(false, 2031, "비밀번호에는 문자, 특수문자, 숫자가 포함되어야 합니다. (8 ~ 15자)"),

    POST_USERS_EMPTY_NAME(false, 2040, "이름을 입력해주세요."),
    POST_USERS_INVALID_NAME(false, 2041, "이름은 2 ~ 20자 사이로 입력해주세요."),

    POST_USERS_EMPTY_BIRTH(false, 2050, "생년월일을 입력해주세요."),
    POST_USERS_INVALID_BIRTH(false, 2051, "생년월일 형식에 맞게 입력해주세요."),
    POST_USERS_LIMIT_BIRTH(false, 2052, "만 14세 미만은 가입하실 수 없습니다."),

    POST_USERS_EMPTY_PHONE(false, 2060, "전화번호를 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2061, "전화번호 형식에 맞게 입력해주세요."),

    POST_USERS_EMPTY_INFO(false, 2070, "전화번호나 이름이 없습니다."),
    POST_USERS_MORE_INFO(false, 2071, "전화번호나 이름 중 한 가지만 입력해주세요."),

    POST_USERS_WRONG_TEXT(false, 2080, "'탈퇴하기'를 입력해주세요."),

    GET_SEARCH_EMPTY_QUERY(false, 2250, "검색어를 입력해주세요."),

    GET_SEARCH_INVALID_QUERY(false, 2251, "검색어는 50자 내로 입력해주세요."),

    DELETE_SEARCH_HISTORY_FAIL(false, 2252, "최근 검색어 삭제를 실패하였습니다."),

    POST_BOOKMARK_FAIL(false, 2253, "즐겨찾기를 실패하셨습니다."),

    SCHOOL_BOARD_AUTH_FAIL(false, 2254, "해당 게시판의 권한이 없습니다."),

    SCHOOL_BOARD_COMMENT_AUTH_FAIL(false, 2255, "해당 댓글의 권한이 없습니다."),

    POST_SCHOOL_BOARD_RECOMMEND_MINE(false, 2256, "게시판 작성자는 자신의 게시물을 추천할 수 없습니다."),
    POST_SCHOOL_BOARD_RECOMMEND_FAIL(false, 2257, "게시판 추천을 실패하였습니다."),
    POST_SCHOOL_BOARD_COMMENT_RECOMMEND_MINE(false, 2258, "댓글 작성자는 자신의 댓글을 추천할 수 없습니다."),
    POST_SCHOOL_BOARD_COMMENT_RECOMMEND_FAIL(false, 2259, "댓글 추천을 실패하였습니다."),
    POST_SCHOOL_BOARD_COMMENT_INVALID(false, 2260, "댓글은 100자까지 작성 가능합니다."),
    POST_SCHOOL_BOARD_TITLE_INVALID(false, 2261, "게시글 제목은 50자까지 작성 가능합니다."),
    POST_SCHOOL_BOARD_CONTENT_INVALID(false, 2262, "게시글 내용은 500자까지 작성 가능합니다."),
    POST_BOARD_REGISTRATION_PURPOSE_INVALID(false, 2263, "게시판 만들기의 운영목적은 10자~500자 내로 작성 해주세요."),
    POST_BOARD_REGISTRATION_RULE_INVALID(false, 2264, "게시판 만들기의 규칙은 10자~500자로 작성 해주세요."),
    POST_BOARD_REGISTRATION_NULL(false, 2265, "게시판 만들기의 모든 내용을 작성해주세요."),
    POST_SCHOOL_BOARD_TITLE_NULL(false, 2266, "게시판의 제목을 입력해주세요."),
    POST_SCHOOL_BOARD_CONTENT_NULL(false, 2267, "게시판의 내용을 입력해주세요."),
    POST_SCHOOL_BOARD_ANONYMITY_NULL(false, 2268, "게시판의 익명 여부를 체크해주세요."),
    POST_SCHOOL_BOARD_COMMENT_NULL(false, 2269, "댓글을 입력해주세요."),
    POST_SCHOOL_BOARD_COMMENT_ANONYMITY_NULL(false, 2270, "댓글의 익명 여부를 체크해주세요."),




    POST_COMMENT_EMPTY_CONTENT(false, 2301,"댓글을 입력해주세요."),
    PATCH_COMMENT_FAIL(false,2302,"댓글 수정에 실패했습니다."),

    DELETE_COMMENT_FAIL(false,2303,"댓글 삭제에 실패하였습니다."),
    GET_MYFILTER_EMPTY_UNIVERSITY(false, 2304, "대학교를 입력하세요."),
    GET_MYFILTER_EMPTY_COLLEGE(false, 2305, "단과대학을 입력하세요."),
    GET_MYFILTER_EMPTY_DEPARTMENT(false, 2306, "학과를 입력하세요."),
    GET_MYFILTER_EMPTY_GRADE(false, 2307, "학년을 입력하세요."),
    GET_MYFILTER_EMPTY_SEMESTER(false, 2308, "학기를 입력하세요."),
    GET_MYFILTER_EMPTY_PROVINCE(false, 2309, "도를 입력하세요."),
    GET_MYFILTER_EMPTY_CITY(false, 2310, "시/군/구/를을 입력하세요."),
    PATCH_WRONG_COMMENT_INDEX(false,2311,"잘못된 접근입니다."),

    POST_SCHOLARSHIP_EMPTY_NAME(false, 2352,"장학금 이름을 입력해주세요."),

    POST_SUPPORT_EMPTY_NAME(false, 2353,"지원금 이름을 입력해주세요."),

    POST_SUPPORT_EMPTY_POLICY(false, 2354,"정책id를 입력해주세요."),




    SCHOLARSHIP_EMPTY_SCHOLARSHIP_IDX(false, 2351, "해당 장학금idx 값이 존재하지 않습니다."),

    SUPPORT_EMPTY_SUPPORT_IDX(false, 2355, "해당 지원금idx 값이 존재하지 않습니다."),

    /**
     * 2200 ~ 2250 : 메인화면 오류처리(옆마당)
     */
    USERS_EMPTY_USER_IDX(false, 2200, "유저 인덱스 값을 확인해주세요. "),
    NON_MATCH_UNIV(false, 2201, "일치하는 장학금이 없습니다."),
    /**
     * 2400 ~ 2450 : 게시판 오류처리(옆마당)
     */
    EMPTY_BOARD_NAME(false, 2400, "게시물 제목을 입력해주세요"),
    EMPTY_BOARD_CONTENT(false, 2401, "게시물 본문을 입력해주세요"),
    INVALID_POST_IDX(false, 2402, "요청한 게시글 인덱스와 일치하지 않습니다."),
    EMPTY_POST_IDX(false, 2403, "게시글 인덱스를 확인하세요."),
    EMPTY_USER_IDX(false, 2404, "유저 인덱스를 확인하세요."),
    EMPTY_CATEGORY_IDX(false, 2405, "게시글 카테고리를 확인하세요."),
    EMPTY_COMMENT_IDX(false, 2406, "댓글 인덱스를 확인하세요."),
    EMPTY_COMMENT_ANONYMITY(false, 2407, "댓글의 익명성 여부를 확인하세요"),
    EMPTY_COMMENT_STATUS(false, 2408, "댓글의 존재유무를 확인하세요."),
    EMPTY_COMMENT_CONTENT(false, 2409, "댓글 내용을 입력하세요"),
    INVALID_COMMENT_IDX(false, 2410, "댓글 인덱스가 일치하지 않습니다."),




    /**
     * 3000 : Response 오류
     * 3400 ~ 3450 : 게시판 오류처리(옆마당)
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    FAILED_TO_LOGIN_STATUS(false,3015,"이미 로그인된 아이디입니다."),

    NON_EXISTENT_EMAIL(false, 3101, "존재하지 않는 이메일입니다."),
    NON_EXISTENT_PHONENUMBER(false, 3102, "존재하지 않는 번호입니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    KAKAO_CONNECTION_ERROR(false, 4020, "카카오톡 연결에 실패하였습니다."),

    MODIFY_FAIL_USERPASSWORD(false,4100,"유저 비밀번호 변경 실패"),
    MODIFY_FAIL_USERPROFILE(false,4101,"유저 프로필 변경 실패"),
    MODIFY_FAIL_USERINFO(false,4102,"유저 학교 및 지역 정보 변경 실패"),

    INCREASE_FAIL_SCHOLARSHIP_VIEW(false,4350,"장학금 조회수 증가 실패"),

    INCREASE_FAIL_SUPPORT_VIEW(false,4356,"지원금 조회수 증가 실패");
    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
