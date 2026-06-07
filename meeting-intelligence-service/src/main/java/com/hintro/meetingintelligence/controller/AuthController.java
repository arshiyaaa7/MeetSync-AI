package com.hintro.meetingintelligence.controller;

import com.hintro.meetingintelligence.common.ApiResponse;
import com.hintro.meetingintelligence.common.TraceIdFilter;
import com.hintro.meetingintelligence.dtos.auth.AuthResponse;
import com.hintro.meetingintelligence.dtos.auth.LoginRequest;
import com.hintro.meetingintelligence.dtos.auth.RegisterRequest;
import com.hintro.meetingintelligence.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {

        authService.register(request);

        String traceId = httpRequest.getAttribute(TraceIdFilter.TRACE_ID).toString();

        return ApiResponse.success(traceId, "Email registered successfully");
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {

        AuthResponse response = authService.login(request);
        String traceId = httpRequest.getAttribute(TraceIdFilter.TRACE_ID).toString();

        return ApiResponse.success(traceId, response);
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(
            HttpServletRequest request
    ) {

        String traceId =
                request.getAttribute(
                        TraceIdFilter.TRACE_ID
                ).toString();

        return ApiResponse.success(
                traceId,
                "Logged out successfully"
        );
    }
}