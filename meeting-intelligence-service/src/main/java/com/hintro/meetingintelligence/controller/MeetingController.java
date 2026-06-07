package com.hintro.meetingintelligence.controller;

import com.hintro.meetingintelligence.common.ApiResponse;
import com.hintro.meetingintelligence.common.TraceIdFilter;
import com.hintro.meetingintelligence.dtos.meeting.CreateMeetingRequest;
import com.hintro.meetingintelligence.dtos.meeting.MeetingResponse;
import com.hintro.meetingintelligence.service.MeetingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public ApiResponse<MeetingResponse> createMeeting(@Valid @RequestBody CreateMeetingRequest request,
                                                      HttpServletRequest httpRequest) {

        MeetingResponse response = meetingService.createMeeting(request);
        String traceId = httpRequest.getAttribute(TraceIdFilter.TRACE_ID).toString();

        return ApiResponse.success(traceId, response);
    }
}