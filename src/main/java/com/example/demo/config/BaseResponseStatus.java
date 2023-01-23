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

    GET_SEARCH_EMPTY_QUERY(false, 2200, "검색어를 입력해주세요."),

    GET_SEARCH_INVALID_QUERY(false, 2201, "검색어는 50자 내로 입력해주세요."),


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
