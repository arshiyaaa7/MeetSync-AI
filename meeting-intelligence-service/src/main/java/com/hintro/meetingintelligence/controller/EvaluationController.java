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
                        "arshiyashaikh2202@gmail.com",
                        "https://github.com/arshiyaaa7/MeetSync-AI",
                        "https://meetsync-ai-production.up.railway.app",
                        "Groq AI API",
                        List.of(
                                "JWT Authentication",
                                "AI Meeting Analysis",
                                "Transcript Citation Grounding",
                                "Action Item Management",
                                "Overdue Action Item Detection",
                                "Scheduled Reminder Job",
                                "Discord Webhook Integration",
                                "Reminder History Tracking",
                                "Swagger/OpenAPI Documentation",
                                "Unified API Responses",
                                "Global Exception Handling",
                                "Input Validation",
                                "Trace ID Logging"
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