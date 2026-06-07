package com.hintro.meetingintelligence.dtos.meeting;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ActionItemResponse {

    private String task;

    private String assignee;

    private List<CitationResponse> citations;
}