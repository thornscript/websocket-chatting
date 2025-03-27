package dev.poporo.websocketchat.common.log;

import dev.poporo.websocketchat.common.constant.MdcConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpRequestLogger {

    private static final String HTTP_REQUEST_LOG_FORMAT = "({} {}) {}";

    public static void info(String message) {
        log.info(HTTP_REQUEST_LOG_FORMAT,
                MDC.get(MdcConstants.REQUEST_METHOD),
                MDC.get(MdcConstants.REQUEST_URI),
                message);
    }

    public static void error(Exception ex) {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "No error message";
        log.error(HTTP_REQUEST_LOG_FORMAT,
                MDC.get(MdcConstants.REQUEST_METHOD),
                MDC.get(MdcConstants.REQUEST_URI),
                errorMessage,
                ex);
    }
}
