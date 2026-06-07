package com.hintro.meetingintelligence.exception;

import com.hintro.meetingintelligence.common.ApiResponse;
import com.hintro.meetingintelligence.common.TraceIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> handleValidation(
          MethodArgumentNotValidException ex,
          HttpServletRequest request
  ) {

    String message =
            Objects.requireNonNull(
                    ex.getBindingResult()
                            .getFieldError()
            ).getDefaultMessage();

    String traceId =
            request.getAttribute(
                    TraceIdFilter.TRACE_ID
            ).toString();

    return ApiResponse.failure(
            traceId,
            "VALIDATION_ERROR",
            message
    );
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiResponse<Void> handleNotFound(
          ResourceNotFoundException ex,
          HttpServletRequest request
  ) {

    String traceId =
            request.getAttribute(
                    TraceIdFilter.TRACE_ID
            ).toString();

    return ApiResponse.failure(
            traceId,
            "NOT_FOUND",
            ex.getMessage()
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> handleIllegalArgument(
          IllegalArgumentException ex,
          HttpServletRequest request
  ) {

    String traceId =
            request.getAttribute(
                    TraceIdFilter.TRACE_ID
            ).toString();

    return ApiResponse.failure(
            traceId,
            "BAD_REQUEST",
            ex.getMessage()
    );
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiResponse<Void> handleRuntime(
          RuntimeException ex,
          HttpServletRequest request
  ) {

    String traceId =
            request.getAttribute(
                    TraceIdFilter.TRACE_ID
            ).toString();

    return ApiResponse.failure(
            traceId,
            "INTERNAL_SERVER_ERROR",
            ex.getMessage()
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiResponse<Void> handleException(
          Exception ex,
          HttpServletRequest request
  ) {

    String traceId =
            request.getAttribute(
                    TraceIdFilter.TRACE_ID
            ).toString();

    return ApiResponse.failure(
            traceId,
            "UNEXPECTED_ERROR",
            ex.getMessage()
    );
  }
}