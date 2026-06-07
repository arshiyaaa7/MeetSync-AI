package com.hintro.meetingintelligence.dtos.meeting;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class CreateMeetingRequest {

    @NotBlank
    private String title;

    @NotEmpty
    private List<String> participants;

    private Instant meetingDate;

    @Valid
    @NotEmpty
    private List<TranscriptEntryRequest> transcript;
}