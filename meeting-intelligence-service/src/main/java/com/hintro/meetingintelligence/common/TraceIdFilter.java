package com.hintro.meetingintelligence.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter implements Filter {

    public static final String TRACE_ID = "traceId";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        String traceId =
                UUID.randomUUID().toString();

        try {

            httpRequest.setAttribute(
                    TRACE_ID,
                    traceId
            );

            MDC.put(
                    TRACE_ID,
                    traceId
            );

            chain.doFilter(
                    request,
                    response
            );

        } finally {

            MDC.clear();
        }
    }
}