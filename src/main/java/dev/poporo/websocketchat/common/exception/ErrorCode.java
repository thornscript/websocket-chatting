package dev.poporo.websocketchat.common.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 사용자
    DUPLICATE_USERNAME(BAD_REQUEST, "이미 사용 중인 사용자명입니다. 다른 사용자명을 입력해주세요."),
    USER_NOT_FOUND(NOT_FOUND, "해당 사용자를 찾을 수 없습니다. 사용자 정보를 확인해주세요."),
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "인증되지 않은 접근입니다. 로그인 후 이용해주세요."),
    ACCOUNT_LOCKED(FORBIDDEN, "계정이 잠겨 있습니다. 관리자에게 문의해주세요."),

    // 데이터 포맷
    INVALID_DATA_FORMAT(BAD_REQUEST, "입력한 데이터 형식이 올바르지 않습니다. 입력 형식을 확인해 주세요."),

    // 인증
    INVALID_TOKEN_EXCEPTION(UNAUTHORIZED, "유효하지 않은 토큰입니다. 다시 로그인해주세요."),
    EXPIRED_TOKEN_EXCEPTION(UNAUTHORIZED, "만료된 토큰입니다. 다시 로그인해주세요."),

    APPLICATION_ERROR(INTERNAL_SERVER_ERROR, "문제가 발생했습니다. 잠시 후 다시 시도해 주세요.");

    private final HttpStatus status;
    private final String message;
}
