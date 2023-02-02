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

    POST_SCHOLARSHIP_EMPTY_NAME(false, 2352,"장학금 이름을 입력해주세요."),






    SCHOLARSHIP_EMPTY_SCHOLARSHIP_IDX(false, 2351, "해당 값이 존재하지 않습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    FAILED_TO_LOGIN_STATUS(false,3015,"이미 로그인된 아이디입니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    INCREASE_FAIL_SCHOLARSHIP_VIEW(false,4350,"장학금 조회수 증가 실패");
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
