package dev.poporo.websocketchat.common.exception;

public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateResourceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
