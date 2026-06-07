package com.hintro.meetingintelligence.controller;

import com.hintro.meetingintelligence.common.ApiResponse;
import com.hintro.meetingintelligence.common.TraceIdFilter;
import com.hintro.meetingintelligence.dtos.meeting.CreateActionItemRequest;
import com.hintro.meetingintelligence.dtos.meeting.UpdateActionItemStatusRequest;
import com.hintro.meetingintelligence.entity.ActionItem;
import com.hintro.meetingintelligence.service.ActionItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/action-items")
@RequiredArgsConstructor
public class ActionItemController {

    private final ActionItemService actionItemService;

    @PostMapping
    public ApiResponse<ActionItem> create(
            @Valid @RequestBody
            CreateActionItemRequest request,
            HttpServletRequest httpRequest
    ) {

        ActionItem actionItem =
                actionItemService.create(request);

        String traceId =
                httpRequest.getAttribute(
                        TraceIdFilter.TRACE_ID
                ).toString();

        return ApiResponse.success(
                traceId,
                actionItem
        );
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<ActionItem> updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateActionItemStatusRequest request,
            HttpServletRequest httpRequest
    ) {

        ActionItem actionItem =
                actionItemService.updateStatus(
                        id,
                        request
                );

        String traceId =
                httpRequest.getAttribute(
                        TraceIdFilter.TRACE_ID
                ).toString();

        return ApiResponse.success(
                traceId,
                actionItem
        );
    }

    @GetMapping
    public ApiResponse<List<ActionItem>> getAll(

            @RequestParam
            String status,

            @RequestParam(required = false)
            String assignee,

            @RequestParam(required = false)
            UUID meetingId,

            HttpServletRequest httpRequest
    ) {

        status = status.toUpperCase();

        List<ActionItem> actionItems =
                actionItemService.getAll(
                        status,
                        assignee,
                        meetingId
                );

        String traceId =
                httpRequest.getAttribute(
                        TraceIdFilter.TRACE_ID
                ).toString();

        return ApiResponse.success(
                traceId,
                actionItems
        );
    }

    @GetMapping("/overdue")
    public ApiResponse<List<ActionItem>> getOverdue(

            HttpServletRequest httpRequest
    ) {

        List<ActionItem> actionItems =
                actionItemService.getOverdueActionItems();

        String traceId =
                httpRequest.getAttribute(
                        TraceIdFilter.TRACE_ID
                ).toString();

        return ApiResponse.success(
                traceId,
                actionItems
        );
    }
}