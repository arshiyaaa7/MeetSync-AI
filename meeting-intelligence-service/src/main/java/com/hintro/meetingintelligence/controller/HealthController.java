package com.hintro.meetingintelligence.controller;

import com.hintro.meetingintelligence.common.ApiResponse;
import com.hintro.meetingintelligence.common.TraceIdFilter;
import com.hintro.meetingintelligence.dtos.HealthResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ApiResponse<HealthResponse> health(
            HttpServletRequest httpRequest
    ) {

        HealthResponse response =
                new HealthResponse(
                        "UP",
                        "meeting-intelligence-service",
                        "UP",
                        "UP",
                        Instant.now().toString()
                );

        String traceId =
                httpRequest.getAttribute(
                        TraceIdFilter.TRACE_ID
                ).toString();

        return ApiResponse.success(
                traceId,
                response
        );
    }
}