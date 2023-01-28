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
    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

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


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");

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
