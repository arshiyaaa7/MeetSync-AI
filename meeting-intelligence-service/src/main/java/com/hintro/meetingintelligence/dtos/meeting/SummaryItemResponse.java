package com.hintro.meetingintelligence.dtos.meeting;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SummaryItemResponse {

    private String text;

    private List<CitationResponse> citations;
}