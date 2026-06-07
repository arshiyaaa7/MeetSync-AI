package com.hintro.meetingintelligence.controller;

import com.hintro.meetingintelligence.common.ApiResponse;
import com.hintro.meetingintelligence.common.TraceIdFilter;
import com.hintro.meetingintelligence.dtos.evaluation.EvaluationResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @GetMapping
    public ApiResponse<EvaluationResponse> evaluation(
            HttpServletRequest request
    ) {

        EvaluationResponse response =
                new EvaluationResponse(
                        "Arshiya Shaikh",
                        "your-email@gmail.com",
                        "https://github.com/your-repo",
                        "https://your-deployment-url.com",
                        "Groq AI API",
                        List.of(
                                "Authentication",
                                "AI Meeting Analysis",
                                "Citation Grounding",
                                "Structured JSON Responses",
                                "Swagger Integration",
                                "Unified API Responses",
                                "Health Monitoring"
                        )
                );

        String traceId =
                request.getAttribute(
                        TraceIdFilter.TRACE_ID
                ).toString();

        return ApiResponse.success(
                traceId,
                response
        );
    }
}