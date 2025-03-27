package dev.poporo.websocketchat.common.log;

import java.util.UUID;

public class UuidTraceIdGenerator implements TraceIdGenerator{

    @Override
    public String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
