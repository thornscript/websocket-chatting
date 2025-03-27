package dev.poporo.websocketchat.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MdcConstants {

    public static final String TRACKING_ID = "traceId";
    public static final String REQUEST_URI = "requestUri";
    public static final String REQUEST_METHOD = "method";
}
