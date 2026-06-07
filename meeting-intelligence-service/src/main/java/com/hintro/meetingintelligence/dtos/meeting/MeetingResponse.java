package com.hintro.meetingintelligence.dtos.meeting;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MeetingResponse {

    private List<SummaryItemResponse> summary;

    private List<ActionItemResponse> actionItems;

    private List<SummaryItemResponse> decisions;

    private List<SummaryItemResponse> followUpSuggestions;
}