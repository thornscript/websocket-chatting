package dev.poporo.websocketchat.common.filter;

import dev.poporo.websocketchat.common.constant.MdcConstants;
import dev.poporo.websocketchat.common.log.TraceIdGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class MdcFilter extends OncePerRequestFilter {

    private final TraceIdGenerator traceIdGenerator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String traceId = traceIdGenerator.generateTraceId();
        MDC.put(MdcConstants.TRACKING_ID, traceId);
        MDC.put(MdcConstants.REQUEST_URI, request.getRequestURI());
        MDC.put(MdcConstants.REQUEST_METHOD, request.getMethod());

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MdcConstants.TRACKING_ID);
            MDC.remove(MdcConstants.REQUEST_URI);
            MDC.remove(MdcConstants.REQUEST_METHOD);
        }
    }
}
