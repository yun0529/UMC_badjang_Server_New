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
    //REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2100, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2101, "유효하지 않은 JWT입니다."),
    //INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    POST_USERS_EMPTY_PW(false, 2030, "비밀번호를 입력해주세요."),
    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2020, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2021, "이메일 형식을 확인해주세요."),
    POST_USERS_INVALID_PW(false, 2031, "비밀번호에는 문자, 특수문자, 숫자가 포함되어야 합니다. (8 ~ 15자)"),
    POST_USERS_INVALID_PHONE(false, 2061, "전화번호 형식에 맞게 입력해주세요."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    FAILED_TO_LOGIN(false,3100,"없는 아이디거나 비밀번호가 틀렸습니다."),

    //FAILED_TO_LOGIN_STATUS(false,3015,"이미 로그인된 아이디입니다."),
    NON_EXISTENT_EMAIL(false, 3101, "존재하지 않는 이메일입니다."),
    NON_EXISTENT_PHONENUMBER(false, 3102, "존재하지 않는 번호입니다."),
    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false,
            4000, "데이터베이스 연결에 실패하였습니다."),
    //SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERPASSWORD(false,4100,"유저 비밀번호 변경 실패"),
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다.");
    //PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");

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
