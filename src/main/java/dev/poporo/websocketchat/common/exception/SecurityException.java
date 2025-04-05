package dev.poporo.websocketchat.common.exception;

public class SecurityException extends BusinessException {

    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SecurityException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
