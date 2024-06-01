package bsmgg.bsmgg_backend.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BAD_REQUEST_ERROR(400, "Bad Request Exception"),
    REQUEST_BODY_MISSING_ERROR(400, "Required request body is missing"),
    INVALID_TYPE_VALUE(400, " Invalid Type Value"),
    MISSING_REQUEST_PARAMETER_ERROR(400, "Missing Servlet RequestParameter Exception"),
    IO_ERROR(400, "I/O Exception"),
    JSON_PARSE_ERROR(400, "JsonParseException"),
    JACKSON_PROCESS_ERROR(400, "com.fasterxml.jackson.core Exception"),

    FORBIDDEN_ERROR(403, "Forbidden Exception"),

    NOT_FOUND(404, "페이지를 찾을 수 없습니다."),

    NOT_FOUND_ERROR(404, "Not Found Exception"),
    NULL_POINT_ERROR(404, "Null Point Exception"),
    NOT_VALID_ERROR(404, "handle Validation Exception"),
    NOT_VALID_HEADER_ERROR(404, "Request Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error Exception"),

    //Jwt
    INVALID_TOKEN(403, "잘못된 토큰입니다."),
    EXPIRED_TOKEN(403, "만료된 토큰입니다."),

    //User
    UNAUTHORIZED(401, "로그인이 필요합니다."),
    ILLEGAL_PASSWORD(401, "잘못된 비밀번호입니다."),
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    USER_ALREADY_REGISTERED(409, "이미 가입된 유저입니다.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
